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
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Class for processing user request.
 * Return dish from cart
 */
@Component
public class GetDishInCart implements Command {

    @Autowired
    private ICartService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        if(content.getSessionAttr(SessionAttributes.CART) != null){
            List<Dish> all = service.getAll((Cart) content.getSessionAttr(SessionAttributes.CART));
            return returnDish(all);
        }else {
            Cart cart = new Cart();
            content.addSessionAttr(SessionAttributes.CART, cart);
            return returnDish(cart.getUserCart());
        }
        
    }

    private RequestResult returnDish(List<Dish> list){
        Gson gson = new Gson();
        RequestResult result = new RequestResult();
        String s = gson.toJson(list);
        result.setCommand(s);
        result.setStatus(HttpServletResponse.SC_OK);
        return result;
    }

    private GetDishInCart() {}
}
