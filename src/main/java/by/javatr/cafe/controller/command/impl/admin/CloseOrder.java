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
 * Close expected or paid order
 */
@Component
public class CloseOrder implements Command {

    @Autowired
    IOrderService orderService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

         String id = content.getRequestParam("id");
        int orderId = Integer.parseInt(id);

        Order order = new Order(orderId);

        order = orderService.getOrder(order);

        order.setStatus(PaymentStatus.CLOSED);

        orderService.updateOrder(order);



        return new RequestResult(HttpServletResponse.SC_OK);
    }
}
