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

@Component
public class GetDishInCart implements Command {

    @Autowired
    ICartService service;

    Gson gson = new Gson();

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        RequestResult result = new RequestResult();
        if(content.getSessionAttr(SessionAttributes.CART) != null){
            List<Dish> all = service.getAll((Cart) content.getSessionAttr(SessionAttributes.CART));
            String str = gson.toJson(all);
            result.setCommand(str);
            result.setStatus(HttpServletResponse.SC_OK);
            return result;
        }else {
            Cart cart = new Cart();
            content.addSessionAttr(SessionAttributes.CART, cart);
            String s = gson.toJson(cart.getCart());
            result.setCommand(s);
            result.setStatus(HttpServletResponse.SC_OK);
            return result;
        }
        
    }

    public GetDishInCart() {
    }
}
