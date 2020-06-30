package by.javatr.cafe.service;

import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService {



    User find(int id) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    User loginUser(User user) throws ServiceException;

    User createUser(User user) throws ServiceException;

    User update(User user) throws ServiceException;

    User addMoney(BigDecimal amount, int user_id) throws ServiceException;

    User addPoints(int point, int user_id) throws ServiceException;

    User subtractMoney(BigDecimal amount, int user_id) throws ServiceException;

    User subtractPoints(int amount, int user_id) throws ServiceException;

    User banUser(int id) throws ServiceException;

    User unbanUser(int id) throws ServiceException;
}
