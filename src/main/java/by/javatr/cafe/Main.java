package by.javatr.cafe;

import by.javatr.cafe.connection.impl.ConnectionPool;
import by.javatr.cafe.dao.repository.AbstractRepository;
import by.javatr.cafe.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main extends AbstractRepository<User>{



    public static void main(String[] args) throws SQLException, InterruptedException {

        DateFormat instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            final Date parse = instance.parse("2020-09-15 10:10:10");

            final long credit_time = parse.getTime();

            final long now = Calendar.getInstance().getTime().getTime();

            long differ = credit_time - now;


            long days = differ / 86_400_000;

            System.out.println(days);

        }catch (Exception e){

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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            retrieve.close();
            statement.close();
        }



    }

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