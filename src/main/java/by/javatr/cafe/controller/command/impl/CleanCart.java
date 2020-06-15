package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Cart;

import javax.servlet.http.HttpServletResponse;

@Component
public class CleanCart implements Command {
    @Override
    public RequestResult execute(RequestContent content) {

        Cart sessionAttr = (Cart) content.getSessionAttr(SessionAttributes.CART);

        sessionAttr.getCart().clear();

        return new RequestResult(HttpServletResponse.SC_OK);
    }
}
