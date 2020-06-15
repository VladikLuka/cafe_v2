package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class MySqlAddressRepository extends AbstractRepository implements IAddressRepository {

    private static final String GET_ALL_ADDRESSES = "select * from address";
    private static final String GET_ALL_ADDRESSES_ID = "select SQL_NO_CACHE * from address where user_id = ?";
    private static final String CREATE_ADDRESS = "INSERT INTO address (city, street, house, flat, user_id) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_ADDRESS = "UPDATE address set city = ?, street = ? , house = ? , flat = ? where address_id = ?";
    private static final String DELETE_ADDRESS = "delete from address where address_id = ?";
    private static final String GET_LAST_ID = "select LAST_INSERT_ID()";

    @Override
    public List<Address> getAll() throws DAOException {

        List<Address> list = new ArrayList<>();

        try (final Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ADDRESSES);
             ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt(1));
                address.setCity(resultSet.getString(2));
                address.setStreet(resultSet.getString(3));
                address.setHouse(resultSet.getString(4));
                address.setFlat(resultSet.getString(5));
                address.setUser_id(resultSet.getInt(6));
                list.add(address);
            }


        } catch (SQLException e) {
            throw new DAOException("get all dishes error", e);
        }

        return list;
    }

    @Override
    public List<Address> getAllId(int id) throws DAOException {
        List<Address> list = new ArrayList<>();
        ResultSet resultSet = null;
        try (final Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ADDRESSES_ID);) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt(1));
                address.setCity(resultSet.getString(2));
                address.setStreet(resultSet.getString(3));
                address.setHouse(resultSet.getString(4));
                address.setFlat(resultSet.getString(5));
                address.setUser_id(resultSet.getInt(6));
                list.add(address);
            }

        }catch(SQLException e) {
            throw new DAOException("get all dishes error", e);
        }finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throw new DAOException(throwables);
            }
        }

        return list;

}

    @Override
    public Address create(Address address) throws DAOException {

        try (final Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
             PreparedStatement statement = connection.prepareStatement(CREATE_ADDRESS);
        ){
            connection.setAutoCommit(false);
            statement.setString(1, address.getCity());
            statement.setString(2, address.getStreet());
            statement.setString(3, address.getHouse());
            statement.setString(4, address.getFlat());
            statement.setInt(5, address.getUser_id());
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement(GET_LAST_ID);
            ResultSet resultSet = statement1.executeQuery();
            resultSet.next();
            address.setId(resultSet.getInt("last_insert_id()"));
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException(" failed create address", e);
        }
        return address;
    }

    @Override
    public Address read(int id) {
        return null;
    }

    @Override
    public Address update(Address address) throws DAOException, SQLException {


        try (Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS);
        ){
            connection.setAutoCommit(false);
            statement.setString(1, address.getCity());
            statement.setString(2, address.getFlat());
            statement.setString(3, address.getHouse());
            statement.setString(4, address.getFlat());
            statement.setInt(5, address.getUser_id());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("failed to update address",e);
        }
        return address;
    }

    @Override
    public boolean delete(int id) throws DAOException {

        try(Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
            PreparedStatement statement = connection.prepareStatement(DELETE_ADDRESS)){
            connection.setAutoCommit(false);
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw  new DAOException("failed to delete address", e);
        }

        return true;
    }

    private MySqlAddressRepository() {}
}
