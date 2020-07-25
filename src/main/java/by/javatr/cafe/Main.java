package by.javatr.cafe;

import by.javatr.cafe.aspectj.log.annotation.LogException;
import by.javatr.cafe.dao.connection.impl.ConnectionPool;
import by.javatr.cafe.dao.repository.AbstractRepository;
import by.javatr.cafe.entity.Entity;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends AbstractRepository<User> {

    @Override
    public User update(Connection connection, Entity<User> entity) throws DAOException {
        return super.update(connection, entity);
    }

    public static void main(String[] args) throws SQLException {

        Main main = new Main();

        User user = new User(10, "Vladik", "Luka", "+375333044914", "TESTLOG@gmail.com",
                "222b125eae8e38b3ce07a62bc1320409", new BigDecimal(950), 25, Role.ADMIN, false, false);

        for (int i = 0; i < 30; i++) {

            if(i == 25){
                System.out.println("asd");
            }

            final Connection retrieve = ConnectionPool.CONNECTION_POOL.retrieve();
            try {
                user.setMoney(user.getMoney().add(new BigDecimal(10)));
                main.update(retrieve, user);
                retrieve.close();
                System.out.println("ok " + i);
            } catch (DAOException e) {

            }
        }

    }

    public static void getCon(int money) throws SQLException {


        PreparedStatement statement = null;
        Connection retrieve = null;
        try {
            retrieve = ConnectionPool.CONNECTION_POOL.retrieve();

            statement = retrieve.prepareStatement("update user set user_money=?,roles_role_id=\"1\",user_email=\"TESTLOG@gmail.com\",user_password=\"222b125eae8e38b3ce07a62bc1320409\",user_isCredit=false,user_id=\"10\",user_isBan=false,user_name=\"Vladik\",user_phone=\"+375333044914\",user_surname=\"Luka\",user_loyaltyPoints=\"25\" where user_id = ?");

            statement.setInt(1, money);
            statement.setInt(2, 10);
            statement.executeUpdate();

            System.out.println("OK");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            retrieve.close();
            statement.close();
        }



    }

    @LogException
    public int devide(int a, int b) {
        return a/b;
    }

    public String coord(String str){


        str = str.replaceAll(" ", "\n");

        String res = "";
        int co = 0;
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == ','){
                res +="{ lng: " + str.substring(co+1, i) + ", ";
                co = i;
            }

            if(str.charAt(i) == '\n'){
                res += " lat: " + str.substring(co+1, i) + " }," + "\n";
                co = i;
            }

        }



        return res;
    }

}