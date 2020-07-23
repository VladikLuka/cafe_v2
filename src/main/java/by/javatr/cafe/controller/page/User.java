package by.javatr.cafe.controller.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.exception.ServiceException;
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
public class User implements Page {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private IUserService userService;

    private static final String ACCESS = AccessLevel.USER;
    private static final String PATH = Path.USER;
    private static final String ERROR = Path.ERROR;
    /**
     * check incoming url for an regex match
     * @param url request url
     * @return match or no
     */

    public boolean thisURL(String url){
        return url.matches("/user");
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
            
            Integer userId = (Integer) req.getSession().getAttribute(SessionAttributes.USER_ID);

            if(userId == null) {
                req.getRequestDispatcher(Path.ERROR).forward(req, resp);
                return;
            }

            resp.setHeader("Location",(String) req.getAttribute("URL"));

            by.javatr.cafe.entity.User user = userService.find(userId);

            if(user.getRole().ordinal() > Role.getRoleByName(ACCESS).ordinal()){
                req.getRequestDispatcher(Path.ERROR).forward(req,resp);
            }else{
                req.getRequestDispatcher(PATH).forward(req,resp);
            }

        } catch (ServletException | IOException e) {
            try {
                req.getRequestDispatcher(ERROR).forward(req,resp);
            } catch (ServletException | IOException ex) {
                logger.error(ex);
            }
            logger.error(e);
        } catch (ServiceException e) {
            logger.error(e);
        }

    }

}
