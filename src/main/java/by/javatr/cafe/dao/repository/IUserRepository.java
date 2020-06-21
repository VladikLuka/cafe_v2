package by.javatr.cafe.dao.repository;

import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;

import java.util.List;

public interface IUserRepository {

    List<User> getAllUser() throws DAOException;

    User findUser(User user)throws DAOException;

    boolean delete(User user) throws DAOException;

    User create(User user)throws DAOException;

    User update(User user)throws DAOException;

    User find(String email, String password) throws DAOException;

}
