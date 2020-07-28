package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;

import javax.servlet.http.HttpServletResponse;

/**
 * Class for processing user request.
 * Sign up user
 */
@Component
public class SignUp implements Command {

    @Autowired
    private IUserService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        User user = new User(content.getRequestParam(RequestParameters.USER_NAME),
                content.getRequestParam(RequestParameters.USER_SURNAME),
                content.getRequestParam(RequestParameters.USER_EMAIL),
                content.getRequestParam(RequestParameters.USER_PASSWORD),
                content.getRequestParam(RequestParameters.USER_PHONE));

        User user1 = service.createUser(user);

        if(user1 == null){
            return  new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }   

        return new RequestResult(Navigation.REDIRECT, Path.URL_PIZZA, HttpServletResponse.SC_OK);
    }

    private SignUp() {
    }
}
