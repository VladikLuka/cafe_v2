package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.constant.DbColumns;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.dao.repository.AbstractRepository;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.util.Cache;

import java.sql.*;
import java.util.*;

/**
 * Contains methods for working with the database.
 * User table
 */
@Component
public class MySqlUserRepository extends AbstractRepository<User> implements IUserRepository {

    private static final String FIND_ALL_USERS = "select * from user left join role on role.role_id = user.roles_role_id";
    private static final String FIND_USER_BY_EMAIL_PSW = "select * from user left join role on user.roles_role_id = role.role_id where user.user_email = ? and user.user_password = ?;";
    private static final String GET_USER = "select * from user left join role on role.role_id = user.roles_role_id where user.user_id = ?";

    @Autowired
    private Cache cache;

    /**
     * Returns all users
     *
     * @return list of users
     */
    @Override
    public List<User> getAllUser() throws DAOException {

        if (!cache.getListUser().isEmpty()) {
            return cache.getListUser();
        }

        ArrayList<User> userList = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS);
                ResultSet resultSet = statement.executeQuery();
        ) {

            if (resultSet == null) {
                return Collections.emptyList();
            }

            while (resultSet.next()) {
                User user = new User();
                connection.setAutoCommit(false);
                user.setId(resultSet.getInt(DbColumns.USER_ID));
                user.setName(resultSet.getString(DbColumns.USER_NAME));
                user.setSurname(resultSet.getString(DbColumns.USER_SURNAME));
                user.setPhone(resultSet.getString(DbColumns.USER_PHONE));
                user.setMail(resultSet.getString(DbColumns.USER_EMAIL));
                user.setPassword(resultSet.getString(DbColumns.USER_PASSWORD));
                user.setMoney(resultSet.getBigDecimal(DbColumns.USER_MONEY));
                user.setLoyaltyPoint(resultSet.getInt(DbColumns.USER_LOYALTY));
                user.setRole(Role.getRoleByName(resultSet.getString(DbColumns.ROLE_NAME)));
                user.setBan(resultSet.getBoolean(DbColumns.USER_IS_BAN));
                user.setCredit(resultSet.getBoolean(DbColumns.USER_IS_CREDIT));
                userList.add(user);
                connection.commit();
            }
        } catch (SQLException e) {
            throw new DAOException("an error occurred in a method getAllUser", e);
        }
        return userList;
    }

    /**
     * Returns user by ID
     *
     * @param user being returned
     * @return found user
     */
    @Override
    public User findUser(User user) throws DAOException {

        if (cache.getUser(user.getId()) != null) {
            return cache.getUser(user.getId());
        }

        try (Connection connection = ConnectionPool.CONNECTION_POOL.retrieve();
             PreparedStatement statement = connection.prepareStatement(GET_USER);
        ) {
            statement.setInt(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery();) {

                while (resultSet.next()) {
                    connection.setAutoCommit(false);
                    user.setId(resultSet.getInt(DbColumns.USER_ID));
                    user.setName(resultSet.getString(DbColumns.USER_NAME));
                    user.setSurname(resultSet.getString(DbColumns.USER_SURNAME));
                    user.setPhone(resultSet.getString(DbColumns.USER_PHONE));
                    user.setMail(resultSet.getString(DbColumns.USER_EMAIL));
                    user.setPassword(resultSet.getString(DbColumns.USER_PASSWORD));
                    user.setMoney(resultSet.getBigDecimal(DbColumns.USER_MONEY));
                    user.setLoyaltyPoint(resultSet.getInt(DbColumns.USER_LOYALTY));
                    user.setRole(Role.getRoleByName(resultSet.getString(DbColumns.ROLE_NAME)));
                    user.setBan(resultSet.getBoolean(DbColumns.USER_IS_BAN));
                    user.setCredit(resultSet.getBoolean(DbColumns.USER_IS_CREDIT));
                    connection.commit();
                }
            }
        } catch (SQLException e) {
            throw new DAOException("an error occurred in findUser ", e);
        }

        return user;
    }

    /**
     * Return user
     *
     * @param email    user email
     * @param password user password
     * @return found user
     */
    @Override
    public User find(String email, String password) throws DAOException {
        User user = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_PSW);
        ) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {


                while (resultSet.next()) {
                    user = new User();
                    connection.setAutoCommit(false);
                    user.setId(resultSet.getInt(DbColumns.USER_ID));
                    user.setName(resultSet.getString(DbColumns.USER_NAME));
                    user.setSurname(resultSet.getString(DbColumns.USER_SURNAME));
                    user.setPhone(resultSet.getString(DbColumns.USER_PHONE));
                    user.setMail(resultSet.getString(DbColumns.USER_EMAIL));
                    user.setPassword(resultSet.getString(DbColumns.USER_PASSWORD));
                    user.setMoney(resultSet.getBigDecimal(DbColumns.USER_MONEY));
                    user.setLoyaltyPoint(resultSet.getInt(DbColumns.USER_LOYALTY));
                    user.setRole(Role.getRoleByName(resultSet.getString(DbColumns.ROLE_NAME)));
                    user.setBan(resultSet.getBoolean(DbColumns.USER_IS_BAN));
                    user.setCredit(resultSet.getBoolean(DbColumns.USER_IS_CREDIT));
                    connection.commit();
                }

            }
            if (Objects.nonNull(user)) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("an error occurred in EM_PSW find user", e);
        }

        return null;
    }

    /**
     * Delete user
     *
     * @param user user being deleted
     * @return boolaen
     * @throws DAOException
     */
    @Override
    public boolean delete(User user) throws DAOException {

        try (Connection connection = getConnection();) {
            connection.setAutoCommit(false);
            super.delete(connection, user);
            connection.commit();
            cache.deleteUser(user);
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return true;
    }


    /**
     * Create user
     *
     * @param user user being created
     * @return created user
     */
    @Override
    public User create(User user) throws DAOException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            user = super.create(connection, user);
            connection.commit();
            cache.addUser(user);
        } catch (SQLException throwables) {
            throw new DAOException(throwables);
        }
        return user;
    }

    /**
     * Update user
     *
     * @param user user being updated
     * @return updated user
     */
    @Override
    public User update(User user) throws DAOException {

        Connection connection = getConnection();
        user = super.update(connection, user);
        cache.updateUser(user);

        return user;
    }
    private MySqlUserRepository() {}
}