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

import java.math.BigDecimal;

@Component
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAddressRepository addressRepository;

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
    public User update(User user) throws ServiceException {

        try {
            userRepository.update(user);
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
        } catch (DAOException e) {
            throw new ServiceException(" Create User Exception", e);
        }

        return user;
    }


    @Override
    public User addMoney(BigDecimal amount, int user_id) throws ServiceException {
        User user = null;
        try {
            user = find(user_id);
            user.setMoney(user.getMoney().add(amount));
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User addPoints(int point, int user_id) throws ServiceException {
        User user = null;
        try {
            user = find(user_id);
            user.setLoyalty_point(user.getLoyalty_point() + point);
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User subtractMoney(BigDecimal amount, int user_id) throws ServiceException {
        User user = null;
        final BigDecimal zero = new BigDecimal(0);
        try {
            user = find(user_id);
            user.setMoney(user.getMoney().subtract(amount));
            if (user.getMoney().compareTo(zero) < 0) {
                user.setMoney(zero);
            }
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User subtractPoints(int amount, int user_id) throws ServiceException {
        User user = null;
        try {
            user = find(user_id);
            user.setLoyalty_point(user.getLoyalty_point() - amount);
            if(user.getLoyalty_point() < 0){
                user.setLoyalty_point(0);
            }
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    @Override
    public User banUser(int id) throws ServiceException {

        try {
            User user = new User(id);
            user = userRepository.findUser(user);

            user.setBan(true);

            user = userRepository.update(user);
            return user ;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User unbanUser(int id) throws ServiceException {

        try {
            User user = new User(id);
            user = userRepository.findUser(user);

            user.setBan(false);

            user = userRepository.update(user);
            return user ;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private String hashPassword(String password){
        return HashPassword.hashPass(password);
    }

}
