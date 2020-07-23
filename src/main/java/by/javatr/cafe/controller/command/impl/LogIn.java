package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.constant.SessionAttributes;
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
 * Login user to application
 */
@Component
public class LogIn implements Command {

    private static final String USER_ID = SessionAttributes.USER_ID;
    private static final String LOGIN_EMAIL = RequestParameters.LOGIN_EMAIL;
    private static final String LOGIN_PASSWORD = RequestParameters.LOGIN_PASSWORD;

    @Autowired
    private IUserService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        User userBean = new User();
        userBean.setMail(content.getRequestParam(LOGIN_EMAIL));
        userBean.setPassword(content.getRequestParam(LOGIN_PASSWORD));

        User user = service.loginUser(userBean);
        if(user == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }else{
            content.addSessionAttr(USER_ID, user.getId());
            return new RequestResult(Navigation.REDIRECT, Path.URL_PIZZA);
        }
    }
}
