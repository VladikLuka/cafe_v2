package by.javatr.cafe.controller.page;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IOrderService;
import by.javatr.cafe.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Processes the request and transfers useful information to the jsp
 */
@Component
public class Checkout implements Page {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;

    private static final String ACCESS = AccessLevel.USER;
    private static final String ERROR = Path.ERROR;
    private static final String PATH = Path.CHECKOUT;

    /**
     * check incoming url for an regex match
     * @param url request url
     * @return match or no
     */

    public boolean thisURL(String url){
        return url.matches("/checkout/[\\d]+");
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
            if(req.getSession().getAttribute(SessionAttributes.USER_ID) == null){
                req.getRequestDispatcher(Path.ERROR).forward(req, resp);
                return;
            }

            resp.setHeader("Location",(String) req.getAttribute("URL"));

            final String URI = req.getRequestURI();

            final String[] split = URI.split("/");
            String id_str = split[2];

            int id = Integer.parseInt(id_str);

            Order order = orderService.getOrder(new Order(id));

            if(order != null){

                int user_id = (int) req.getSession().getAttribute(SessionAttributes.USER_ID);

                User user = userService.find(user_id);

                if(user.getRole().equals(Role.ADMIN)){
                    req.setAttribute("order", order);
                    req.getRequestDispatcher(PATH).forward(req,resp);
                    return;
                }

                List<Order> orders = orderService.getOrders(user);

                for (Order user_order:orders) {
                    if(order.getOrderId() == user_order.getOrderId()){
                        req.setAttribute("order", order);
                        req.getRequestDispatcher(PATH).forward(req,resp);
                        return;
                    }
                }
            }

            req.getRequestDispatcher(ERROR).forward(req, resp);

        } catch (ServiceException | IOException | ServletException e) {
            req.getRequestDispatcher(ERROR).forward(req, resp);
            logger.error(e);
        }

    }
}
