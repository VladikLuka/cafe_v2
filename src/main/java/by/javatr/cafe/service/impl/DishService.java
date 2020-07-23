package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IDishService;

import java.util.List;

/**
 * Contains methods for work with dishes
 */
@Component
public class  DishService implements IDishService {

    @Autowired
    IDishRepository dishRepository;

    /**
     * Return dish by ID
     * @param dishId dish id
     * @return dish
     */
    @Override
    public Dish get(int dishId) throws ServiceException {
        try {
            return dishRepository.getById(dishId);
        } catch (DAOException e) {
            throw new ServiceException("get dish ex", e);
        }
    }

    /**
     * Update dish
     * @param dish dish to be updated
     * @return updated dish
     */
    @Override
    public Dish update(Dish dish) throws ServiceException {

        try {
            dishRepository.getById(dish.getId());
            dish = dishRepository.update(dish);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return dish;
    }

    /**
     * Create dish
     * @param dish being created
     * @return created dish
     */
    @Override
    public Dish create(Dish dish) throws ServiceException {

        try {
            dish = dishRepository.create(dish);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return dish;
    }

    /**
     * Delete dish
     * @param dish to be deleted
     * @return boolean
     */
    @Override
    public boolean delete(Dish dish) throws ServiceException {

        try {
            dishRepository.delete(dish.getId());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }


    /**
     * Returns all dishes
     * @return list of dishes
     */
    public List<Dish> getDishes() throws ServiceException {
        List<Dish> all = null;
        try {
                all = dishRepository.getAll();
            } catch (DAOException e){
            throw new ServiceException("get dishes ex", e);
        }
        return all;
    }

    /**
     * Make dish unavailable
     * @param dish to be hidden
     * @return hidden dish
     */
    @Override
    public boolean hideDish(Dish dish) throws ServiceException {

        try {
            dish = dishRepository.getById(dish.getId());
            dish.setAvailable(false);
            dishRepository.update(dish);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    /**
     * Make dish available
     * @param dish to be hidden
     * @return hidden dish
     */
    @Override
    public boolean showDish(Dish dish) throws ServiceException {
        try {
            dish = dishRepository.getById(dish.getId());
            dish.setAvailable(true);
            dishRepository.update(dish);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    public void setDishRepository(IDishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    private DishService(){}
}
