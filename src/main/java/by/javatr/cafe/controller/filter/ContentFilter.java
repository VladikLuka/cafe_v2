package by.javatr.cafe.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Redirects a request to a static resource
 */
public class ContentFilter  implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.getRequestDispatcher(request.getRequestURI()).forward(request,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
