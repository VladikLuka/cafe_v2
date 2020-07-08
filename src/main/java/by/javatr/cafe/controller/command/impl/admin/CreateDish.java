package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IDishService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class CreateDish implements Command {

    @Autowired
    IDishService dishService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        int dish_id = Integer.parseInt(content.getRequestParam("dish_id"));
        String name = content.getRequestParam("name");
        String description = content.getRequestParam("description");
        BigDecimal price = new BigDecimal(content.getRequestParam("price"));
        boolean available = Boolean.parseBoolean(content.getRequestParam("available"));
        int weight = Integer.parseInt(content.getRequestParam("weight"));
        int category_id = Integer.parseInt(content.getRequestParam("category_id"));
        String picturePath = content.getRequestParam("picture_path");

        Dish dish = new Dish(dish_id, name, description, price, available, weight, category_id, picturePath);

        dish = dishService.create(dish);

        return new RequestResult(HttpServletResponse.SC_OK);
    }
}
