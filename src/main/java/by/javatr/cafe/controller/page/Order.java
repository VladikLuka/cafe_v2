package by.javatr.cafe.controller.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Cart;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * processes the request and transfers useful information to the jsp
 */
@Component
public class Order implements Page {

    private final Logger logger = LogManager.getLogger(getClass());

    private static final String ACCESS = AccessLevel.USER;
    private static final String PATH = Path.ORDER;
    private static final String ERROR = Path.ERROR;

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

    /**
     * check incoming url for an regex match
     * @param url request url
     * @return match or no
     */

    @Override
    public boolean thisURL(String url) {
        return url.matches("/order");
    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final HttpSession session = req.getSession();
            Integer user_id = (Integer) session.getAttribute(SessionAttributes.USER_ID);
            final Cart cart =(Cart) session.getAttribute("cart");

            if(cart != null){
                if(cart.getUserCart() != null){
                    final Map<Dish, Integer> dishAmount = Utils.countSame(cart.getUserCart());
                    req.setAttribute("dishes", dishAmount);
                }
            }

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
            try {
                req.getRequestDispatcher(ERROR).forward(req,resp);
            } catch (ServletException | IOException ex) {
                logger.error(ex);
            }
        }
    }
}
