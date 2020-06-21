package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;

import javax.servlet.http.HttpServletResponse;

@Component
public class Feedback implements Command {

    @Autowired
    IOrderService orderService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        final String stars = content.getRequestParam("stars");
        Integer rating = null;
        if(stars != null){
            rating = Integer.parseInt(stars);
        }
        final String feedback = content.getRequestParam("feedback");
        int order_id = Integer.parseInt(content.getRequestParam("order_id"));
        int user_id = (int)content.getSessionAttr(SessionAttributes.USER_ID);

        Order order = new Order(order_id, user_id);

        order = orderService.getOrder(order);

        order.setOrder_review(feedback);
        order.setOrder_rating(rating);

        orderService.updateOrder(order);

        return new RequestResult(Navigation.REDIRECT, "/checkout/" + order_id);
    }
}
