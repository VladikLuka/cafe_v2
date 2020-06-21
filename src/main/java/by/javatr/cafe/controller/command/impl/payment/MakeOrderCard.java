package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.util.Cache;
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

@Component
public class MakeOrderCard implements Command {

    @Autowired
    IUserService userService;
    @Autowired
    ICartService cartService;
    @Autowired
    IOrderService orderService;
    @Autowired
    Cache cache;


    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String pay_method = content.getRequestParam("payment_method_nonce");
        int address_id = Integer.parseInt(content.getRequestParam("address"));
        int user_id =(int) content.getSessionAttr(SessionAttributes.USER_ID);

        User user = cache.getUser(user_id);
        final List<Address> addresses = user.getAddress();
        Address address = null;


        for (Address addr:addresses) {
                if(addr.getId() == address_id){
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

        Map<Dish, Integer> dishOrder = Utils.countSameDish(cart.getCart());

        BigDecimal amount  = Utils.amount(cart.getCart()).setScale(2, RoundingMode.HALF_UP);

        Order order = new Order(PaymentMethod.CARD, PaymentStatus.PAID, time, time, dishOrder, address, amount ,user_id);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.paymentMethodNonce(pay_method)
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
            order = orderService.makeOrder(order, user);
            cartService.clear(cart);
            return new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrder_id(), HttpServletResponse.SC_OK);
        }else {
            System.out.println(transactionResult.getMessage());
            return new RequestResult(Navigation.REDIRECT, "/error_payment", HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}