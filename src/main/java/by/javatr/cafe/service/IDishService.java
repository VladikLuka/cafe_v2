package by.javatr.cafe.service;

import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.ServiceException;

import java.util.List;

public interface IDishService {

    List<Dish> getDishes() throws ServiceException;

    Dish get(int id) throws ServiceException;

    Dish update(Dish dish) throws ServiceException;

    Dish create(Dish dish) throws ServiceException;

    boolean delete(Dish dish) throws ServiceException;

    boolean hideDish(Dish dish) throws ServiceException;

    boolean showDish(Dish dish) throws ServiceException;
}
