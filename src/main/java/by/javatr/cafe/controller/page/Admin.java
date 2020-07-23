package by.javatr.cafe.controller.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
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
public class Admin implements Page {

    private final Logger logger = LogManager.getLogger(Admin.class);

    @Autowired
    private IUserService userService;

    /**
     * page access level
     */
    private static final String ACCESS = AccessLevel.ADMIN;
    /**
     * path to jsp
     */
    private static final String PATH = Path.ADMIN;
    /**
     *  path to error
     */
    private static final String ERROR = Path.ERROR;

    /**
     * check incoming url for an regex match
     * @param url request url
     * @return match or no
     */
    public boolean thisURL(String url){
        return url.matches("/admin");
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
    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Integer user_id = (Integer) req.getSession().getAttribute(SessionAttributes.USER_ID);

            if(user_id == null) {
                req.getRequestDispatcher(ERROR).forward(req, resp);
                return;
            }

            User user = userService.find(user_id);

            if(user.getRole().ordinal() > Role.getRoleByName(ACCESS).ordinal()){
                req.getRequestDispatcher(ERROR).forward(req,resp);
            }else{
                req.getRequestDispatcher(PATH).forward(req,resp);
            }

        } catch (ServletException | IOException | ServiceException e) {
            req.getRequestDispatcher(ERROR).forward(req, resp);
            logger.error(e);
        }

    }
}
