package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;

public class MakeUserAdmin implements Command {

    @Autowired
    IUserService userService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        final String userId = content.getRequestParam(RequestParameters.USER_ID);

        

        return null;
    }
}
