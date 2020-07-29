package by.javatr.cafe.dao;

import by.javatr.cafe.connection.impl.ConnectionPool;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.dao.repository.impl.MySqlAddressRepository;
import by.javatr.cafe.dao.repository.impl.MySqlDishRepository;
import by.javatr.cafe.dao.repository.impl.MySqlOrderRepository;
import by.javatr.cafe.dao.repository.impl.MySqlUserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory implements AutoCloseable {

    private static final Logger logger = LogManager.getLogger(DAOFactory.class);
    private final Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();

    public IUserRepository getUserRepository(){
        return new MySqlUserRepository(connection);
    }

    public IAddressRepository getAddressRepository(){
        return new MySqlAddressRepository(connection);
    }

    public IDishRepository getDishRepository(){
        return new MySqlDishRepository(connection);
    }

    public IOrderRepository getOrderRepository(){
        return new MySqlOrderRepository(connection);
    }

    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "An exception occurred during setting auto commit in false.");
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "An exception occurred during setting auto commit in false.");
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "An exception occurred during committing.", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "An exception occurred during rolling back transaction.", e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            logger.log(Level.ERROR, throwables);
        }
    }

}
