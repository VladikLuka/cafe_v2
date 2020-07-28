package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IDishService;

import javax.servlet.http.HttpServletResponse;


@Component
public class DeleteDish implements Command {

    @Autowired
    private IDishService dishService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        int dishId = Integer.parseInt(content.getRequestParam(RequestParameters.ID));

        boolean delete = dishService.delete(new Dish(dishId));

        if(delete){
            return new RequestResult(Navigation.REDIRECT,(String) content.getRequestAttr("URI"),HttpServletResponse.SC_OK);
        }else {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
