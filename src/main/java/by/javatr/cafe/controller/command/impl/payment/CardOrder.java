package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.*;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.ICartService;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.util.Utils;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class for processing payment request.
 * Make an order from client card
 */
@Component
public class CardOrder implements Command {

    @Autowired
    IUserService userService;
    @Autowired
    ICartService cartService;
    @Autowired
    IOrderService orderService;


    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String payMethod = content.getRequestParam(RequestParameters.PAYMENT_NONCE);
        int addressId = Integer.parseInt(content.getRequestParam(RequestParameters.ADDRESS));
        String deliveryTime = content.getRequestParam(RequestParameters.TIME_DELIVERY);
        int userId =(int) content.getSessionAttr(SessionAttributes.USER_ID);

        final User user = userService.find(userId);

        final List<Address> addresses = user.getAddress();
        Address address = null;

        for (Address addr:addresses) {
                if(addr.getId() == addressId){
                    address = addr;
                    break;
                }
        }

        if(address == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = instance.format(Calendar.getInstance().getTime());

        Cart cart =(Cart) content.getSessionAttr(SessionAttributes.CART);

        Map<Dish, Integer> dishOrder = Utils.countSame(cart.getUserCart());

        BigDecimal amount  = Utils.amount(cart.getUserCart()).setScale(2, RoundingMode.HALF_UP);

        Order order = new Order(PaymentMethod.CARD, PaymentStatus.PAID, time, deliveryTime, dishOrder, address, amount ,userId);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.paymentMethodNonce(payMethod)
                .customer()
                .customerId(Integer.toString(user.getId()))
                .firstName(user.getName())
                .lastName(user.getSurname())
                .email(user.getMail())
                .id(String.valueOf(user.getId()))
                .done()
                .billingAddress()
                .locality(address.getCity())
                .streetAddress(address.getStreet())
                .extendedAddress("house: " + address.getHouse() + " flat: " + address.getFlat())
                .done()
                .amount(amount)
                .options()
                .submitForSettlement(true)
                .done();


        Result<Transaction> transactionResult = Gateway.getGateway().transaction().sale(transactionRequest);

        if(transactionResult.isSuccess()){
            order = orderService.makeOrderCashCard(order, user);
            cartService.clean(cart);
            return new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrderId(), HttpServletResponse.SC_OK);
        }else {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private CardOrder() {
    }
}