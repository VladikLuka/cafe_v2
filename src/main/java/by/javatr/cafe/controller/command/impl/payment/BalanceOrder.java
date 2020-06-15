package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.constant.PaymentMethod;
import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.entity.*;
import by.javatr.cafe.exception.DAOException;
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
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BalanceOrder implements Command {

    @Autowired
    IOrderService orderService;
    @Autowired
    IUserService userService;
    @Autowired
    ICartService cartService;


    @Override
    public RequestResult execute(RequestContent content) throws ServiceException, DAOException {

        String address_id_str = content.getRequestParam("address");
        Address user_address = null;
        int address_id = Integer.parseInt(address_id_str);
        int user_id = (int) content.getSessionAttr(SessionAttributes.USER_ID);
        final User user = userService.find(user_id);
        Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        List<Address> address1 = user.getAddress();

        for (Address addr : address1) {
            if (addr.getId() == address_id) {
                user_address = addr;
                break;
            }
        }

        BigDecimal amount = orderService.amount(cart);
        amount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal money = user.getMoney();
        money.setScale(2, RoundingMode.HALF_UP);

        DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = instance.format(Calendar.getInstance().getTime());
        final Map<Dish, Integer> dishes = countSameDish(cart.getCart());
        System.out.println(dishes);
        Order order = new Order();
        order.setMethod(PaymentMethod.BALANCE);
        order.setDishes(dishes);
        order.setUser_id(user_id);
        order.setTime(format);
        order.setAmount(amount);
        order.setStatus(PaymentStatus.EXPECTED);

        if (money.compareTo(amount) != -1) {
            orderService.makeOrderBalance(order);
            cartService.clear((Cart) cart);
            final User user1 = userService.find(user_id);
            content.addSessionAttr(SessionAttributes.USER_MONEY, user1.getMoney());
        } else {
            return new RequestResult(Navigation.REDIRECT, "/payment_error", HttpServletResponse.SC_BAD_REQUEST);
        }

        return new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrder_id());
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
