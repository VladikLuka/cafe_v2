package by.javatr.cafe.dao.repository;

import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;

import java.sql.SQLException;
import java.util.List;

public interface IAddressRepository {

    List<Address> getAll() throws DAOException;
    List<Address> getAllId(int id) throws DAOException;
    Address create(Address address) throws DAOException;
    Address read(int id) throws DAOException;
    Address update(Address address) throws DAOException, SQLException;
    boolean delete(int id) throws DAOException;

}
