package by.javatr.cafe.controller.command.impl.admin;

import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

public class BanUser implements Command {
    @Override
    public RequestResult execute(RequestContent content){

        int id = Integer.parseInt(content.getRequestParam("id"));



        return null;
    }
}
