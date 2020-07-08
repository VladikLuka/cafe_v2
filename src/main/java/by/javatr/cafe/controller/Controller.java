package by.javatr.cafe.controller;

import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.command.CommandProvider;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.impl.UserService;
import by.javatr.cafe.util.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


@WebServlet("/controller")
public class Controller extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(Controller.class);

    private final CommandProvider provider = new CommandProvider();

    public void init(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("My SQL Driver Not Found" , e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //В фильтре ты выбираешь какую команду использовать
        // скрытыми инпут полями можно передать
        // написать свою аннотацию для log4j
        // написать свой интересный текст
        // для изображений можно использовать отдельный сервлет
        // фильтр для смены языка
        // send redirect (истит все параметры...) использовать для логаута
        // создать 2 shutdown  пуле и в proxy
        // посмотреть в контексте как подгружать файлы
        // flow api 9 java и выше!!!
        // admin pages класть в WEB-INF
        // все jsp хранить в web-inf кроме index.jsp в нем есть форвард на main.jsp из web-inf
        // в web-inf лежат lib, jsp, tld, весь код на java
        // перенаправлять в слуаче 404 ошибки на свой error 404
        // парсить url и проверять действительно ли это команда
        // set.of посомтреть для маппинга урл
        // батчем надо сделать address и user create
        // посмотреть тестовую апи paypal
        // посмотреть docker
        // верхний уровень знает о нижних всвех, нельзя вызывать из userDao address dao, можно в сервисах вызвать и аддресс и юсер дао
        // не хранить поного ющера в session хранит ьтолько важные части
        // найти куки в sqlLite в хроме
        // в exception можно доставать код ошибки станицы в jsp
        // надо прописывать в фильтрах имя и заносить в нужном порябке в web.xml
        // в contextListener можно запихнуть инициализацию и дестрой листенера
        // concurrentLinkedDeque в пуле
        // оптимистичный/пессимистичный лок
        // atomic-и и эффектив файнал
        // oauth2 !!!!
        // сделать таймаут на пуле
        // на ремоутном томкате запустить проект
        // ПРИ УДАЛЕНИИ ЮЗЕРА ПРОВЕРИТЬ УДАЛЕНА ЛИ ЕГО СЕССИЯ
        // pageable в Hibernate чекнуть
        // кэшировать итемы!!!
        // сделать STATIC лист и переинитить при апдейте
        // redis для хранения дишей
        // вкрутить aspectJ для логирования || SQLStatement
        // Чекнуть паттерн команду (execute должен быть без параметров)
        processRequest(req,resp);

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        processRequest(req,resp);

    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final String url1 = req.getRequestURI();
        System.out.println("CONTROLLER URL " + url1);

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
        frw(req,resp,result);
    }




    public void frw(HttpServletRequest req, HttpServletResponse resp, RequestResult result) throws IOException, ServletException {
        if(result.getNav() != null && result.getNav().equals(Navigation.REDIRECT)){
            resp.sendRedirect(result.getPage());
        }else if(result.getNav() != null && result.getNav().equals(Navigation.FORWARD)){
            req.getRequestDispatcher(result.getPage()).forward(req,resp);
        }else {
            if(result.getStatus() == 400 && req.getMethod().equals("GET")){
                req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req,resp);
            }
            resp.getWriter().println(result.getCommand());
        }

    }


    @Override
    public void destroy() {
        ConnectionPool.CONNECTION_POOL.terminatePool();
    }
}
