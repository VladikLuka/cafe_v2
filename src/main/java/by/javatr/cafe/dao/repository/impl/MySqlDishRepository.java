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

import static by.javatr.cafe.constant.BD_Columns.*;

@Component
public class MySqlDishRepository extends AbstractRepository<Dish> implements IDishRepository {

    Logger logger = LogManager.getLogger(MySqlDishRepository.class);

    private static final String GET_ALL = "select * from dish";
    private static final String GET_DISH = "select * from dish where dish_id = ?";

    @Autowired
    Cache cache;

    @Override
    public List<Dish> getAll() throws DAOException {
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
                connection.setAutoCommit(false);
                dish.setId(resultSet.getInt(DISH_ID));
                dish.setName(resultSet.getString(DISH_NAME));
                dish.setDescription(resultSet.getString(DISH_DESCRIPTION));
                dish.setPrice(resultSet.getBigDecimal(DISH_PRICE));
                dish.setAvailable(resultSet.getBoolean(DISH_IS_AVAILABLE));
                dish.setCategory_id(resultSet.getInt(DISH_CATEGORY_ID));
                dish.setWeight(resultSet.getInt(DISH_WEIGHT));
                dish.setPicture_path(resultSet.getString(DISH_PICTURE_PATH));
                list.add(dish);
                connection.commit();
            }
        }catch (SQLException e){
            logger.error("an error occurred in get All dish");
            throw new DAOException("an error occurred in get All dish", e);
        }
        return list;
    }


    @Override
    public Dish getById(int id) throws DAOException {
        Dish dish = cache.getDish(new Dish(id));

        if (dish!=null) {
            return cache.getDish(new Dish(id));
        }

        dish = new Dish();

       try(           Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
                      PreparedStatement preparedStatement = connection.prepareStatement(GET_DISH)) {
            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                connection.setAutoCommit(false);
                dish.setId(resultSet.getInt(DISH_ID));
                dish.setName(resultSet.getString(DISH_NAME));
                dish.setDescription(resultSet.getString(DISH_DESCRIPTION));
                dish.setPrice(resultSet.getBigDecimal(DISH_PRICE));
                dish.setAvailable(resultSet.getBoolean(DISH_IS_AVAILABLE));
                dish.setCategory_id(resultSet.getInt(DISH_CATEGORY_ID));
                dish.setWeight(resultSet.getInt(DISH_WEIGHT));
                dish.setPicture_path(resultSet.getString(DISH_PICTURE_PATH));
                connection.commit();
            }
       }catch (SQLException e){
           logger.error("an error occurred in get by id");
           throw new DAOException("an error occurred in get by id",e);
       }
        return dish;
    }

    @Override
    public boolean delete(int id) throws DAOException {

        try (Connection connection = getConnection()){
            connection.setAutoCommit(false);
            super.delete(connection, new Dish(id));
            connection.commit();
            cache.deleteDish(new Dish(id));
        }catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return true;
    }

    @Override
    public Dish create(Dish dish) throws DAOException {
        try (Connection connection = getConnection();){
            connection.setAutoCommit(false);
            dish = super.create(connection, dish);
            connection.commit();
            cache.addDish(dish);
        }catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return dish;
    }

    @Override
    public Dish update(Dish dish) throws DAOException {
        try (Connection connection = getConnection()){
            connection.setAutoCommit(false);
            dish = super.update(connection, dish);
            connection.commit();
            cache.updateDish(dish);
        }catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return dish;
    }

    private MySqlDishRepository() {}
}
