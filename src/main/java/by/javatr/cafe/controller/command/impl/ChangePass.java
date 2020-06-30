package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.util.Cache;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.util.HashPassword;

import javax.servlet.http.HttpServletResponse;

@Component
public class ChangePass implements Command {

    @Autowired
    IUserService userService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String old_pass = HashPassword.hashPass(content.getRequestParam("old_pass"));
        String new_pass = HashPassword.hashPass(content.getRequestParam("new_pass"));

        User user = userService.find((int)content.getSessionAttr(SessionAttributes.USER_ID));

        user.setPassword(new_pass);

        userService.update(user);

        return new RequestResult(Navigation.REDIRECT, "/user", HttpServletResponse.SC_OK);
    }
}
