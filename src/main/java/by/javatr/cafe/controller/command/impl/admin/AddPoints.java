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
 * Add points to chosen user
 */
@Component
public class AddPoints implements Command {

    @Autowired
    IUserService userService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        final String pointsParam = content.getRequestParam("points");
        final int userId = Integer.parseInt(content.getRequestParam("user_id"));

        int points = Integer.parseInt(pointsParam);

        User user = userService.addPoints(points, userId);
        Gson gson = new Gson();

        return new RequestResult(gson.toJson(user), HttpServletResponse.SC_OK);
    }
}
