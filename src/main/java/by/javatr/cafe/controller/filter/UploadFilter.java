package by.javatr.cafe.controller.filter;


import javax.servlet.*;
import java.io.IOException;

public class UploadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.getRequestDispatcher("/upload").forward(servletRequest, servletResponse);
    }
}
