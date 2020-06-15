package by.javatr.cafe.dao.repository;

import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;

import java.sql.SQLException;
import java.util.List;

public interface IDishRepository {

    List<Dish> getAll() throws DAOException;

    Dish getById(int id) throws DAOException;

    boolean delete(int id) throws DAOException;

    Dish create(Dish dish) throws DAOException;

    Dish update(Dish dish) throws DAOException;
}
