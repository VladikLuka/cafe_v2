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
import by.javatr.cafe.util.Cache;
import by.javatr.cafe.entity.*;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.ICartService;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.javatr.cafe.util.Utils.countSameDish;

@Component
public class BalanceOrder implements Command {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private Cache cache;


    @Override
    public RequestResult execute(RequestContent content) throws ServiceException   {

        String address_id_str = content.getRequestParam("address");
        int address_id = Integer.parseInt(address_id_str);
        User user = cache.getUser((int)content.getSessionAttr(SessionAttributes.USER_ID));

        Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        List<Address> address1 = user.getAddress();

        Address user_address = null;

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
        Order order = new Order();
        order.setMethod(PaymentMethod.BALANCE);
        order.setDishes(dishes);
        order.setUser_id(user.getId());
        order.setTime(format);
        order.setDelivery_time(format);
        order.setAddress(user_address);
        order.setAmount(amount);
        order.setStatus(PaymentStatus.PAID);

        if (money.compareTo(amount) >= 0) {
                order = orderService.makeOrderBalance(order, user);
                if(order == null){
                    return new RequestResult(Navigation.REDIRECT, "/payment_error", HttpServletResponse.SC_BAD_REQUEST);
                }
                cartService.clear(cart);
                cache.addOrder(order);
            }
        else {
            return new RequestResult(Navigation.REDIRECT, "/payment_error", HttpServletResponse.SC_BAD_REQUEST);
        }
        RequestResult result = new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrder_id(), HttpServletResponse.SC_FOUND);
        result.setHeaders("Location", "/checkout/" + order.getOrder_id() );
        result.setHeaders("content-type", "application/json;charset=UTF-8");
        return result;
    }
}
