package by.javatr.cafe.dao.factory;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.dao.repository.impl.MySqlAddressRepository;
import by.javatr.cafe.dao.repository.impl.MySqlDishRepository;
import by.javatr.cafe.dao.repository.impl.MySqlOrderRepository;
import by.javatr.cafe.dao.repository.impl.MySqlUserRepository;

public class MySqlFactory extends AbstractFactory{

    BeanFactory factory = BeanFactory.getInstance();

    @Override
    public IAddressRepository getAddressRepository() {
        return (MySqlAddressRepository)factory.getBean("mySqlAddressRepository");
    }

    @Override
    public IUserRepository getUserRepository() {
        return (MySqlUserRepository) factory.getBean("mySqlUserRepository");
    }

    @Override
    public IDishRepository getDishRepository() {
        return (MySqlDishRepository) factory.getBean("mySqlDishRepository");
    }

    @Override
    public IOrderRepository getOrderRepository() {
        return (MySqlOrderRepository) factory.getBean("mySqlOrderRepository");
    }
}
