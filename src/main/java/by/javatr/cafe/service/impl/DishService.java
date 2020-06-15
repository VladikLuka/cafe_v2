package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IDishService;

import java.util.List;

@Component
public final class DishService implements IDishService {

    @Autowired
    IDishRepository dishRepository;

    private DishService(){}

    @Override
    public boolean loadDishes() {
        return false;
    }

    @Override
    public Dish getDish(int id) throws ServiceException {
        try {
            return dishRepository.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("get dish ex", e);
        }
    }


    public List<Dish> getDishes() throws ServiceException {
        List<Dish> all = null;
        try {
                all = dishRepository.getAll();
            } catch (DAOException e){
            throw new ServiceException("get dishes ex", e);
        }
        return all;
    }
}
