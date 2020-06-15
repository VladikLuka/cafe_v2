package by.javatr.cafe.controller.command;

import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

public interface Command {

    public RequestResult execute(RequestContent content) throws ServiceException, DAOException;

}
