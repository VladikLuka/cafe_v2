package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.repository.AbstractRepository;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.util.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.javatr.cafe.constant.DbColumns.*;

/**
 * Contains methods for working with the database.
 * Dish table
 */
@Component
public class MySqlDishRepository extends AbstractRepository<Dish> implements IDishRepository {

    private static final Logger logger = LogManager.getLogger(MySqlDishRepository.class);

    private static final String GET_ALL = "select * from dish";
    private static final String GET_DISH = "select * from dish where dish_id = ?";

    @Autowired
    private Cache cache;

    /**
     * Returns all dishes
     * @return list of dishes
     */
    @Override
    public List<Dish> getAll() throws DAOException {

        System.out.println("DISH getAll");

        if(!cache.getDishes().isEmpty()){
            return cache.getDishes();
        }

        List<Dish> list = new ArrayList<>();

        try (
            Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();

            PreparedStatement statement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = statement.executeQuery();
        ){


            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt(DISH_ID));
                dish.setName(resultSet.getString(DISH_NAME));
                dish.setDescription(resultSet.getString(DISH_DESCRIPTION));
                dish.setPrice(resultSet.getBigDecimal(DISH_PRICE));
                dish.setAvailable(resultSet.getBoolean(DISH_IS_AVAILABLE));
                dish.setCategoryId(resultSet.getInt(DISH_CATEGORY_ID));
                dish.setWeight(resultSet.getInt(DISH_WEIGHT));
                dish.setPicturePath(resultSet.getString(DISH_PICTURE_PATH));
                list.add(dish);
            }
        }catch (SQLException e){
            logger.error("an error occurred in get All dish");
            throw new DAOException("an error occurred in get All dish", e);
        }
        return list;
    }

    /**
     * Return dish by id
     * @param dishId dish id
     * @return dish
     */
    @Override
    public Dish getById(int dishId) throws DAOException {

        System.out.println("DISH getById");

        Dish dish = cache.getDish(new Dish(dishId));

        if (dish!=null) {
            return cache.getDish(new Dish(dishId));
        }

        dish = new Dish();

       try(Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
           PreparedStatement preparedStatement = connection.prepareStatement(GET_DISH)) {
           preparedStatement.setInt(1, dishId);

           try(ResultSet resultSet = preparedStatement.executeQuery()) {
               resultSet.next();
               dish.setId(resultSet.getInt(DISH_ID));
               dish.setName(resultSet.getString(DISH_NAME));
               dish.setDescription(resultSet.getString(DISH_DESCRIPTION));
               dish.setPrice(resultSet.getBigDecimal(DISH_PRICE));
               dish.setAvailable(resultSet.getBoolean(DISH_IS_AVAILABLE));
               dish.setCategoryId(resultSet.getInt(DISH_CATEGORY_ID));
               dish.setWeight(resultSet.getInt(DISH_WEIGHT));
               dish.setPicturePath(resultSet.getString(DISH_PICTURE_PATH));
           }
       }catch (SQLException e){
           logger.error("an error occurred in get by id");
           throw new DAOException("an error occurred in get by id",e);
       }
        return dish;
    }

    /**
     * Delete existing dish
     * @param dishId dish id
     * @return boolaen
     */
    @Override
    public boolean delete(int dishId) throws DAOException {
        System.out.println("DISH delete");

        try (Connection connection = getConnection()){
            super.delete(connection, new Dish(dishId));
            cache.deleteDish(new Dish(dishId));
        }catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return true;
    }

    /**
     * Create dish
     * @param dish dish being created
     * @return created dish
     */
    @Override
    public Dish create(Dish dish) throws DAOException {

        System.out.println("DISH create");

        try (Connection connection = getConnection();){
            dish = super.create(connection, dish);
            cache.addDish(dish);
        }catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return dish;
    }

    /**
     * Update dish
     * @param dish dish being updated
     * @return updated dish
     */
    @Override
    public Dish update(Dish dish) throws DAOException {

        System.out.println("DISH update");

        try (Connection connection = getConnection()){
            dish = super.update(connection, dish);
            cache.updateDish(dish);
        }catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return dish;
    }

    private MySqlDishRepository() {}
}
