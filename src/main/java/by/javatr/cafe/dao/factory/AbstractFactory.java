package by.javatr.cafe.dao.factory;

import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.dao.repository.IUserRepository;

public abstract class AbstractFactory {

    public static AbstractFactory getFactory(String name){

        switch(name){
            case "mysql": return new MySqlFactory();
        }

        return null;
    }

    public abstract IAddressRepository getAddressRepository();
    public abstract IUserRepository getUserRepository();
    public abstract IDishRepository getDishRepository();
    public abstract IOrderRepository getOrderRepository();

}
