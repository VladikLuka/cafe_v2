package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.RequestParameters;
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
import by.javatr.cafe.service.IUserService;

import javax.servlet.http.HttpServletResponse;

/**
 * Class for processing payment request.
 * Close credit from user account
 */
@Component
public class CloseCreditBalance implements Command {

    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        int user_id = Integer.parseInt(content.getRequestParam(RequestParameters.USER_ID));
        int order_id = Integer.parseInt(content.getRequestParam(RequestParameters.ORDER_ID));

        Order order = new Order(order_id, user_id);

        order = orderService.getOrder(order);

        try {
            orderService.closeCredit(order);
        } catch (DAOException e) {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        RequestResult result = new RequestResult(Navigation.REDIRECT, Path.URL_USER, HttpServletResponse.SC_OK);
        result.setHeaders("Location", Path.URL_USER);
        return new RequestResult(Navigation.REDIRECT, Path.URL_USER, HttpServletResponse.SC_OK);

    }
}
