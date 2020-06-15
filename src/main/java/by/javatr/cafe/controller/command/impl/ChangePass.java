package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.service.impl.UserService;

public class ChangePass implements Command {
    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String old_pass = content.getRequestParam("old_pass");
        String new_pass = content.getRequestParam("new_pass");

        User user = new User();
        IUserService userService = UserService.getInstance();

        return new RequestResult();
    }
}
