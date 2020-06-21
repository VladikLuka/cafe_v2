package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.AbstractRepositoryTest;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class MySqlAddressRepository extends AbstractRepositoryTest<Address> implements IAddressRepository {

    private static final String GET_ALL_ADDRESSES = "select * from address";
    private static final String GET_ALL_ADDRESSES_ID = "select SQL_NO_CACHE * from address where address_user_id = ?";
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

        super.create(getConnection(), address);
        return address;
    }

    @Override
    public Address read(Address address) throws DAOException {
        return super.read(getConnection(), address.getClass(), address.getId());
    }

    @Override
    public Address update(Address address) throws ServiceException {

        try {
            super.update(getConnection(), address);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return address;
    }

    @Override
    public boolean delete(Address address) throws DAOException {

        super.delete(getConnection(), address);
        return true;
    }

    private MySqlAddressRepository() {}
}
