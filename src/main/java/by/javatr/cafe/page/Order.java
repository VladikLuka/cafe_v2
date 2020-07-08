package by.javatr.cafe.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Order implements Page {

    private static final String ACCESS = AccessLevel.USER;
    private static final String PATH = "WEB-INF/jsp/order.jsp";
    private static final String ERROR = "WEB-INF/jsp/error.jsp";

    @Autowired
    private IUserService userService;


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

            if(user_id == null) req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);

            User user = userService.find(user_id);

            if(user.getRole().ordinal() > Role.getRoleByName(ACCESS).ordinal()){
                req.getRequestDispatcher(ERROR).forward(req,resp);
            }else{
                req.getRequestDispatcher(PATH).forward(req,resp);
            }

        } catch (ServletException | IOException | ServiceException e) {

        }
    }

    @Override
    public boolean thisURL(String url) {
        return url.matches("/order");
    }
}
