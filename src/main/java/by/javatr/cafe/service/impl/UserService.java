package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

import java.util.List;

import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.validation.HashPassword;

@Component
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAddressRepository addressRepository;



    private static UserService INSTANCE;

    public static UserService getInstance(){

        if(INSTANCE == null){
            INSTANCE = new UserService();
        }

        return INSTANCE;
    }

    private UserService() {}

    @Override
    public List<User> findAllUser() {

        try {
            final List<User> allUser = userRepository.getAllUser();
            final List<Address> all = addressRepository.getAll();
            for (User user:allUser) {

            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
    public boolean delete(int id) throws ServiceException {

        try {
            addressRepository.delete(id);
            userRepository.deleteUser(id);
            return true;
        }catch (DAOException e){
            throw new ServiceException("Failed delete user", e);
        }
    }


    @Override
    public User loginUser(User user) throws ServiceException {

        user.setPassword(hashPassword(user.getPassword()));
        try {
            user = userRepository.findUser(user.getMail(), user.getPassword());
            user.setAddress(addressRepository.getAllId(user.getId()));
        } catch (DAOException e) {
            throw new ServiceException("Login User Exception", e);
        }

        return user;
    }


    @Override
    public User createUser(User user) throws ServiceException {

        user.setPassword(hashPassword(user.getPassword()));
        try {
            userRepository.createUser(user);
        } catch (DAOException e) {
            throw new ServiceException(" Create User Exception", e);
        }

        return user;
    }

    private String hashPassword(String password){
        return HashPassword.hashPass(password);
    }


}
