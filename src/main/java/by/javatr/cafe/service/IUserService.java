package by.javatr.cafe.service;

import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;

import java.math.BigDecimal;

public interface IUserService {



    User find(int id) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    User loginUser(User user) throws ServiceException;

    User createUser(User user) throws ServiceException;

    User update(User user) throws ServiceException;

    User addMoney(BigDecimal amount, int userId) throws ServiceException;

    User addPoints(int point, int userId) throws ServiceException;

    User subtractMoney(BigDecimal amount, int userId) throws ServiceException;

    User subtractPoints(int amount, int userId) throws ServiceException;

    User banUser(int id) throws ServiceException;

    User unbanUser(int id) throws ServiceException;

    User makeAdmin(int id) throws ServiceException;

    User makeUser(int id) throws ServiceException;
}
