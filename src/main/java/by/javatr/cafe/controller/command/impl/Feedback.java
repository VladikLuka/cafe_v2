package by.javatr.cafe.controller.command.impl;

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


/**
 * Class for processing user request.
 * Add user feedback to order
 */
@Component
public class Feedback implements Command {

    @Autowired
    private IOrderService orderService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        int rating = Integer.parseInt(content.getRequestParam(RequestParameters.ORDER_RATING));
        String feedback = content.getRequestParam(RequestParameters.ORDER_FEEDBACK);
        int orderId = Integer.parseInt(content.getRequestParam(RequestParameters.ORDER_ID));
        int userId = (int)content.getSessionAttr(SessionAttributes.USER_ID);

        Order order = new Order(orderId, userId);

        order = orderService.getOrder(order);

        order.setOrderReview(feedback);
        order.setOrderRating(rating);

        orderService.updateOrder(order);

        return new RequestResult(Navigation.REDIRECT, "/checkout/" + orderId);
    }

    private Feedback() {
    }
}
