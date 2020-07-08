package by.javatr.cafe.controller.filter;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.util.Cache;
import by.javatr.cafe.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AccessFilter implements Filter {

    public static final String SERVLET = "/controller";
    public static final String ACCESS_LEVEL = SessionAttributes.ACCESS_LEVEL;
    public static final String ERROR = "/WEB-INF/jsp/error.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Object logged = session.getAttribute(SessionAttributes.USER_ID);
        Map<String, String> business_url = (Map<String, String>) request.getServletContext().getAttribute("BUSINESS_URL");

        String page_access_level = business_url.get(request.getAttribute("get_page").toString().split("/")[0]);


        if(logged != null){
            Cache cache = (Cache)BeanFactory.getInstance().getBean("cache");
            int id = (Integer) logged;

            if(cache.getUser(id).isBan()){
                request.getRequestDispatcher("/WEB-INF/jsp/ban.jsp").forward(request,response);
                return;
            }
            final Role role = cache.getUser(id).getRole();
            if (role.ordinal() > Role.getRoleByName(page_access_level).ordinal()) {
                request.getRequestDispatcher(ERROR).forward(request,response);
            }else{
                filterChain.doFilter(request,response);
            }
        }else if(Role.getRoleByName(AccessLevel.GUEST).ordinal() <= Role.getRoleByName(page_access_level).ordinal()){
            filterChain.doFilter(request,response);
        }else {
            request.getRequestDispatcher(ERROR).forward(request, response);
        }
    }



    @Override
    public void destroy() {

    }
}
