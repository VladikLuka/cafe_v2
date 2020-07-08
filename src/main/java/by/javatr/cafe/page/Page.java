package by.javatr.cafe.page;


import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IDishService;
import by.javatr.cafe.service.IUserService;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Page {

    int DISH_ON_PAGE = 8;

    String getAccessLevel();

    String getPath();

    void process(HttpServletRequest req, HttpServletResponse resp);

    boolean thisURL(String regex);


    default void test(HttpServletRequest req, HttpServletResponse resp, IDishService dishService, IUserService userService,int category_id ,String PATH, Logger logger){
        try {
            final String url =(String) req.getAttribute("URL");
            final String[] split = url.split("/");

            int page_id = Integer.parseInt(split[2]);

            int current_page = page_id;
            page_id -= 1;

            final List<Dish> dishes = dishService.getDishes();

            List<Dish> drinks = new ArrayList<>();

            for (Dish dish :dishes) {
                if(dish.getCategory_id() == category_id){
                    drinks.add(dish);
                }
            }

            if(page_id * DISH_ON_PAGE >= drinks.size()){
                req.getRequestDispatcher(Path.ERROR).forward(req,resp);
            }

            List<Dish> page_dishes = new ArrayList<>();

            if(req.getSession().getAttribute(SessionAttributes.USER_ID) == null){
                processDish(page_id, drinks, req, page_dishes);
            }

            if(req.getSession().getAttribute(SessionAttributes.USER_ID) != null){
                int user_id = (int) req.getSession().getAttribute(SessionAttributes.USER_ID);
                User user = userService.find(user_id);

                if(user.getRole().equals(Role.USER)){

                    processDish(page_id, drinks, req, page_dishes);

                }else if(user.getRole().equals(Role.ADMIN)){
                    float f = (float) drinks.size() / DISH_ON_PAGE;
                    final double ceil = Math.ceil(f);
                    long pages = Math.round(ceil);
                    req.setAttribute("pages", pages);

                    for (int i = page_id * DISH_ON_PAGE; i < page_id * DISH_ON_PAGE + DISH_ON_PAGE; i++) {
                        if(i >= drinks.size()){
                            break;
                        }
                        page_dishes.add(drinks.get(i));
                    }

                }
            }

            req.setAttribute("current_page", current_page);
            req.setAttribute("drinks", page_dishes);

            req.getRequestDispatcher(PATH).forward(req,resp);
        } catch (ServletException | IOException e) {
            logger.error(e);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    default void processDish(int page_id , List<Dish> drinks, HttpServletRequest req, List<Dish> page_dishes){
        int ctr = page_id * DISH_ON_PAGE;

        List<Dish> available_drink = new ArrayList<>();
        for (Dish dish :drinks) {
            if(dish.isAvailable()){
                available_drink.add(dish);
            }
        }

        float f = (float) available_drink.size() / DISH_ON_PAGE;
        final double ceil = Math.ceil(f);
        long pages = Math.round(ceil);
        req.setAttribute("pages", pages);

        for (int i = 0; i < drinks.size(); i++) {
            if(drinks.get(i).isAvailable()){
                ctr -= 1;
                if(ctr < 0){
                    page_dishes.add(drinks.get(i));
                }
            }
            if(page_dishes.size() == 8){
                break;
            }
        }
    }

}
