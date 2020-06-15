package by.javatr.cafe.service;

import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.ServiceException;

import java.util.List;

public interface IAddressService {

    List<Address> getAll() throws ServiceException;

    List<Address> getAllForUser(int id) throws ServiceException;

    Address create(Address address) throws ServiceException;

    Address update(Address address) throws ServiceException;

    boolean delete(Address address) throws ServiceException;
}
