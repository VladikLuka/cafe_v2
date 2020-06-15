package by.javatr.cafe.dao.repository;

import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;

import java.util.List;

public interface IUserRepository {

    List<User> getAllUser() throws DAOException;

    User findUser(User user)throws DAOException;

    boolean deleteUser(int id)throws DAOException;

    User createUser(User user)throws DAOException;

    User update(User user)throws DAOException;

    User findUser(String email, String password) throws DAOException;

}
