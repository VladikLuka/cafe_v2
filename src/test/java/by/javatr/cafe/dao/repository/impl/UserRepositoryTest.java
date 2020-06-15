//package by.javatr.cafe.dao.repository.impl;
//
//
//
//import by.javatr.cafe.config.ApplicationConfiguration;
//import by.javatr.cafe.dao.factory.AbstractFactory;
//import by.javatr.cafe.dao.repository.IUserRepository;
//import by.javatr.cafe.entity.Address;
//import by.javatr.cafe.entity.Role;
//import by.javatr.cafe.entity.User;
//import by.javatr.cafe.exception.DAOException;
//import org.junit.Test;
//
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//public class UserRepositoryTest {
//
//    User actual = new User("test", "test", "+375333044914", "TEST1@GMAIL.COM", "test", new Address("test" ,"test" ,"test" ,"test"));
//
//    AbstractFactory factory = AbstractFactory.getFactory(ApplicationConfiguration.INSTANCE.getDb());
//
//    @Test
//    public void getAllUser() {
//
//    }
//
//    @Test
//    public void findUserID() throws DAOException {
//
//        IUserRepository userRepository = factory.getUserRepository();
//        Optional<User> actual = userRepository.findUser(1);
//
//        assertEquals(Optional.of(new User(1,"Vladislav", "Torety", "+375 (29) 666-08-19", "TESTADMIN@MAIL.RU", "e34b27716b2cfe8064f47dcc7c3432fb9aeed66f", 0,0, Role.ADMIN,
//                new Address(1, "Gezgaly", "novaya", "6", "52"))), actual);
//    }
//
//    @Test
//    public void findUserEmailPsw() throws DAOException {
//
//        IUserRepository userRepository = factory.getUserRepository();
//        Optional<User> actual = userRepository.findUser("TESTADMIN@MAIL.RU", "e34b27716b2cfe8064f47dcc7c3432fb9aeed66f");
//
//        assertEquals(Optional.of(new User(1,"Vladislav", "Torety", "+375 (29) 666-08-19", "TESTADMIN@MAIL.RU", "e34b27716b2cfe8064f47dcc7c3432fb9aeed66f", 0,0, Role.ADMIN,
//                new Address(1, "Gezgaly", "novaya", "6", "52"))), actual);
//    }
//
//    @Test
//    public void userNotFound() throws DAOException {
//
//        IUserRepository userRepository = factory.getUserRepository();
//        boolean b = userRepository.findUser("qwe", "qwe").isPresent();
//
//        assertFalse(b);
//
//    }
//
//
//
//    @Test
//    public void createUser() throws DAOException {
//        IUserRepository userRepository = factory.getUserRepository();
//        userRepository.createUser(actual);
//        User expected = userRepository.findUser(actual.getMail(), actual.getPassword()).get();
//
//        assertEquals(expected , actual);
//
//    }
//
//
//    @Test
//    public void deleteUser() throws DAOException {
//
//        MySqlUserRepository userRepository = new MySqlUserRepository();
//
//        User user = userRepository.findUser(actual.getMail(), actual.getPassword()).get();
//
//        boolean i = userRepository.deleteUser(user.getId());
//
//        assertTrue(i);
//
//    }
//
//
//    @Test
//    public void update() {
//
//
//
//    }
//}