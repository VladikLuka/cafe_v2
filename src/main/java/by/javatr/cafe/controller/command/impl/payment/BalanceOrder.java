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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.javatr.cafe.util.Utils.countSameDish;

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

        String delivery_time = content.getRequestParam("delivery_time");
        System.out.println(delivery_time);
        String address_id_str = content.getRequestParam("address");
        int address_id = Integer.parseInt(address_id_str);

        if(!delivery_time.matches("^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})")){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }
        long del_time;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            del_time = simpleDateFormat.parse(delivery_time).getTime();
//            del_time = SimpleDateFormat.getInstance().parse(delivery_time).getTime();
        } catch (ParseException e) {
            throw new ServiceException(e);
        }

        User user = userService.find((int)content.getSessionAttr(SessionAttributes.USER_ID));


        Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        List<Address> address1 = user.getAddress();

        Address user_address = null;

        for (Address addr : address1) {
            if (addr.getId() == address_id) {
                user_address = addr;
                break;
            }
        }

        if(cart.getCart() == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(user_address == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        BigDecimal amount = orderService.amount(cart);
        amount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal money = user.getMoney();
        money.setScale(2, RoundingMode.HALF_UP);

        DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = instance.format(Calendar.getInstance().getTime());

        long time = Calendar.getInstance().getTime().getTime();

        if(time > del_time){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        if(del_time - time > 259_200_000){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        final Map<Dish, Integer> dishes = countSameDish(cart.getCart());
        Order order = new Order(PaymentMethod.BALANCE, dishes, user.getId(), format, delivery_time, user_address, amount,PaymentStatus.PAID);

        if (money.compareTo(amount) >= 0) {
                order = orderService.makeOrderBalance(order, user);
                if(order == null){
                    return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
                }
                cartService.clear(cart);
            }
        else {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }
        return new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrder_id(), HttpServletResponse.SC_FOUND);
    }
}
