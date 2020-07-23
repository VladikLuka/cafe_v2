package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;

import javax.servlet.http.HttpServletResponse;

/**
 * Class for processing payment request.
 * Cancel order
 */
@Component
public class CancelOrder implements Command {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;


    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        int orderId = Integer.parseInt(content.getRequestParam(RequestParameters.ORDER_ID));

        int userId =(int) content.getSessionAttr(SessionAttributes.USER_ID);

        Order order = new Order(orderId, userId);

        if (orderService.cancelOrder(order)) {
            return new RequestResult(Navigation.REDIRECT, "/checkout/" + order.getOrderId(), HttpServletResponse.SC_OK);
        }

        return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

    }
}
