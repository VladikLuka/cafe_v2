package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import com.braintreegateway.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Component
public class Deposit implements Command {

    @Autowired
    IUserService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String pay_method = content.getRequestParam("payment_method_nonce");
        String amount = content.getRequestParam("amount");

        int user_id = (int) content.getSessionAttr(SessionAttributes.USER_ID);
        final User user = service.find(user_id);


        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.paymentMethodNonce(pay_method).customer().customerId(Integer.valueOf(user.getId()).toString()).done()
                .amount(new BigDecimal(amount))
                .options()
                .submitForSettlement(true)
                .done();



        Result<Transaction> result = Gateway.getGateway().transaction().sale(transactionRequest);

        if(result.isSuccess()){
            Transaction target = result.getTarget();
            user.setMoney(target.getAmount().add(user.getMoney()));
            final boolean b = service.replenishBalance(user);
            if(b){
                content.addSessionAttr(SessionAttributes.USER_MONEY, user.getMoney());
            }
            displayTransactionInfo(target);
            return new RequestResult(Navigation.REDIRECT, "/checkout/" + target.getId(), HttpServletResponse.SC_OK);
        }else{
            ValidationErrors errors = result.getErrors();
            validationError(errors);
            System.out.println(result.getMessage());
        }

        return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

    }

    private static void displayTransactionInfo(Transaction transaction) {
        System.out.println(" ------ Transaction Info ------ ");
        System.out.println(" Transaction Id  : " +transaction.getId());
        System.out.println(" Processor Response Text : " +transaction.getProcessorResponseText());
    }

    private static void validationError(ValidationErrors errors) {
        List<ValidationError> error = errors.getAllDeepValidationErrors();
        for (ValidationError er : error) {
            System.out.println(" error code : " + er.getCode());
            System.out.println(" error message  : " + er.getMessage());
        }
    }
}
