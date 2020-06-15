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
import by.javatr.cafe.dao.Cache;
import by.javatr.cafe.entity.*;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.ICartService;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationErrors;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MakeOrderCard implements Command {

    @Autowired IUserService userService;
    @Autowired ICartService cartService;
    @Autowired IOrderService orderService;
    @Autowired
    Cache cache;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String pay_method = content.getRequestParam("payment_method_nonce");
        String address_id_str = content.getRequestParam("address");
        Address user_address = null;
        int address_id = Integer.parseInt(address_id_str);
        int user_id = (int) content.getSessionAttr(SessionAttributes.USER_ID);
        final User user = userService.find(user_id);
        Cart cart = (Cart)content.getSessionAttr(SessionAttributes.CART);

        List<Address> address1 = user.getAddress();

        for (Address addr:address1) {
            if(addr.getId() == address_id){
                user_address = addr;
                break;
            }
        }

        BigDecimal amount = orderService.amount(cart);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.paymentMethodNonce(pay_method)
                .customer()
                .customerId(Integer.valueOf(user.getId()).toString())
                .firstName(user.getName())
                .lastName(user.getSurname())
                .email(user.getMail())
                .id(String.valueOf(user_id))
                .done()
                .billingAddress()
                .locality(user_address.getCity())
                .streetAddress(user_address.getStreet())
                .extendedAddress("house: " + user_address.getHouse() + " flat: " + user_address.getFlat())
                .done()
                .amount(amount)
                .options()
                .submitForSettlement(true)
                .done();


        Result<Transaction> result = Gateway.getGateway().transaction().sale(transactionRequest);

        if(result.isSuccess()){

            Transaction target = result.getTarget();
            DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = instance.format(Calendar.getInstance().getTime());
            final Map<Dish, Integer> dishes = countSameDish(cart.getCart());
            System.out.println(dishes);
            Order order = new Order(PaymentMethod.CARD, PaymentStatus.PAID,format, dishes,user_id,target.getId());

            try {
                orderService.makeOrder(order);

            } catch (ServiceException e) {
                e.printStackTrace();
            }
            cartService.clear((Cart) cart);
            return new RequestResult(Navigation.REDIRECT, "/checkout/" + target.getId(), HttpServletResponse.SC_OK);
        }else{
            return new RequestResult(Navigation.REDIRECT, "/payment_error", HttpServletResponse.SC_BAD_REQUEST);
//            ValidationErrors errors = result.getErrors();
//            System.out.println(result.getMessage());
        }

//        return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
    }


    private Map<Dish, Integer> countSameDish(List<Dish> dishes){
        HashMap<Dish, Integer> map = new HashMap<>();

        map.put(dishes.get(0), 1);

        for (int i = 1; i < dishes.size(); i++) {
            Dish dish = dishes.get(i);
            if(map.containsKey(dishes.get(i))){
                Integer integer = map.get(dishes.get(i));
                map.put(dishes.get(i), integer + 1);
            }else{
                map.put(dish, 1);
            }
        }

        return map;
    }
}
