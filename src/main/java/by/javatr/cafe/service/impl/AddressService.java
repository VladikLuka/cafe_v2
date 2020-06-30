package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.util.Cache;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IAddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AddressService implements IAddressService {

    public static Logger logger = LogManager.getLogger(AddressService.class);

    @Autowired
    IAddressRepository addressRepository;
    @Autowired
    IUserRepository userRepository;

    private AddressService(){}


    @Override
    public Address update(Address address) throws ServiceException {
        try {
            final Address update = addressRepository.update(address);
            if(update == null) throw new ServiceException("invalid update address");
//            cache.updateAddress(address);
            return address;
        } catch (DAOException | SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Address> getAll() throws ServiceException {

        List<Address> all = null;
        try {
            all = addressRepository.getAll();
        } catch (DAOException e) {
            throw new ServiceException("get all addresses service ex",e);
        }
        return all;

    }

    @Override
    public List<Address> getAllForUser(int id) throws ServiceException {

        List<Address> allId = null;
        try {
            allId = addressRepository.getAllId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return allId;
    }

    @Override
    public Address create(Address address) throws ServiceException {

        try {
            address = addressRepository.create(address);
            if(address != null){
                final User user = userRepository.findUser(new User(address.getUser_id()));
                if (user.getAddress() == null) {
                    user.setAddress(new ArrayList<>());
                }
                user.getAddress().add(address);
                return address;
            }else{
                return null;
            }
        } catch (DAOException e) {

            throw new ServiceException("failed to create address ",e);

        }
    }

    @Override
    public Address find(Address address) throws ServiceException {

        try {
            address = addressRepository.get(address);
            return address ;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean delete(Address address) throws ServiceException {
        try {
            addressRepository.delete(address);
            return true;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
