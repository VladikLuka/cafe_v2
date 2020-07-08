package by.javatr.cafe.page;

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

@Component
public class User implements Page {

    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private IUserService userService;

    private final String ACCESS = AccessLevel.USER;
    private final String PATH = Path.USER;

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

            Integer user_id = (Integer) req.getSession().getAttribute(SessionAttributes.USER_ID);

            if(user_id == null) req.getRequestDispatcher(Path.ERROR).forward(req, resp);

            by.javatr.cafe.entity.User user = userService.find(user_id);

            if(user.getRole().ordinal() > Role.getRoleByName(ACCESS).ordinal()){
                req.getRequestDispatcher(Path.ERROR).forward(req,resp);
            }else{
                req.getRequestDispatcher(PATH).forward(req,resp);
            }

        } catch (ServletException | IOException | ServiceException e) {
            logger.error(e);
        }

    }

}
