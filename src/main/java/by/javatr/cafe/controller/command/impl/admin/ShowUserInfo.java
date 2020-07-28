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

/**
 * Class for processing admin request.
 * Show information about chosen user
 */
@Component
public class ShowUserInfo implements Command {

    @Autowired
    IUserService userService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        int id =Integer.parseInt(content.getRequestParam("id"));
        Gson gson = new Gson();

        User user = userService.find(id);

        if(user==null){
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        String jsonUser = gson.toJson(user);
        RequestResult result = new RequestResult(jsonUser, HttpServletResponse.SC_OK);
        result.setHeaders("content-type", "application/json;charset=UTF-8");
        return new RequestResult(gson.toJson(user), HttpServletResponse.SC_OK);
    }

    private ShowUserInfo() {
    }
}
