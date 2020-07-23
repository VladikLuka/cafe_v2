package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.ICartService;

import javax.servlet.http.HttpServletResponse;

/**
 * Class for processing user request.
 * Delete one dish from cart
 */
@Component
public class DeleteDishFromCart implements Command {

    @Autowired
    private ICartService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);

        int dishId = Integer.parseInt(content.getRequestParam(RequestParameters.ID));

        boolean b = service.deleteFromCart(cart, dishId);

        if(b){
            return new RequestResult(HttpServletResponse.SC_OK);
        }else return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);

    }
}
