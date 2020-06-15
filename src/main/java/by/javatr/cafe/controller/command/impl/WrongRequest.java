package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;

import javax.servlet.http.HttpServletResponse;

@Component
public class WrongRequest implements Command {

    @Override
    public RequestResult execute(RequestContent content) {
        return new RequestResult(Navigation.FORWARD, "WEB-INF/jsp/error.jsp", HttpServletResponse.SC_NOT_FOUND);
    }
}
