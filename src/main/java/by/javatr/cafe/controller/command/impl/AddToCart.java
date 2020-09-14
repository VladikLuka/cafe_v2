package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.ICartService;
import by.javatr.cafe.service.IDishService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;


/**
 * Class for processing user request.
 * Add dish to user cart
 */
@Component
public class AddToCart implements Command {
    @Autowired
    private IDishService dishService;
    @Autowired
    private ICartService cartService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String id = content.getRequestParam(RequestParameters.ID);

        Dish dish = dishService.get(Integer.parseInt(id));

        Gson gson = new Gson();

        if(content.getSessionAttr(SessionAttributes.CART) == null){
            Cart cart = new Cart();
            content.addSessionAttr(SessionAttributes.CART, cart);
            cartService.addToCart(cart, dish);
        }else{
            Cart cart = (Cart) content.getSessionAttr(SessionAttributes.CART);
            cartService.addToCart(cart, dish);
        }

        String s = gson.toJson(dish);
        RequestResult result = new RequestResult(s, HttpServletResponse.SC_OK);
        result.setHeaders("content-type", "application/json;charset=UTF-8");
        return result;
    }

    public void setDishService(IDishService dishService) {
        this.dishService = dishService;
    }

    public void setCartService(ICartService cartService) {
        this.cartService = cartService;
    }

    private AddToCart() {
    }
}
