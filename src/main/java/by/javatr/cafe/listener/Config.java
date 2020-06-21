package by.javatr.cafe.listener;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.config.ApplicationConfiguration;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.util.Cache;
import by.javatr.cafe.dao.repository.IAddressRepository;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.dao.repository.IOrderRepository;
import by.javatr.cafe.dao.repository.IUserRepository;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.entity.Order;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.service.IUserService;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@WebListener
@Component
public class Config implements ServletContextListener {
    ApplicationConfiguration configuration = ApplicationConfiguration.INSTANCE;

    IDishRepository dishRepository;
    IAddressRepository addressRepository;
    IOrderRepository orderRepository;
    IUserRepository userRepository;
    IUserService userService;
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
            userRepository =(IUserRepository) factory.getBean("mySqlUserRepository");
            userService =(IUserService) factory.getBean("userService");
            cache =(Cache) factory.getBean("cache");
            final List<Address> all = addressRepository.getAll();
            cache.setAddresses(all);
            final List<Dish> all1 = dishRepository.getAll();
            cache.setDishes(all1);
            final List<Order> all2 = orderRepository.getAll();
            System.out.println(all2);
            cache.setOrders(all2);
            final List<User> allUser = userRepository.getAllUser();
            cache.setUsers(allUser);
            cache.injectAddressToUser();


            sce.getServletContext().setAttribute("cache", cache);

        }catch (DIException | MalformedURLException | DAOException e) {
            e.printStackTrace();
        }

        sce.getServletContext().setAttribute("cache",cache);


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

        
    private String getResource(String name) throws MalformedURLException {

        name = name.replaceAll("\\.", "/");

        return "WEB-INF/classes/" +  name;

    }


    private void initBeanFactory(String path) throws DIException {
        BeanFactory beanFactory = BeanFactory.getInstance();
        beanFactory.instantiate(path);
        beanFactory.populateProperties();
    }
}
