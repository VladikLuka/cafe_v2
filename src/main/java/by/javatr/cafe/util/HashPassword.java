package by.javatr.cafe.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    private static final Logger logger = LogManager.getLogger(HashPassword.class);

    public static String hashPass(String password){

        StringBuilder sb = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());

            sb = new StringBuilder();

            for (byte b : bytes) {
                sb.append(String.format("%02x",b));
            }

        } catch (NoSuchAlgorithmException e) {
            logger.error("Invalid hash password", e);
        }




        return sb.toString();
    }
}
