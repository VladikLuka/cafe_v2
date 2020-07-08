package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;
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

@Component
public class Meat implements Page {

    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    IDishService dishService;
    @Autowired
    IUserService userService;

    private static final String ACCESS = AccessLevel.GUEST;

    private static final String PATH = Path.MEAT;

    public boolean thisURL(String url){
        return url.matches("/meat/[\\d]+");
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
        test(req, resp, dishService, userService, 5, PATH, logger);
    }

}
