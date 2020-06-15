package by.javatr.cafe.listener;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.config.ApplicationConfiguration;
import by.javatr.cafe.dao.Cache;
import by.javatr.cafe.dao.factory.AbstractFactory;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.exception.DAOException;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@WebListener
public class Config implements ServletContextListener {
    ApplicationConfiguration configuration = ApplicationConfiguration.INSTANCE;

    IDishRepository dishRepository;
    IAddressRepository addressRepository;
    IOrderRepository orderRepository;
    Cache cache;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            URL resource = sce.getServletContext().getResource(getResource(configuration.getDiPackage()));
            initBeanFactory(resource.getPath());
            BeanFactory factory = BeanFactory.getInstance();
            dishRepository = (IDishRepository) factory.getBean("mySqlDishRepository");
            addressRepository = (IAddressRepository) factory.getBean("mySqlAddressRepository");
            orderRepository =(IOrderRepository) factory.getBean("mySqlOrderRepository");
            cache =(Cache) factory.getBean("cache");
            final List<Address> all = addressRepository.getAll();
            cache.setAddresses(all);
            final List<Dish> all1 = dishRepository.getAll();
            cache.setDishes(all1);
            final List<Order> all2 = orderRepository.getAll();
            cache.setOrders(all2);
        } catch (DAOException | NoSuchMethodException | IOException | InstantiationException | URISyntaxException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        sce.getServletContext().setAttribute("cache",cache);


    }

    private void initBeanFactory(String path) throws NoSuchMethodException, IOException, InstantiationException, URISyntaxException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        BeanFactory beanFactory = BeanFactory.getInstance();
        beanFactory.instantiate(path);
        beanFactory.populateProperties();
    }

    private String getResource(String name) throws MalformedURLException {

        name = name.replaceAll("\\.", "/");

        return "WEB-INF/classes/" +  name;

    }

}
