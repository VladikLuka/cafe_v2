package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.DBQuery;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

@Component
public class MySqlUserRepository extends AbstractRepository implements IUserRepository {

    private static final String FIND_ALL_USERS = "select * from user left join role on role.role_id = user.roles_role_id";
    private static final String CREATE_USER = "INSERT INTO user (user_name, user_surname, user_phone, user_email, user_password) VALUES ( ?, ?, ?, ?, ?);";
    private static final String CREATE_USER_EMAIL_PASSWRD_PHONE = "INSERT INTO user (user_email, user_password, user_phone) VALUES ( ?, ?, ?);";
    private static final String FIND_USER_BY_ID = "select * from user left join address on address.user_id = user.user_id where user.user_id = ?;";
    private static final String FIND_USER_BY_EMAIL = "select * from user left join address on user.user_id = address.address_id where user_email = ?;";
    private static final String FIND_USER_BY_EMAIL_PSW = "select * from user left join role on user.roles_role_id = role.role_id where user.user_email = ? and user.user_password = ?;";
    private static final String DELETE_USER_BY_ID = "delete from user where user_id = ?;";
    private static final String GET_USER_ID = "select user_id from user where user_email = ?";
    private static final String UPDATE_USER_BY_ID = "update user set user_id = ?, user_name = ? ,user_surname = ?, user_email =?,user_phone = ?,  user_password = ?, user_money = ?, user_loyaltyPoints = ?, roles_role_id = ? where user_id = ?";
    private static final String GET_LAST_ID = "select LAST_INSERT_ID()";
    private static final String GET_USR = "select * from user left join role on role.role_id = user.roles_role_id where user.user_id = ?";
    private static final String CREATE = "INSERT INTO user (user_name, user_surname, user_phone, user_email, user_password) VALUES ( ?, ?, ?, ?, ?);";

    Logger logger = LogManager.getLogger(MySqlUserRepository.class);

    @Override
    public List<User> getAllUser() throws DAOException {

        ArrayList<User> userList = new ArrayList<>();

        try (
                final Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS);
                final ResultSet resultSet = statement.executeQuery();
        ){

            if (resultSet == null) {
                return Collections.emptyList();
            }
            while (resultSet.next()) {
                User retrieveUser = new User();
                connection.setAutoCommit(false);
                retrieveUser.setId(resultSet.getInt("user_id"));
                retrieveUser.setName(resultSet.getString("user_name"));
                retrieveUser.setSurname(resultSet.getString("user_surname"));
                retrieveUser.setPhone(resultSet.getString("user_phone"));
                retrieveUser.setMail(resultSet.getString("user_email"));
                retrieveUser.setPassword(resultSet.getString("user_password"));
                retrieveUser.setLoyalty_point(resultSet.getInt("user_loyaltyPoints"));
                retrieveUser.setRole(Role.getRoleByName(resultSet.getString("role_name")));
                userList.add(retrieveUser);
                connection.commit();
            }
        } catch (SQLException e) {
            throw new DAOException("an error occurred in a method getAllUser", e);
        }

