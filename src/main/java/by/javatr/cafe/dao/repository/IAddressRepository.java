package by.javatr.cafe.dao.repository;

import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface IAddressRepository {

    List<Address> getAll() throws DAOException;
    List<Address> getAllId(int id) throws DAOException;
    Address create(Address address) throws DAOException;
    Address get(Address address) throws DAOException;
    Address update(Address address) throws DAOException, SQLException, ServiceException;
    boolean delete(Address address) throws DAOException;

}
