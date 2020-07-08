package by.javatr.cafe.page;

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


@Component
public class Checkout_id implements Page {

    Logger logger = LogManager.getLogger(getClass());

    @Autowired
    IOrderService orderService;
    @Autowired
    IUserService userService;


    private final String ACCESS = AccessLevel.USER;

    private final String PATH = Path.CHECKOUT;

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
    public void process(HttpServletRequest req, HttpServletResponse resp) {

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
                    if(order.getOrder_id() == user_order.getOrder_id()){
                        req.setAttribute("order", order);
                        req.getRequestDispatcher(PATH).forward(req,resp);
                        return;
                    }
                }
            }

            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);

        } catch (ServiceException | IOException | ServletException e) {
            logger.error(e);
        }

    }
}