        return userList;
    }

    @Override
    public User findUser(User user) throws DAOException {

        ResultSet resultSet = null;

        try(Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
//            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID);
            PreparedStatement statement = connection.prepareStatement(GET_USR);
            ){
            statement.setInt(1, user.getId());
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                connection.setAutoCommit(false);
                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("user_name"));
                user.setSurname(resultSet.getString("user_surname"));
                user.setPhone(resultSet.getString("user_phone"));
                user.setMail(resultSet.getString("user_email"));
                user.setPassword(resultSet.getString("user_password"));
                user.setMoney(resultSet.getBigDecimal("user_money"));
                user.setLoyalty_point(resultSet.getInt("user_loyaltyPoints"));
                user.setRole(Role.getRoleByName(resultSet.getString("role_name")));

                connection.commit();
            }
        } catch (SQLException e) {
            throw new DAOException("an error occurred in findUser ", e);
        }finally {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throw new DAOException("ResultSet is null ", throwables);
            }
        }

        return user;
    }

    @Override
    public User findUser(String email, String password) throws DAOException {
        User user = null;

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_PSW);
        ){
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                user = new User();
                connection.setAutoCommit(false);
                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("user_name"));
                user.setSurname(resultSet.getString("user_surname"));
                user.setPhone(resultSet.getString("user_phone"));
                user.setMail(resultSet.getString("user_email"));
                user.setPassword(resultSet.getString("user_password"));
                user.setMoney(resultSet.getBigDecimal("user_money"));
                user.setLoyalty_point(resultSet.getInt("user_loyaltyPoints"));
                user.setRole(Role.getRoleByName(resultSet.getString("role_name")));
                connection.commit();
            }


            if (Objects.nonNull(user)){
                if(user.getPassword().equals(password)){
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("an error occurred in EM_PSW find user", e);
        }

        return null;
    }


    @Override
    public boolean deleteUser(int id) throws DAOException {

            try(Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DBQuery.DELETE_USER_ID)){
                connection.setAutoCommit(false);
                statement.setInt(1, id);
                statement.executeUpdate();
                connection.commit();
                return true;
            } catch (SQLException e) {
            throw new DAOException("an error occurred in delete user", e);
        }
    }

    @Override
    public User createUser(User user) throws DAOException {

        try (            final Connection connection = getConnection();
                         PreparedStatement statement = connection.prepareStatement(CREATE_USER);
        ){
            connection.setAutoCommit(false);
            statement.setString(4, user.getMail());
            statement.setString(5, user.getPassword());
            statement.setString(3, user.getPhone());
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement(GET_LAST_ID);
            ResultSet resultSet = statement1.executeQuery();
            resultSet.next();
            user.setId(resultSet.getInt("last_insert_id()"));
            connection.commit();
        }catch (SQLException e) {
            throw new DAOException("an occurred in createUser", e);
        }finally {

        }
        return user;
    }

//    @Override
//    public User createUser(User user) throws DAOException {
//
//            try (            final Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
//                             PreparedStatement statement = connection.prepareStatement(CREATE_USER);
//            ){
//                    connection.setAutoCommit(false);
//                    statement.setString(1, user.getName());
//                    statement.setString(2, user.getSurname());
//                    statement.setString(3, user.getPhone());
//                    statement.setString(4, user.getMail());
//                    statement.setString(5, user.getPassword());
//                    statement.executeUpdate();
//                    PreparedStatement statement1 = connection.prepareStatement(GET_LAST_ID);
//                    ResultSet resultSet = statement1.executeQuery();
//                    resultSet.next();
//                    user.setId(resultSet.getInt("last_insert_id()"));
//                    connection.commit();
//                } catch (SQLException e) {
//                    throw new DAOException("an occurred in create user", e);
//            }
//            return user;
//    }

    @Override
    public User update(User user) throws DAOException {
        try (        Connection connection = getConnection();
                     PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID);
                     ){
            connection.setAutoCommit(false);
            statement.setInt(1,user.getId());
            statement.setString(2,user.getName());
            statement.setString(3,user.getSurname());
            statement.setString(4,user.getMail());
            statement.setString(5,user.getPhone());
            statement.setString(6,user.getPassword());
            statement.setBigDecimal(7,user.getMoney());
            statement.setInt(8,user.getLoyalty_point());
            statement.setInt(9,Role.getRoleID(user.getRole()));
            statement.setInt(10,user.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException("an error occurred in update user metod", e);
        }

        return user;
    }


//    private int getUserID(Connection connect,User user) throws DAOException {
//
//        ResultSet resultSet = null;
//        int id = 0;
//        try (
//                PreparedStatement statement = connect.prepareStatement(GET_USER_ID);
//                ){
//
//            statement.setString(1, user.getMail());
//            resultSet = statement.executeQuery();
//            if(resultSet.next()){
//                id = resultSet.getInt(1);
//            }
//        } catch (SQLException throwables) {
//            throw new DAOException("an error occurred while trying to get the last id", throwables);
//        }finally {
//            try {
//                resultSet.close();
//            } catch (SQLException throwables) {
//                throw new DAOException("result set is null");
//            }
//        }
//
//        return id;
//    }


    private MySqlUserRepository() {}
}