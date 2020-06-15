package by.javatr.cafe.constant;

public class DBQuery {

    /**
     * USER QUERY
     */
    public static final String FIND_USER_ID = "SELECT * FROM user LEFT JOIN role ON user.roles_role_id = role.role_id WHERE user.user_id = ?";
    public static final String FIND_USER_MAIL_PASS = "SELECT * FROM user LEFT JOIN role ON user.roles_role_id = role.role_id WHERE user.user_email = ? and user.user_password = ?";
    public static final String DELETE_USER_ID = "DELETE FROM ADDRESS WHERE address.address_id = ?; DELETE FROM user WHERE user.user_id = ?";
    public static final String CREATE_USER = "INSERT INTO USER (user_name, user_surname, user_phone, user_email, user_password) VALUES ( ?, ?, ?, ?, ?);";
    public static final String UPDATE_USER = "UPDATE user SET (update user set user_id = ?, user_name = ? ,user_surname = ?, user_email =?,user_phone = ?,  user_password = ?, user_money = ?, user_loyaltyPoints = ?, roles_role_id = ? where user_id = ?)";



    public static final String GET_LAST_ID = "SELECT LAST_INSERT_ID()";


}
