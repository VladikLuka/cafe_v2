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

/**
 * Class for processing admin request.
 * Add money to chosen user
 */
@Component
public class AddMoney implements Command {

    @Autowired
    private IUserService userService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        final String amount = content.getRequestParam("amount");
        final int userId = Integer.parseInt(content.getRequestParam("user_id"));

        BigDecimal addMoney = new BigDecimal(amount);

        User user = userService.addMoney(addMoney, userId);
        Gson gson = new Gson();

        RequestResult result = new RequestResult(gson.toJson(user), HttpServletResponse.SC_OK);
        result.setHeaders("content-type", "application/json;charset=UTF-8");
        return new RequestResult(gson.toJson(user), HttpServletResponse.SC_OK);
    }

    private AddMoney() {
    }
}
