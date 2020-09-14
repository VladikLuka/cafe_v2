package by.javatr.cafe.controller.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Category;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.service.IDishService;
import by.javatr.cafe.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * processes the request and transfers useful information to the jsp
 */
@Component
public class Pizza implements Page {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private IDishService dishService;
    @Autowired
    private IUserService userService;

    private static final String ACCESS = AccessLevel.GUEST;
    private static final String ERROR = Path.ERROR;
    private static final String PATH = Path.PIZZA;
    /**
     * check incoming url for an regex match
     * @param url request url
     * @return match or no
     */

    public boolean thisURL(String url){
        return url.matches("/pizza/[\\d]+");
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
        try {
            displayDishes(req,resp,dishService,userService, Category.PIZZA, PATH, logger);
        } catch (ServletException | IOException e) {
            try {
                req.getRequestDispatcher(ERROR).forward(req,resp);
            } catch (ServletException | IOException ex) {
                logger.error(ex);
            }
            logger.error(e);
        }
    }

}

