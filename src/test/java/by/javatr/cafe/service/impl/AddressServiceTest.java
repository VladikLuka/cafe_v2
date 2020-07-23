package by.javatr.cafe.service.impl;


import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.dao.repository.impl.MySqlAddressRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.exception.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class AddressServiceTest {

    static AddressService addressService;
    static IAddressRepository addressRepository;

    @BeforeAll
    static void setUp() throws DIException {


            File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(" " + absolutePath);

        addressService = (AddressService) BeanFactory.getInstance().getBean("addressService");
        MySqlAddressRepository mock = Mockito.mock(MySqlAddressRepository.class);
        addressService.setAddressRepository(mock);
        addressRepository = mock;
    }

    @AfterAll
    static void tearDown() {
    }


    @Test
    void update() throws DAOException, ServiceException, SQLException {

        Address address = new Address(1, "Minsk", "Ratomskaya", "15", "52", 176);

        when(addressRepository.update(address)).thenReturn(
                new Address(1, "Minsk", "Ratomskaya", "15", "52", 176));

        Address address1 = addressService.update(address);

        assertEquals(address, address1);

    }

    @Test
    void update_Should_Throw_Exception() throws DAOException, ServiceException, SQLException {

        Address address = new Address(1, "Minsk", "Ratomskaya", "15", "52", 176);

        given(addressRepository.update(new Address(1, "Minsk", "Ratomskaya", "15", "52", 176))).willThrow(
                DAOException.class);

        assertThrows(ServiceException.class, ()->{
            addressService.update(address);
        });

    }

    @Test
    void getAll() throws DAOException, ServiceException{

        when(addressRepository.getAll()).thenReturn(new ArrayList<>());

        final List<Address> all = addressService.getAll();

        assertEquals(new ArrayList<>(), all);

    }

    @Test
    void getAll_Exception() throws DAOException, ServiceException{

        when(addressRepository.getAll()).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            addressService.getAll();
        });

    }

    @Test
    void getAllForUser() throws DAOException, ServiceException {

        int user_id = 1;

        when(addressRepository.getAllId(user_id)).thenReturn(new ArrayList<>());
        final List<Address> all = addressService.getAllForUser(user_id);

        assertEquals(new ArrayList<>(), all);

    }

    @Test
    void getAllForUser_Should_Return_Exception() throws DAOException, ServiceException {

        int user_id = 1;

        when(addressRepository.getAllId(user_id)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            addressService.getAllForUser(user_id);
        });
    }


    @Test
    void create() throws DAOException, ServiceException {

        Address address = new Address(1);

        when(addressRepository.create(address)).thenReturn(address);

        final Address address1 = addressService.create(address);

        assertEquals(address1, address);


    }

    @Test
    void create_Should_Return_Exception() throws DAOException, ServiceException {

        Address address = new Address(1);

        when(addressRepository.create(address)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            addressService.create(address);
        });

    }

    @Test
    void find() throws DAOException, ServiceException {

        Address address = new Address(1);

        when(addressRepository.get(address)).thenReturn(address);

        final Address address1 = addressService.find(address);

        assertEquals(address, address1);

    }

    @Test
    void find_Should_Return_Exception() throws DAOException, ServiceException {

        Address address = new Address(1);

        when(addressRepository.get(address)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
           addressService.find(address);
        });

    }


    @Test
    void delete_Should_Return_True() throws DAOException, ServiceException {

        Address address = new Address(1);

        when(addressRepository.delete(address)).thenReturn(true);

        boolean t = addressService.delete(address);

        assertTrue(t);

    }

    @Test
    void delete_Should_Return_False() throws DAOException, ServiceException {

        Address address = new Address(1);

        when(addressRepository.delete(address)).thenReturn(false);

        boolean t = addressService.delete(address);

        assertFalse(t);

    }


    @Test
    void delete_Should_Return_Exception() throws DAOException, ServiceException {

        Address address = new Address(1);

        when(addressRepository.delete(address)).thenThrow(DAOException.class);
        addressService.delete(address);

        assertThrows(ServiceException.class, ()->{
            addressService.delete(address);
        });
    }

}
