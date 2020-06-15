package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.exception.ServiceException;

import javax.servlet.http.HttpServletResponse;

@Component
public class Get_Page implements Command {

    private String path;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        path = content.getRequestAttr("get_page").toString().split("/")[0];

        RequestResult result = new RequestResult(Navigation.FORWARD, "WEB-INF/jsp/" + path + ".jsp", HttpServletResponse.SC_OK);
        return result;
    }
}
