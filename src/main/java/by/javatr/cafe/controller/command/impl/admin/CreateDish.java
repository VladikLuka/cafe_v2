package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IDishService;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Class for processing admin request.
 * Create new dish
 */
@Component
public class CreateDish implements Command {

    @Autowired
    IDishService dishService;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String name = content.getRequestParam("dishName");
        String description = content.getRequestParam("dishDescription");
        BigDecimal price = new BigDecimal(content.getRequestParam("dishPrice"));
        int weight = Integer.parseInt(content.getRequestParam("dishWeight"));
        int categoryId = Integer.parseInt(content.getRequestParam("dishCategory"));
        String picturePath = content.getRequestParam("dishPicture");

        picturePath = picturePath.replaceAll("\\\\", "/");



//        InputStream stream = new FileInputStream("");
        final Dish dish = dishService.create(new Dish(name, description, price, false, weight, categoryId, picturePath));

        if(dish != null){
            return new RequestResult(HttpServletResponse.SC_OK);
        }else{
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private CreateDish() {
    }
}
