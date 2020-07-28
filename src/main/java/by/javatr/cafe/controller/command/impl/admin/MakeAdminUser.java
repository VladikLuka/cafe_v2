package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.constant.RequestParameters;
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
public class MakeAdminUser implements Command {

    @Autowired
    IUserService userService;


    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {
        final int userId = Integer.parseInt(content.getRequestParam(RequestParameters.USER_ID));
        Gson gson = new Gson();


        final User user = userService.makeUser(userId);

        if(user!=null){
            return new RequestResult(gson.toJson(user) ,HttpServletResponse.SC_OK);
        }else {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private MakeAdminUser() {
    }
}
