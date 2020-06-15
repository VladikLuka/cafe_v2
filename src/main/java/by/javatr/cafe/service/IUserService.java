package by.javatr.cafe.service;

import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.ServiceException;

import java.util.List;

public interface IUserService {


    List<User> findAllUser();

    User find(int id) throws ServiceException;

    boolean delete(int id) throws ServiceException;

    User loginUser(User user) throws ServiceException;

    User createUser(User user) throws ServiceException;

    boolean replenishBalance(User user) throws ServiceException;

}
