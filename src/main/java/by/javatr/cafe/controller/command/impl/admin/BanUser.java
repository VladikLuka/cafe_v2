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
 * Ban chosen user
 */
@Component
public class BanUser implements Command {

    @Autowired
    IUserService userService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        int id = Integer.parseInt(content.getRequestParam("user_id"));

        User user = null;
        try {
            user = userService.banUser(id);
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }

        Gson gson = new Gson();

        return new RequestResult(gson.toJson(user), HttpServletResponse.SC_OK);
    }
}
