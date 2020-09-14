package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlAddressRepositoryTest {

    static MySqlAddressRepository addressRepository;
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

        addressRepository = new MySqlAddressRepository(connection);

        addressRepository.create(new Address(1, "Minsk", "Ratomskaya", "15", "52", 1));
        addressRepository.create(new Address(2, "Minsk", "Oktyabrskaya", "6", "309", 1));

    }

    @AfterAll
    static void tearDown() throws DAOException, SQLException {

        connection.prepareStatement("truncate address").execute();

        connection.close();

    }

    @Test
    void getAll() throws SQLException, DAOException {

        final List<Address> all = addressRepository.getAll();

        assertEquals(2, all.size());


    }

    @Test
    void getAllId() throws DAOException {

        final List<Address> allId = addressRepository.getAllId(1);

        assertEquals(2, allId.size());

    }

    @Test
    void get() throws DAOException {

        Address expected_address = new Address(1, "Minsk", "Ratomskaya", "15", "52", 1);

        final Address address = addressRepository.get(new Address(1));

        System.out.println(address);

        assertEquals(expected_address, address);

    }

    @Test
    void create() throws DAOException {

        Address address = new Address(40, "Minsk", "Ratomskaya", "15", "52", 1);

        addressRepository.create(address);

        final Address address2 = addressRepository.get(address);

        assertNotNull(address2);

        addressRepository.delete(address);

    }

    @Test
    void update() throws DAOException {

        Address address = new Address(5, "Minsk", "Ratomskaya", "15", "52", 1);

        addressRepository.create(address);

        final Address updated = addressRepository.update(new Address(30, "Minsk", "Novaya", "25", "25", 1));

        final Address address1 = addressRepository.get(updated);

        assertEquals(updated, address1);

        addressRepository.delete(address);

    }

    @Test
    void delete() throws DAOException {

        Address address = new Address(30, "Minsk", "Ratomskaya", "15", "52", 1);

        addressRepository.create(address);

        final boolean delete = addressRepository.delete(address);

        assertTrue(delete);

    }
}