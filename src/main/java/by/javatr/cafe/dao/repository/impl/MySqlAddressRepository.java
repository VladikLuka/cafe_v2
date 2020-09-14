package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.connection.impl.ConnectionPool;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods for working with the database.
 * Address table
 */
@Component
public class MySqlAddressRepository extends AbstractRepository<Address> implements IAddressRepository {

    private static final String GET_ALL_ADDRESSES = "select * from address";
    private static final String GET_ALL_ADDRESSES_ID = "select SQL_NO_CACHE * from address where address_user_id = ?";
    private static final String GET_ADDRESS_ID = "select * from address where address_id = ?";

    public MySqlAddressRepository(Connection connection) {
        setConnection(connection);
    }

    public MySqlAddressRepository() {
        setConnection(ConnectionPool.CONNECTION_POOL.retrieve());
    }
    /**
     * Returns all addresses
     * @return list of addresses
     */
    @Override
    public List<Address> getAll() throws DAOException {

        Cache cache = Cache.getInstance();

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
                address.setUserId(resultSet.getInt(6));
                address.setAvailable(resultSet.getBoolean(7));
                list.add(address);
            }
        } catch (SQLException e) {
            throw new DAOException("get all dishes error", e);
        }

        return list;
    }


    /**
     * Returns all user addresses
     * @param userId user id
     * @return list of addresses
     */
    @Override
    public List<Address> getAllId(int userId) throws DAOException {

        Cache cache = Cache.getInstance();

        if(cache.getUserAddresses(userId)!= null){
            return cache.getUserAddresses(userId);
        }

        final Connection connection = getConnection();

        List<Address> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ADDRESSES_ID);) {
            statement.setInt(1, userId);
            try(ResultSet resultSet = statement.executeQuery();){
                while (resultSet.next()) {
                    Address address = new Address();
                    address.setId(resultSet.getInt(1));
                    address.setCity(resultSet.getString(2));
                    address.setStreet(resultSet.getString(3));
                    address.setHouse(resultSet.getString(4));
                    address.setFlat(resultSet.getString(5));
                    address.setUserId(resultSet.getInt(6));
                    address.setAvailable(resultSet.getBoolean(7));
                    list.add(address);
                }
            }

        }catch(SQLException e) {
            throw new DAOException("get all dishes error", e);
        }

        return list;

}

    /**
     * Looking for an address by ID
     * @param address should contain id
     * @return address
     */
    @Override
    public Address get(Address address) throws DAOException {

        Cache cache = Cache.getInstance();

        Address address1 = cache.getAddress(address);
        if(address1 != null){
            return address1;
        }

        final Connection connection = getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_ADDRESS_ID)) {
             statement.setInt(1, address.getId());

             try(ResultSet resultSet = statement.executeQuery();){
                while (resultSet.next()) {
                    address.setId(resultSet.getInt(1));
                    address.setCity(resultSet.getString(2));
                    address.setStreet(resultSet.getString(3));
                    address.setHouse(resultSet.getString(4));
                    address.setFlat(resultSet.getString(5));
                    address.setUserId(resultSet.getInt(6));
                    address.setAvailable(resultSet.getBoolean(7));
                }
            }

        }catch(SQLException e) {
            throw new DAOException("get dish error", e);
        }

        return address;
    }

    /**
     * Create address
     * @param address address
     * @return address
     */
    @Override
    public Address create(Address address) throws DAOException {

        Cache cache = Cache.getInstance();

        address = super.create(address);
        cache.addAddress(address);
        return address;
    }

    /**
     * Update existing address
     * @param address address
     * @return address
     */
    @Override
    public Address update(Address address) throws DAOException {
        Cache cache = Cache.getInstance();

        address = super.update(address);
        cache.updateAddress(address);
        return address;
    }

    /**
     * Delete existing address
     * @param address address
     * @return boolean
     */
    @Override
    public boolean delete(Address address) throws DAOException {
         Cache cache = Cache.getInstance();

        boolean result = super.delete(address);
        if(result){
            cache.deleteAddress(address);
        }
        return result;
    }

}
