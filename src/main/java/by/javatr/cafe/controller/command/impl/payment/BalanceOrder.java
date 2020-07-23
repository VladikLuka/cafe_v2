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
import by.javatr.cafe.entity.*;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.ICartService;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.javatr.cafe.util.Utils.countSame;

/**
 * Class for processing payment request.
 * Make an order from client account
 */
@Component
public class BalanceOrder implements Command {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICartService cartService;



    @Override
    public RequestResult execute(RequestContent content) throws ServiceException   {

        String deliveryTime = content.getRequestParam(RequestParameters.TIME_DELIVERY);
        String addressIdStr = content.getRequestParam(RequestParameters.ADDRESS);
        int addressId = Integer.parseInt(addressIdStr);

        long delTime;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            delTime = simpleDateFormat.parse(deliveryTime).getTime();
        } catch (ParseException e) {
            throw new ServiceException(e);
        }

        User user = userService.find((int)content.getSessionAttr(SessionAttributes.USER_ID));


        Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        List<Address> address1 = user.getAddress();

        Address userAddress = null;

        for (Address addr : address1) {
            if (addr.getId() == addressId) {
                userAddress = addr;
                break;
            }
        }

        if(userAddress == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        BigDecimal amount = cartService.amount(cart);
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal money = user.getMoney();
        money = money.setScale(2, RoundingMode.HALF_UP);

        DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = instance.format(Calendar.getInstance().getTime());

        long time = Calendar.getInstance().getTime().getTime();

        if(time > delTime){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(delTime - time > 259_200_000){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        final Map<Dish, Integer> dishes = countSame(cart.getUserCart());
        Order order = new Order(PaymentMethod.BALANCE, dishes, user.getId(), format, deliveryTime, userAddress, amount,PaymentStatus.PAID);

        if (money.compareTo(amount) >= 0) {
                order = orderService.makeOrderBalance(order, user);
                if(order == null){
                    return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
                }
                cartService.clean(cart);
            }
        else {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }
        return new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrderId(), HttpServletResponse.SC_FOUND);
    }
}
