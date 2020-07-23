package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MySqlAddressRepositoryTest {

    static IAddressRepository addressRepository;
    static Connection connection;

    @BeforeAll
    static void setUp() throws DIException, ClassNotFoundException, SQLException {

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(" " + absolutePath);

        addressRepository = (IAddressRepository) BeanFactory.getInstance().getBean("mySqlAddressRepository");

        Class.forName("com.mysql.cj.jdbc.Driver");

        final ResourceBundle dbBundle = ResourceBundle.getBundle("db");

        connection = DriverManager.getConnection(dbBundle.getString("testDbUrl"), dbBundle.getString("testDbUser"), dbBundle.getString("testDbPassword"));
    }

    @AfterAll
    static void tearDown() throws SQLException {

        connection.close();
    }

    @Test
    void getAll() throws SQLException, DAOException {

        String get_all = "select * from address";

        ArrayList<Address> list = new ArrayList<>();

        final PreparedStatement preparedStatement = connection.prepareStatement(get_all);
        final ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Address address = new Address();
            address.setId(resultSet.getInt(1));
            address.setCity(resultSet.getString(2));
            address.setStreet(resultSet.getString(3));
            address.setHouse(resultSet.getString(4));
            address.setFlat(resultSet.getString(5));
            address.setUserId(resultSet.getInt(6));
            address.setAvailable(resultSet.getBoolean(7));
            list.add(address);
        }

        final List<Address> all = addressRepository.getAll();

        assertEquals(list, all);

    }

    @Test
    void getAllId() {
    }

    @Test
    void get() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}