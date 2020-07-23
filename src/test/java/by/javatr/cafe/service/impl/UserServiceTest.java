package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.dao.repository.impl.MySqlAddressRepository;
import by.javatr.cafe.dao.repository.impl.MySqlUserRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.Role;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserService userService;
    IAddressRepository addressRepository;
    IUserRepository userRepository;

    @BeforeEach
    public void setUp() throws DIException {

        if(userService != null){
            return;
        }

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(" " + absolutePath);

        userService = (UserService) BeanFactory.getInstance().getBean("userService");
        MySqlUserRepository mock = Mockito.mock(MySqlUserRepository.class);
        userService.setUserRepository(mock);
        userRepository = mock;
        MySqlAddressRepository address_mock = Mockito.mock(MySqlAddressRepository.class);
        userService.setAddressRepository(address_mock);
        addressRepository = address_mock;

    }

    @Test
    void find_Should_Return_User() throws DAOException, ServiceException {

        User user = new User(1);
        List<Address> user_addresses = new ArrayList<>();
        Address address = new Address(1);
        Address address2 = new Address(2);
        Address address3 = new Address(3);
        user_addresses.add(address);
        user_addresses.add(address2);
        user_addresses.add(address3);


        when(userRepository.findUser(user)).thenReturn(user);
        when(addressRepository.getAllId(user.getId())).thenReturn(user_addresses);

        final User user1 = userService.find(1);

        assertNotNull(user1);
        assertEquals(user_addresses, user.getAddress());
        assertEquals(user, user1);
    }

    @Test
    void find_Throw_Exception() throws DAOException {

        User user = new User(1);

        when(userRepository.findUser(user)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            userService.find(user.getId());
        });

    }

    @Test
    void update() throws DAOException, ServiceException {

        User user = new User();

        when(userRepository.update(user)).thenReturn(user);

        User user1 = userService.update(user);

        assertEquals(user, user1);

    }

    @Test
    void update_Should_Throw_Service_Exception() throws DAOException{

        User user = new User();

        when(userRepository.update(user)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            userService.update(user);
        });
    }

    @Test
    void delete() {
    }

    @Test
    void loginUser_Should_Return_notNull_User() throws DAOException, ServiceException {

        User user = new User(1);
        user.setMail("asd@mail.com");
        user.setPassword("12345678");

        ArrayList<Address> user_addresses = new ArrayList<>();

        when(userRepository.find(user.getMail(), Utils.hashPass(user.getPassword()))).thenReturn(user);
        when(addressRepository.getAllId(1)).thenReturn(user_addresses);

        final User user1 = userService.loginUser(user);

        assertEquals(user, user1);
        assertEquals(user_addresses, user.getAddress());

    }


    @Test
    void loginUser_Should_Return_Null_User() throws DAOException, ServiceException {

        User user = new User(1);
        user.setMail("asd@mail.com");
        user.setPassword("12345678");

        ArrayList<Address> user_addresses = new ArrayList<>();

        when(userRepository.find(user.getMail(), Utils.hashPass(user.getPassword()))).thenReturn(null);
        when(addressRepository.getAllId(1)).thenReturn(user_addresses);

        final User user1 = userService.loginUser(user);

        assertNull(user1);
    }


    @Test
    void login_Should_Throw_Service_Exception() throws DAOException{

        User user = new User(1);
        user.setMail("asd@mail.com");
        user.setPassword("12345678");

        when(userRepository.find(user.getMail(), Utils.hashPass(user.getPassword()))).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            userService.loginUser(user);
        });
    }


    @Test
    void createUser_Should_Return_NotNull_User() throws DAOException, ServiceException {

        User user = new User(1);
        user.setMail("asd@mail.com");
        user.setPassword("12345678");


        when(userRepository.create(user)).thenReturn(user);

        final User user1 = userService.createUser(user);

        assertEquals(user, user1);
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void create_Should_Throw_Service_Exception() throws DAOException{

        User user = new User(1);
        user.setMail("asd@mail.com");
        user.setPassword("12345678");

        when(userRepository.create(user)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            userService.createUser(user);
        });
    }


    @Test
    void addMoney() throws ServiceException, DAOException {

        BigDecimal amount = new BigDecimal(10);
        User user = new User(1);
        int user_id = 1;

        when(userRepository.update(user)).thenReturn(user);
        when(userRepository.findUser(user)).thenReturn(user);
        when(addressRepository.getAllId(user.getId())).thenReturn(new ArrayList<>());

        final User user1 = userService.addMoney(amount, user_id);

        assertNotNull(user1);
        assertEquals(new BigDecimal("10.00"), user1.getMoney());

    }

    @Test
    void addPoints() throws DAOException, ServiceException {

        int points = 10;
        int user_id = 1;

        User user = new User(1);

        when(userRepository.update(user)).thenReturn(user);
        when(userRepository.findUser(user)).thenReturn(user);
        when(addressRepository.getAllId(user.getId())).thenReturn(new ArrayList<>());

        final User user1 = userService.addPoints(points, user_id);

        assertEquals(user, user1);
        assertEquals(10, user.getLoyaltyPoint());

    }

    @Test
    void subtractMoney() throws ServiceException, DAOException {

        BigDecimal amount = new BigDecimal(10);
        User user = new User(1);
        int user_id = 1;
        IUserService userService_mock = Mockito.mock(IUserService.class);

        when(userService_mock.find(user_id)).thenReturn(user);
        when(userRepository.update(user)).thenReturn(user);
        when(userRepository.findUser(user)).thenReturn(user);
        when(addressRepository.getAllId(user.getId())).thenReturn(new ArrayList<>());

        final User user1 = userService.subtractMoney(amount, user_id);

        assertNotNull(user1);
        assertEquals(new BigDecimal(0), user1.getMoney());

    }

    @Test
    void subtractPoints() throws DAOException, ServiceException {

        int points = 10;
        int user_id = 1;

        User user = new User(1);

        when(userRepository.update(user)).thenReturn(user);
        when(userRepository.findUser(user)).thenReturn(user);
        when(addressRepository.getAllId(user.getId())).thenReturn(new ArrayList<>());

        final User user1 = userService.subtractPoints(points, user_id);

        assertEquals(user, user1);
        assertTrue(user1.isBan());

    }

    @Test
    void banUser() throws DAOException, ServiceException {

        int user_id = 1;

        User user = new User(1);

        when(userRepository.update(user)).thenReturn(user);
        when(userRepository.findUser(user)).thenReturn(user);

        final User user1 = userService.banUser(user_id);

        assertTrue(user1.isBan());

    }

    @Test
    void unbanUser() throws DAOException, ServiceException {

        int user_id = 1;

        User user = new User(1);

        when(userRepository.update(user)).thenReturn(user);
        when(userRepository.findUser(user)).thenReturn(user);

        final User user1 = userService.unbanUser(user_id);

        assertFalse(user1.isBan());

    }
}