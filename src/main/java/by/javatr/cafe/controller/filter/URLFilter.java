package by.javatr.cafe.controller.filter;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.controller.page.Page;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * determines if the server can process the request
 */
public class URLFilter implements Filter {

    private static final String SERVLET = "/controller";
    private static final Set<Page> PAGES = new HashSet<>();


    /**
     * looks for classes that implement the "page" interface
     * @param filterConfig filter config
     */
    @Override
    public void init(FilterConfig filterConfig) {

        final Map<String, Object> singletons = BeanFactory.getInstance().getContainer();
        final Set<String> strings = singletons.keySet();
        for (String key :strings) {
            final Object o = singletons.get(key);
            if(o instanceof Page){
                PAGES.add((Page) o);
            }
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String URI = request.getRequestURI();
        request.setAttribute("URL", URI);
        request.setAttribute("method", request.getMethod());

        for (Page page : PAGES) {
            if (page.thisURL(request.getRequestURI())) {
                page.process(request, response);
                return;
            }
        }

        request.getRequestDispatcher(SERVLET).forward(request,servletResponse);

    }


    @Override
    public void destroy() {

    }
}
