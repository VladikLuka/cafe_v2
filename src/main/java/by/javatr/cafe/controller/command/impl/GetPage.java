package by.javatr.cafe.controller.command.impl;

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

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetPage implements Command {

    @Autowired
    IOrderService orderService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {


        String[] path = content.getRequestAttr("get_page").toString().split("/");

        ArrayList<String> pageParams = (ArrayList<String>)content.getRequestAttr("pageParams");

        if(path[0].equals("checkout") && path.length>1){
            User user = new User((int)content.getSessionAttr(SessionAttributes.USER_ID));
            final List<Order> orders = orderService.getOrders(user);
            for (Order order : orders) {
                if (order.getOrder_id() == Integer.parseInt(path[1])) {
                    content.addRequestAttr("order", order);
                }
            }
            if (content.getRequestAttr("order") == null) return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        RequestResult result =new RequestResult(Navigation.FORWARD, "WEB-INF/jsp/" + path[0] + ".jsp", HttpServletResponse.SC_OK);
        result.setHeaders("Location",(String) content.getRequestAttr("URL"));
        return result;
    }
}
