package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.Path;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;

import javax.servlet.http.HttpServletResponse;

/**
 * Class for processing user request.
 * Process invalid request
 */
@Component
public class WrongRequest implements Command {
    @Override
    public RequestResult execute(RequestContent content) {
        return new RequestResult(Navigation.FORWARD, Path.ERROR, HttpServletResponse.SC_NOT_FOUND);
    }
}
