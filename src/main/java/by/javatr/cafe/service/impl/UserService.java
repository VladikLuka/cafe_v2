package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.util.Cache;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.util.HashPassword;

@Component
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAddressRepository addressRepository;
    @Autowired
    Cache cache;

    private UserService() {}

    @Override
    public User find(int id) throws ServiceException {

        try {
            User user= new User();
            user.setId(id);
            user = userRepository.findUser(user);
            user.setAddress(addressRepository.getAllId(user.getId()));
            return user;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean replenishBalance(User user) throws ServiceException {

        try {
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return true;
    }

    @Override
    public User update(User user) throws ServiceException {

        try {
            userRepository.update(user);
            cache.updateUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean delete(int id) throws ServiceException {

        try {
            User user = new User(id);
            Address address = new Address(id);
            addressRepository.delete(address);
            userRepository.delete(user);
            return true;
        }catch (DAOException e){
            throw new ServiceException("Failed delete user", e);
        }
    }


    @Override
    public User loginUser(User user) throws ServiceException {

        user.setPassword(hashPassword(user.getPassword()));
        try {
            user = userRepository.find(user.getMail(), user.getPassword());
            if(user!=null){
                user.setAddress(addressRepository.getAllId(user.getId()));
                return user;
            }

        } catch (DAOException e) {
            throw new ServiceException("Login User Exception", e);
        }
        return null;
    }


    @Override
    public User createUser(User user) throws ServiceException {

        user.setPassword(hashPassword(user.getPassword()));
        try {
            user = userRepository.create(user);
            user.setRole(Role.USER);
            cache.addUser(user);
        } catch (DAOException e) {
            throw new ServiceException(" Create User Exception", e);
        }

        return user;
    }

    private String hashPassword(String password){
        return HashPassword.hashPass(password);
    }


}
