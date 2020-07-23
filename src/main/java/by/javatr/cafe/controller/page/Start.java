package by.javatr.cafe.controller.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.container.annotation.Component;
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
public class Start implements Page {

    private final Logger logger = LogManager.getLogger();

    private static final String ACCESS = AccessLevel.GUEST;
    private static final String PATH = "/";
    private static final String ERROR = Path.ERROR;
    /**
     * check incoming url for an regex match
     * @param url request url
     * @return match or no
     */

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
        }  catch (IOException e) {
            try {
                req.getRequestDispatcher(ERROR).forward(req,resp);
            } catch (ServletException | IOException ex) {
                logger.error(ex);
            }
            logger.error(e);
        }

    }

}
