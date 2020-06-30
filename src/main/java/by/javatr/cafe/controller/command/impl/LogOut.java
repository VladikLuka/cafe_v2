package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.exception.ServiceException;

import javax.servlet.http.HttpServletResponse;

@Component
public class LogOut implements Command {
    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        content.invalidate();

        content.addSessionAttr(SessionAttributes.USER_ID, null);
        content.addSessionAttr(SessionAttributes.CART, null);
        content.addSessionAttr(SessionAttributes.LOCALE, null);

        return new RequestResult(Navigation.REDIRECT,Path.PIZZA);

    }
}
