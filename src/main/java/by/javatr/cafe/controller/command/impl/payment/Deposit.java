package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class for processing payment request.
 * Make deposit to user account
 */
@Component
public class Deposit implements Command {

    @Autowired
    IUserService service;
    @Autowired
    IOrderService orderService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String pay_method = content.getRequestParam(RequestParameters.PAYMENT_NONCE);
        String request_amount = content.getRequestParam(RequestParameters.AMOUNT);

        int userId = (int) content.getSessionAttr(SessionAttributes.USER_ID);
        final User user = service.find(userId);

        BigDecimal amount = new BigDecimal(request_amount).setScale(2, RoundingMode.HALF_UP);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.paymentMethodNonce(pay_method).customer().customerId(Integer.valueOf(user.getId()).toString()).done()
                .amount(amount)
                .options()
                .submitForSettlement(true)
                .done();



        Result<Transaction> result = Gateway.getGateway().transaction().sale(transactionRequest);

        if(result.isSuccess()){
            Transaction target = result.getTarget();

            DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = instance.format(Calendar.getInstance().getTime());

            Order order = new Order(PaymentMethod.CARD, PaymentStatus.CLOSED, time,time,amount, user.getId());

            orderService.deposit(order);
            return new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrderId(), HttpServletResponse.SC_OK);
        }else{
            System.out.println(result.getErrors());
            System.out.println(result.getMessage());
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private Deposit() {
    }
}
