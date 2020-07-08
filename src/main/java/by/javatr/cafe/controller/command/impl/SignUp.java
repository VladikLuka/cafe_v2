package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

@Component
public class SignUp implements Command {

    @Autowired
    private IUserService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        User user = new User(content.getRequestParam("name"), content.getRequestParam("surname"), content.getRequestParam("email"), content.getRequestParam("password"), content.getRequestParam("phone"));

        User user1 = service.createUser(user);

        if(user1 == null){
            return  new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }   

        return new RequestResult(Navigation.REDIRECT, Path.URL_PIZZA, HttpServletResponse.SC_OK);
    }

}
