package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.DAOFactory;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.ServiceException;

import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.util.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;



/**
 * contains methods for working with users
 */
@Component
public class UserService implements IUserService {

    private UserService() {}

    /**
     * Find dish bu ID
     * @param userId user ID
     * @return found user
     */
    @Override
    public User find(int userId) throws ServiceException {

        try (DAOFactory factory = new DAOFactory()){
            IAddressRepository addressRepository = factory.getAddressRepository();
            IUserRepository userRepository = factory.getUserRepository();

            User user= new User();
            user.setId(userId);
            user = userRepository.findUser(user);

            if(user != null){
                user.setAddress(addressRepository.getAllId(user.getId()));
            }

            return user;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Update user
     * @param user to be updated
     * @return updated user
     */
    @Override
    public User update(User user) throws ServiceException {

        try (DAOFactory factory = new DAOFactory()){
            IUserRepository userRepository = factory.getUserRepository();
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    /**
     * Delete user by ID
     * @param userId user ID
     * @return boolean
     */
    @Override
    public boolean delete(int userId) throws ServiceException {

        DAOFactory factory = new DAOFactory();

        try {

            IAddressRepository addressRepository = factory.getAddressRepository();
            IUserRepository userRepository = factory.getUserRepository();

            User user = new User(userId);
            Address address = new Address();
            address.setUserId(userId);
            addressRepository.delete(address);
            userRepository.delete(user);
            return true;
        }catch (DAOException e){
            factory.rollback();
            throw new ServiceException("Failed delete user", e);
        }finally {
            factory.endTransaction();
        }
    }

    /**
     * Find user by email and password
     * @param user user to be found
     * @return found user
     */
    @Override
    public User loginUser(User user) throws ServiceException {

        user.setPassword(Utils.hashPass(user.getPassword()));
        try (DAOFactory factory = new DAOFactory()){

            IUserRepository userRepository = factory.getUserRepository();
            IAddressRepository addressRepository = factory.getAddressRepository();

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

    /**
     * Create user
     * @param user to be created
     * @return created user
     */
    @Override
    public User createUser(User user) throws ServiceException {

        user.setPassword(Utils.hashPass(user.getPassword()));
        try (DAOFactory factory = new DAOFactory()){
            IUserRepository userRepository = factory.getUserRepository();
            user = userRepository.create(user);
            user.setRole(Role.USER);
        } catch (DAOException e) {
            throw new ServiceException(" Create User Exception", e);
        }

        return user;
    }

    /**
     * Add money to user
     * @param amount amount of money to be added
     * @param userId user id
     * @return user
     */
    @Override
    public User addMoney(BigDecimal amount, int userId) throws ServiceException {
        User user = null;
        amount = amount.setScale(2, RoundingMode.HALF_UP);

        try (DAOFactory factory = new DAOFactory()){
            user = find(userId);

            IUserRepository userRepository = factory.getUserRepository();

            user.setMoney(user.getMoney().add(amount));
            userRepository.update(user);
        } catch (DAOException e) {
            user.setMoney(user.getMoney().subtract(amount));
            throw new ServiceException(e);
        }

        return user;
    }

    /**
     * Add points to user
     * @param point amount of points to be added
     * @param userId user id
     * @return user
     */
    @Override
    public User addPoints(int point, int userId) throws ServiceException {
        User user = null;

        try (DAOFactory factory = new DAOFactory()) {
            user = find(userId);

            IUserRepository userRepository = factory.getUserRepository();

            user.setLoyaltyPoint(user.getLoyaltyPoint() + point);
            userRepository.update(user);
        } catch (DAOException e) {
            user.setLoyaltyPoint(user.getLoyaltyPoint() - point);
            throw new ServiceException(e);
        }

        return user;
    }

    /**
     * Subtract money to user
     * @param amount amount of money to be subtracted
     * @param userId user id
     * @return user
     */
    @Override
    public User subtractMoney(BigDecimal amount, int userId) throws ServiceException {
        User user = null;
        final BigDecimal zero = new BigDecimal(0);
        try (DAOFactory factory = new DAOFactory()){

            IUserRepository userRepository = factory.getUserRepository();

            amount = amount.setScale(2, RoundingMode.HALF_UP);

            user = find(userId);

            if(user!= null){
                user.setMoney(user.getMoney().subtract(amount));
                if (user.getMoney().compareTo(zero) < 0) {
                    user.setMoney(zero);
                }
                userRepository.update(user);
            }

        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    /**
     * Subtract point to user
     * @param amount amount of points to be subtracted
     * @param userId user id
     * @return user
     */
    @Override
    public User subtractPoints(int amount, int userId) throws ServiceException {
        User user = null;
        try (DAOFactory factory = new DAOFactory()){

            IUserRepository userRepository = factory.getUserRepository();

            user = find(userId);
            user.setLoyaltyPoint(user.getLoyaltyPoint() - amount);
            if(user.getLoyaltyPoint() < 0){
                user.setBan(true);
                user.setLoyaltyPoint(0);
            }
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    /**
     * Ban user
     * @param userId user ID
     * @return banned user
     */
    @Override
    public User banUser(int userId) throws ServiceException {

        try (DAOFactory factory = new DAOFactory();){

            IUserRepository userRepository = factory.getUserRepository();

            User user = new User(userId);
            user = userRepository.findUser(user);

            user.setBan(true);

            user = userRepository.update(user);
            return user ;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Unban user
     * @param userId user id
     * @return user
     */
    @Override
    public User unbanUser(int userId) throws ServiceException {

        try (DAOFactory factory = new DAOFactory();){

            IUserRepository userRepository = factory.getUserRepository();

            User user = new User(userId);
            user = userRepository.findUser(user);

            if(user != null){
                user.setBan(false);
                user = userRepository.update(user);
            }

            return user ;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User makeAdmin(int userId) throws ServiceException {
        try (DAOFactory factory = new DAOFactory();){

            IUserRepository userRepository = factory.getUserRepository();

            User user = new User(userId);
            user = userRepository.findUser(user);

            if(user != null){
                user.setRole(Role.ADMIN);
                user = userRepository.update(user);
            }

            return user ;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User makeUser(int userId) throws ServiceException {
        try (DAOFactory factory = new DAOFactory();){

            IUserRepository userRepository = factory.getUserRepository();

            User user = new User(userId);
            user = userRepository.findUser(user);

            if(user != null){
                user.setRole(Role.USER);
                user = userRepository.update(user);
            }

            return user ;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
