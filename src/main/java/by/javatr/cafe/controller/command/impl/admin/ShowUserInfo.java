package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;

@Component
public class ShowUserInfo implements Command {

    @Autowired
    IUserService userService;

    Gson gson = new Gson();

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        int id =Integer.parseInt(content.getRequestParam("id"));

        User user = userService.find(id);

        String json_user = gson.toJson(user);
        RequestResult result = new RequestResult(json_user, HttpServletResponse.SC_OK);
        result.setHeaders("content-type", "application/json;charset=UTF-8");
        return new RequestResult(gson.toJson(user), HttpServletResponse.SC_OK);
    }
}
