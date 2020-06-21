package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.util.Cache;
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
    @Autowired
    Cache cache;

    private DishService(){}

    @Override
    public Dish get(int id) throws ServiceException {
        try {
            return dishRepository.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("get dish ex", e);
        }
    }

    @Override
    public Dish update(Dish dish) throws ServiceException {

        try {
            dish = cache.getDish(dish.getId());
            dish = dishRepository.update(dish);
            if(dish != null){
                cache.updateDish(dish);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return dish;
    }

    @Override
    public Dish create(Dish dish) throws ServiceException {

        try {
            dish = dishRepository.create(dish);
            if(dish != null){
                cache.addDish(dish);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return dish;
    }

    @Override
    public boolean delete(Dish dish) throws ServiceException {

        try {
            dishRepository.delete(dish.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
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

    @Override
    public boolean hideDish(Dish dish) throws ServiceException {

        Dish cacheDish = cache.getDish(dish.getId());
        cacheDish.setAvailable(false);
        try {
            dishRepository.update(cacheDish);
            cache.updateDish(cacheDish);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public boolean showDish(Dish dish) throws ServiceException {
        Dish cacheDish = cache.getDish(dish.getId());
        cacheDish.setAvailable(true);
        try {
            dishRepository.update(cacheDish);
            cache.updateDish(cacheDish);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }
}
