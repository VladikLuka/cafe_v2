package by.javatr.cafe.controller.command.impl;

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

@Component
public class AddToCart implements Command {
    @Autowired
    IDishService service;

    @Autowired
    ICartService cartService;

    Gson gson = new Gson();

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        String id = content.getRequestParam("id");

        Dish dish = service.getDish(Integer.parseInt(id));


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
}
