package by.javatr.cafe.controller;

import by.javatr.cafe.constant.Path;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.command.CommandProvider;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.connection.impl.ConnectionPool;
import by.javatr.cafe.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Accept requests and send response
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {

    private final Logger logger = LogManager.getLogger(Controller.class);
    private final CommandProvider provider = new CommandProvider();

    @Override
    public void init(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("My SQL Driver Not Found" , e);
        }

    }

    /**
     * Accept request with GET method
     * @param req contains request information
     * @param resp contains response information
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req,resp);
    }

    /**
     * Accept request with POST method
     * @param req contains request information
     * @param resp contains response information
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req,resp);
    }


    /**
     * Process request
     * @param req contains request information
     * @param resp contains response information
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        RequestContent content = new RequestContent();
        content.setContent(req);
        Command command;

        command = provider.getCommand(req.getParameter("command"));

        RequestResult result = null;

        try {
            result = command.execute(content);
        } catch (ServiceException e) {
            logger.error("Service exception ", e);
            result = new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        result.setResponseData(resp);
        content.addAttrToSession(req);
        content.addAttrToRequest(req);
        try {
            frw(req,resp,result);
        } catch (IOException | ServletException e) {
            logger.error(e);
        }
    }

    /**
     * Defines the type of response
     * @param req contains request information
     * @param resp contains response information
     * @param result contains result of processing request
     * @throws IOException can't find jsp
     * @throws ServletException not a servlet
     */
    public void frw(HttpServletRequest req, HttpServletResponse resp, RequestResult result) throws IOException, ServletException {
        if(result.getNav() != null && result.getNav().equals(Navigation.REDIRECT)){
            resp.sendRedirect(result.getPage());
        }else if(result.getNav() != null && result.getNav().equals(Navigation.FORWARD)){
            req.getRequestDispatcher(result.getPage()).forward(req,resp);
        }else {
            if(result.getStatus() == HttpServletResponse.SC_BAD_REQUEST && req.getMethod().equals("GET")){
                req.getRequestDispatcher(Path.ERROR).forward(req,resp);
            }
            resp.getWriter().println(result.getCommand());
        }

    }

    /**
     * Terminate connection pool
     */
    @Override
    public void destroy() {
        ConnectionPool.CONNECTION_POOL.terminatePool();
    }
}
