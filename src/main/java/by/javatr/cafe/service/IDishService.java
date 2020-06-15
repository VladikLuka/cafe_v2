package by.javatr.cafe.service;

import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.ServiceException;

public interface IDishService {

    boolean loadDishes();

    Dish getDish(int id) throws ServiceException;

}
