package by.javatr.cafe.controller.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.container.annotation.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DishPageError implements Page {
    Logger logger = LogManager.getLogger(getClass());

    private final  String ACCESS = AccessLevel.GUEST;
    private final  String PATH = Path.PIZZA;

    public boolean thisURL(String url){
        return url.matches("/pizza/") || url.matches("/pizza")
                || url.matches("/garnish/") || url.matches("/garnish")
                || url.matches("/drink/") || url.matches("/drink")
                || url.matches("/salad/") || url.matches("/salad")
                || url.matches("/sushi/") || url.matches("/sushi")
                || url.matches("/meat/") || url.matches("/meat");
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
            String[] str = req.getRequestURI().split("/");
            resp.sendRedirect("/" + str[1] + "/1");
        } catch (IOException e) {
            logger.error(e);
        }

    }
}
