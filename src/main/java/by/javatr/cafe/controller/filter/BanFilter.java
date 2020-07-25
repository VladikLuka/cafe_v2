package by.javatr.cafe.controller.filter;

import by.javatr.cafe.constant.Path;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.service.impl.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Checks user status
 */
@Component
public class BanFilter implements Filter {

    Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getSession().getAttribute(SessionAttributes.USER_ID) == null){
            filterChain.doFilter(req, servletResponse);
            return;
        }
        int user_id = (int) req.getSession().getAttribute(SessionAttributes.USER_ID);
        try {
            IUserService userService =(UserService) BeanFactory.getInstance().getBean("userService");
            final User user = userService.find(user_id);
            if(user.isBan()){
                if(req.getParameter("command") != null) {
                    if (req.getParameter("command").equalsIgnoreCase("LOGOUT")) {
                        filterChain.doFilter(req, servletResponse);
                    }
                }
                req.getRequestDispatcher(Path.BAN).forward(req, servletResponse);
                return;
            }
            filterChain.doFilter(req, servletResponse);
        } catch (ServiceException e) {
            logger.error(e);
        }

    }

    @Override
    public void destroy() {

    }
}
