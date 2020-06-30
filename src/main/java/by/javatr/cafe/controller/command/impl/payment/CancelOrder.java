package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.service.impl.OrderService;

import javax.servlet.http.HttpServletResponse;

@Component
public class CancelOrder implements Command {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;


    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        int order_id = Integer.parseInt(content.getRequestParam("order_id"));

        int user_id =(int) content.getSessionAttr(SessionAttributes.USER_ID);

        Order order = new Order(order_id, user_id);

        if (orderService.cancelOrder(order)) {
            return new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrder_id(), HttpServletResponse.SC_OK);
        }

        return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

    }
}
