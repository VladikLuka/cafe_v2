package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MySqlUserRepositoryTest {

    static MySqlUserRepository userRepository;
    static Connection connection;

    @BeforeAll
    static void setUp() throws DIException, SQLException, DAOException {

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(absolutePath);

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/epam-cafe-test?useUnicode=true&serverTimezone=UTC&useSSL=false",
                "root",
                "vladislav7890");

        userRepository = new MySqlUserRepository(connection);

        userRepository.create(new User("Vlad", "Lukien", "+375333044914", "TEST@GMAIL.COM", "qwe123qwe123", new ArrayList<>()));
        userRepository.create(new User("Vlad2", "Lukien2", "+375333044915", "TEST2@GMAIL.COM", "qwe123qwe123", new ArrayList<>()));

    }

    @AfterAll
    static void tearDown() throws SQLException {

        connection.prepareStatement("truncate user").execute();

        connection.close();

    }

    @Test
    void getAllUser() throws DAOException {

        final List<User> allUser = userRepository.getAllUser();

        assertEquals(2, allUser.size());


    }

    @Test
    void findUser() throws DAOException {

        final User user = userRepository.findUser(new User(1));

        assertEquals(1, user.getId());
        assertEquals("Vlad", user.getName());

    }

    @Test
    void find() throws DAOException {

        final User user = userRepository.find("TEST@GMAIL.COM", "qwe123qwe123");

        assertEquals(1, user.getId());
        assertEquals("TEST@GMAIL.COM", user.getMail());
        assertEquals("qwe123qwe123", user.getPassword());


    }

    @Test
    void delete() throws DAOException {

        User user = userRepository.create(new User("Vlad3", "Lukien3", "+375333044911", "TEST3@GMAIL.COM", "qwe123qwe123", new ArrayList<>()));

        boolean delete = userRepository.delete(user);

        assertTrue(delete);


    }

    @Test
    void create() throws DAOException {

        User user = userRepository.create(new User("Vlad4", "Lukien4", "+375333044921", "TEST4@GMAIL.COM", "qwe123qwe123", new ArrayList<>()));

        final User foundUser = userRepository.find("TEST4@GMAIL.COM", "qwe123qwe123");

        assertEquals("TEST4@GMAIL.COM", foundUser.getMail());
        assertEquals("Vlad4", foundUser.getName());
        assertEquals("Lukien4", foundUser.getSurname());
    }

    @Test
    void update() throws DAOException {

        User user = userRepository.create(new User("Vlad5", "Lukien5", "+375333044945", "TEST5@GMAIL.COM", "qwe123qwe123", new ArrayList<>()));

        user.setMail("UPDATED@MGMAIL.COM");

        final User update = userRepository.update(user);

        assertEquals("UPDATED@MGMAIL.COM", update.getMail());

    }
}