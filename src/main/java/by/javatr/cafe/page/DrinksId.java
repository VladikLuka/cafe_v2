package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IDishService;
import by.javatr.cafe.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DrinksId implements Page {

    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    IDishService dishService;
    @Autowired
    IUserService userService;

    private final String ACCESS = AccessLevel.GUEST;
    private final int DISH_ON_PAGE = 8;

    private final String PATH = "/WEB-INF/jsp/drink.jsp";

    public boolean thisURL(String url){
        return url.matches("/drink/[\\d]*") || url.matches("/drink/[\\d]*/");
    }

    @Override
    public String getAccessLevel() {
        return ACCESS;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) {
        test(req, resp, dishService, userService, 1, PATH, logger);
    }


//    private void processDish(int page_id , List<Dish> drinks, HttpServletRequest req, List<Dish> page_dishes){
//        int ctr = page_id * DISH_ON_PAGE;
//
//        List<Dish> available_drink = new ArrayList<>();
//        for (Dish dish :drinks) {
//            if(dish.isAvailable()){
//                available_drink.add(dish);
//            }
//        }
//
//        float f = (float) available_drink.size() / DISH_ON_PAGE;
//        final double ceil = Math.ceil(f);
//        long pages = Math.round(ceil);
//        req.setAttribute("pages", pages);
//
//        for (int i = 0; i < drinks.size(); i++) {
//            if(drinks.get(i).isAvailable()){
//                ctr -= 1;
//                if(ctr < 0){
//                    page_dishes.add(drinks.get(i));
//                }
//            }
//            if(page_dishes.size() == 8){
//                break;
//            }
//        }
//    }

}
