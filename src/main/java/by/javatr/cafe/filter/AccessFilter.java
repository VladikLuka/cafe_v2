package by.javatr.cafe.filter;

import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.SessionAttributes;
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(SessionAttributes.ACCESS_LEVEL);
        Map<String, String> business_url = (Map<String, String>) request.getServletContext().getAttribute("BUSINESS_URL");

        String page_access_level = business_url.get(request.getAttribute("get_page").toString().split("/")[0]);


        if(role != null){
            if (Role.getRoleByName(role).ordinal() > Role.getRoleByName(page_access_level).ordinal()) {
                request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request,response);
            }else{
                filterChain.doFilter(request,response);
                //request.getRequestDispatcher(SERVLET).forward(request,response);
            }
        }else{
            session.setAttribute(SessionAttributes.ACCESS_LEVEL, AccessLevel.GUEST);

            filterChain.doFilter(request,response);
        }



//        Role user_access_level_string = (Role) session.getAttribute(ACCESS_LEVEL);
//
//        if(user_access_level_string == null){
//            user_access_level_string = Role.GUEST;
//        }
//
//        String get_page = (String) request.getAttribute("get_page");
//
//        Map<String, String> business_url = (Map<String, String>) request.getServletContext().getAttribute("BUSINESS_URL");
//
//
//            String page_access_level_string = business_url.get(get_page);
//
//            if(page_access_level_string == null){
//                filterChain.doFilter(request,response);
//                return;
//            }
//
//
//            Role page_access_level = Role.getRoleByName(page_access_level_string);
//
//
//
//            if (user_access_level_string.ordinal() > page_access_level.ordinal()) {
//                request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request,response);
//            }else{
//                filterChain.doFilter(request,response);
//                //request.getRequestDispatcher(SERVLET).forward(request,response);
//            }


    }



    @Override
    public void destroy() {

    }
}
