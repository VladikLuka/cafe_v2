package by.javatr.cafe.filter;


import by.javatr.cafe.constant.AccessLevel;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class URLFilter implements Filter {

    public static final Map<String, String> BUSINESS_URL = new HashMap<>();
    public static final String SERVLET = "/controller";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        BUSINESS_URL.put("signup", AccessLevel.GUEST);
        BUSINESS_URL.put("menu", AccessLevel.GUEST);
        BUSINESS_URL.put("error", AccessLevel.GUEST);
        BUSINESS_URL.put("cart", AccessLevel.USER);
        BUSINESS_URL.put("drink", AccessLevel.GUEST);
        BUSINESS_URL.put("garnish", AccessLevel.GUEST);
        BUSINESS_URL.put("meat", AccessLevel.GUEST);
        BUSINESS_URL.put("pizza", AccessLevel.GUEST);
        BUSINESS_URL.put("salad", AccessLevel.GUEST);
        BUSINESS_URL.put("sushi", AccessLevel.GUEST);
        BUSINESS_URL.put("user", AccessLevel.USER);
        BUSINESS_URL.put("order", AccessLevel.USER);
        BUSINESS_URL.put("", AccessLevel.GUEST);
        BUSINESS_URL.put("pay", AccessLevel.USER);
        BUSINESS_URL.put("success", AccessLevel.USER);
        BUSINESS_URL.put("admin", AccessLevel.ADMIN);
        BUSINESS_URL.put("checkout", AccessLevel.USER);
        BUSINESS_URL.put("payment_order", AccessLevel.USER);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String URI = request.getRequestURI();

        Object role = request.getSession().getAttribute(SessionAttributes.ACCESS_LEVEL);

        if(role == null){
            request.getSession().setAttribute(SessionAttributes.ACCESS_LEVEL, AccessLevel.GUEST);
        }

        if(URI.charAt(URI.length() - 1)==('/') && URI.length() > 1){
            response.sendRedirect(URI.substring(0, URI.length()-1));
            return;
        }
        String[] URL = request.getRequestURI().split("/");

        if(servletRequest.getServletContext().getAttribute("BUSINESS_URL")==null) {
            servletRequest.getServletContext().setAttribute("BUSINESS_URL", BUSINESS_URL);
        }

        if(request.getParameter("command") == null){
            request.setAttribute("command", "get_page" );
            request.setAttribute("get_page", request.getRequestURI().substring(1));
        }else{
            request.getRequestDispatcher(SERVLET).forward(request,servletResponse);
            return;
        }

        if(URL.length == 0){
            request.getRequestDispatcher("/index.jsp").forward(request,response);
            return;
        }
        if(URL.length >= 2){

            ArrayList<String> params = new ArrayList<>();

            for (int i = 2; i < URL.length; i++) {
                params.add(URL[i]);
            }

            request.setAttribute("pageParams", params);

            if(BUSINESS_URL.containsKey(URL[1])){
                filterChain.doFilter(request,response);
                return;
            }
        }

        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request,response);
    }


    @Override
    public void destroy() {

    }
}
