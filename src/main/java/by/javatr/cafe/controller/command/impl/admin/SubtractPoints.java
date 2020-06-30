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
import java.math.BigDecimal;

@Component
public class SubtractPoints implements Command {

    @Autowired
    IUserService userService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        final String subst_amount = content.getRequestParam("points");
        final String user_id = content.getRequestParam("user_id");

        final int points = Integer.parseInt(subst_amount);
        int id = Integer.parseInt(user_id);

        final User user = userService.subtractPoints(points, id);

        Gson gson = new Gson();

        RequestResult result = new RequestResult(gson.toJson(user), HttpServletResponse.SC_OK);
        result.setHeaders("content-type", "application/json;charset=UTF-8");
        return new RequestResult(gson.toJson(user), HttpServletResponse.SC_OK);
    }
}
