package by.javatr.cafe.controller.command.impl;

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
import by.javatr.cafe.util.Utils;

import javax.servlet.http.HttpServletResponse;

/**
 * Class for processing user request.
 * Change user password
 */
@Component
public class ChangePass implements Command {

    @Autowired
    private IUserService userService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String newPass = Utils.hashPass(content.getRequestParam(RequestParameters.NEW_PASSWORD));

        User user = userService.find((int)content.getSessionAttr(SessionAttributes.USER_ID));

        user.setPassword(newPass);

        userService.update(user);

        return new RequestResult(Navigation.REDIRECT, "/user", HttpServletResponse.SC_OK);
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    private ChangePass() {
    }
}
