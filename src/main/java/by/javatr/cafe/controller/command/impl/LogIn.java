package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;

import javax.servlet.http.HttpServletResponse;

@Component
public class LogIn implements Command {

    private static final String USER_EMAIL = SessionAttributes.USER_EMAIL;
    private static final String USER_PASSWORD = SessionAttributes.USER_PASSWORD;
    private static final String USER_ID = SessionAttributes.USER_ID;
    private static final String ACCESS_LEVEL = SessionAttributes.ACCESS_LEVEL;
    private static final String LOGIN_EMAIL = RequestParameters.LOGIN_EMAIL;
    private static final String LOGIN_PASSWORD = RequestParameters.LOGIN_PASSWORD;
    private static final String USER_MONEY = SessionAttributes.USER_MONEY;

    @Autowired
    IUserService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        User userBean = new User();
        userBean.setMail(content.getRequestParam(LOGIN_EMAIL));
        userBean.setPassword(content.getRequestParam(LOGIN_PASSWORD));

        User user = service.loginUser(userBean);
        if(user == null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }else{
            content.addSessionAttr(USER_EMAIL, user.getMail());
            content.addSessionAttr(USER_ID, user.getId());
            content.addSessionAttr(ACCESS_LEVEL, user.getRole().name());
            content.addSessionAttr(USER_MONEY, user.getMoney());
             return new RequestResult(Navigation.REDIRECT, Path.PIZZA);
        }
    }
}
