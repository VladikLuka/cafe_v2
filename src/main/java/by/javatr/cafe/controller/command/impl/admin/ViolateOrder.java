package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.constant.PaymentStatus;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;

import javax.servlet.http.HttpServletResponse;

/**
 * Class for processing admin request.
 * Violate order
 */
@Component
public class ViolateOrder implements Command {

    @Autowired
    IOrderService orderService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        final String id = content.getRequestParam("id");
        int orderId = Integer.parseInt(id);

        Order order = new Order(orderId);

        orderService.violateOrder(order);

        order = orderService.getOrder(order);

        order.setStatus(PaymentStatus.VIOLATED);

        try {
            orderService.updateOrder(order);
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }

        return new RequestResult(HttpServletResponse.SC_OK);
    }

    private ViolateOrder() {
    }
}
