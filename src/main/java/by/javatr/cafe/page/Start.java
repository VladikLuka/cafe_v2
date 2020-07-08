package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.container.annotation.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Start implements Page {

    Logger logger = LogManager.getLogger();

    private final String ACCESS = AccessLevel.GUEST;

    private final String PATH = "/";

    public boolean thisURL(String url){
        return url.matches("/");
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
            resp.sendRedirect("/pizza");
        } catch (IOException e) {
            logger.error(e);
        }

    }

}
