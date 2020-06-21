package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IDishService;

import javax.servlet.http.HttpServletResponse;

@Component
public class ShowDish implements Command {

    @Autowired
    IDishService dishService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException{

        int id = Integer.parseInt(content.getRequestParam("id"));

        Dish dish = new Dish(id);

        dishService.showDish(dish);

        return new RequestResult(HttpServletResponse.SC_OK);
    }
}
