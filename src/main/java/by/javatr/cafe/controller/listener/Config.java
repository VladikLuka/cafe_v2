package by.javatr.cafe.controller.listener;

import by.javatr.cafe.config.ApplicationConfiguration;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.container.annotation.Component;
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
import by.javatr.cafe.util.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *  Service to fill the cache with information
 */
@WebListener
@Component
public class Config implements ServletContextListener {

    private final Logger logger = LogManager.getLogger(getClass());

    private final ApplicationConfiguration configuration = ApplicationConfiguration.INSTANCE;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            URL resource = sce.getServletContext().getResource(getResource(configuration.getDiPackage()));
            initBeanFactory(resource.getPath());

            BeanFactory factory = BeanFactory.getInstance();
            IDishRepository dishRepository = (IDishRepository) factory.getBean("mySqlDishRepository");
            IAddressRepository addressRepository = (IAddressRepository) factory.getBean("mySqlAddressRepository");
            IOrderRepository orderRepository = (IOrderRepository) factory.getBean("mySqlOrderRepository");
            IUserRepository userRepository = (IUserRepository) factory.getBean("mySqlUserRepository");
            Cache cache = (Cache) factory.getBean("cache");

            final List<Address> all = addressRepository.getAll();
            cache.setAddresses(all);

            final List<Dish> all1 = dishRepository.getAll();
            cache.setDishes(all1);

            final List<Order> all2 = orderRepository.getAll();
            cache.setOrders(all2);

            final List<User> allUser = userRepository.getAllUser();
            cache.setUsers(allUser);

            cache.injectAddressToUser();

            sce.getServletContext().setAttribute("cache", cache);
        }catch (DIException | MalformedURLException | DAOException e) {
            logger.error(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * format path to bean container
     * @param name base package
     * @return format path
     */
    private String getResource(String name) {
        name = name.replaceAll("\\.", "/");
        return "WEB-INF/classes/" +  name;
    }


    /**
     * init container
     * @param path path to directory
     * @throws DIException something went wrong
     */
    private void initBeanFactory(String path) throws DIException {
        BeanFactory beanFactory = BeanFactory.getInstance();
        beanFactory.instantiate(path);
        beanFactory.populateProperties();
    }
}
