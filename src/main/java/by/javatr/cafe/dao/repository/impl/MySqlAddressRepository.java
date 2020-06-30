package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.repository.AbstractRepository;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.util.Cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class MySqlAddressRepository extends AbstractRepository<Address> implements IAddressRepository {

    private static final String GET_ALL_ADDRESSES = "select * from address";
    private static final String GET_ALL_ADDRESSES_ID = "select SQL_NO_CACHE * from address where address_user_id = ?";
    private static final String GET_ADDRESS_ID = "select * from address where id = ?";
    private static final String GET_LAST_ID = "select LAST_INSERT_ID()";

    @Autowired
    Cache cache;

    @Override
    public List<Address> getAll() throws DAOException {
        if (!cache.getMapAddress().isEmpty()) {
            return cache.getListAddress();
        }

        List<Address> list = new ArrayList<>();

        try (final Connection connection = getConnection();
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
        if(cache.getUserAddresses(id)!= null){
            return cache.getUserAddresses(id);
        }

        List<Address> list = new ArrayList<>();
        try (final Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_ADDRESSES_ID);) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery();){
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
            }

        }catch(SQLException e) {
            throw new DAOException("get all dishes error", e);
        }

        return list;

}


    @Override
    public Address get(Address address) throws DAOException {

        Address address1 = cache.getAddress(address);
        if(address1 != null){
            return address1;
        }

        try (final Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ADDRESS_ID);) {
            statement.setInt(1, address.getId());
            try(ResultSet resultSet = statement.executeQuery();
            ){
                while (resultSet.next()) {
                    address.setId(resultSet.getInt(1));
                    address.setCity(resultSet.getString(2));
                    address.setStreet(resultSet.getString(3));
                    address.setHouse(resultSet.getString(4));
                    address.setFlat(resultSet.getString(5));
                    address.setUser_id(resultSet.getInt(6));
                }
            }

        }catch(SQLException e) {
            throw new DAOException("get dish error", e);
        }

        return address;
    }

    @Override
    public Address create(Address address) throws DAOException {

        try(Connection connection = getConnection();) {
            address = super.create(connection, address);
            cache.addAddress(address);
        }catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return address;
    }

    @Override
    public Address update(Address address) throws DAOException {

        try (Connection connection = getConnection();) {
            address= super.update(connection, address);
            cache.updateAddress(address);
        }catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return address;
    }

    @Override
    public boolean delete(Address address) throws DAOException {
        try(Connection connection = getConnection()){
            super.delete(connection, address);
            cache.deleteAddress(address);
            return true;
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
    }

    private MySqlAddressRepository() {}
}
