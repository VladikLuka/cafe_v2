package by.javatr.cafe.service.impl;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.dao.repository.IDishRepository;
import by.javatr.cafe.dao.repository.impl.MySqlDishRepository;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.exception.ServiceException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DishServiceTest {


    static DishService dishService;
    static IDishRepository dishRepository;

    @BeforeAll
    static void setUp() throws DIException {



        if(dishService != null){
            return;
        }

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(" " + absolutePath);

        dishService = (DishService) BeanFactory.getInstance().getBean("dishService");
        IDishRepository mock = Mockito.mock(MySqlDishRepository.class);
        dishRepository = mock;
    }


    @Test
    void get_Should_Return_NotNull_Dish() throws ServiceException, DAOException {

        int dish_id = 1;

        when(dishRepository.getById(dish_id)).thenReturn(new Dish(1));

        Dish dish = dishService.get(dish_id);

        assertEquals(new Dish(1), dish);


    }

    @Test
    void get_Should_Throw_Exception() throws DAOException{

        int dish_id = 1;

        when(dishRepository.getById(dish_id)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            dishService.get(dish_id);
        });

    }


    @Test
    void update_Should_Return_NotNull_Dish() throws DAOException, ServiceException {

        Dish dish = new Dish(1);

        when(dishRepository.update(dish)).thenReturn(dish);

        Dish dish1 = dishService.update(dish);

        assertEquals(dish, dish1);

    }

    @Test
    void update_Should_Throw_Exception() throws DAOException{

        Dish dish = new Dish(1);

        when(dishRepository.update(dish)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            dishService.update(dish);
        });

    }


    @Test
    void create_Should_Return_Dish() throws DAOException, ServiceException {

        Dish dish = new Dish(1);

        when(dishRepository.create(dish)).thenReturn(dish);

        Dish dish1 = dishService.create(dish);

        assertEquals(dish, dish1);

    }

    @Test
    void create_Should_Throw_Exception() throws DAOException{

        Dish dish = new Dish(1);

        when(dishRepository.create(dish)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            dishService.create(dish);
        });

    }

    @Test
    void delete_Should_Return_True() throws DAOException, ServiceException {

        Dish dish = new Dish(1);

        when(dishRepository.delete(dish.getId())).thenReturn(true);

        boolean t = dishService.delete(dish);

        assertTrue(t);

    }

    @Test
    void delete_Should_Throw_Exception() throws DAOException{

        Dish dish = new Dish(1);

        when(dishRepository.delete(dish.getId())).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            dishService.delete(dish);
        });

    }

    @Test
    void getDishes_Should_Return_ArrayList_With_Dishes() throws DAOException, ServiceException {

        List<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish(1));
        dishes.add(new Dish(2));
        dishes.add(new Dish(3));

        when(dishRepository.getAll()).thenReturn(dishes);

        final List<Dish> dishes1 = dishService.getDishes();

        final ArrayList<Dish> dishes_expected = new ArrayList<>();
        dishes_expected.add(new Dish(1));
        dishes_expected.add(new Dish(2));
        dishes_expected.add(new Dish(3));

        assertEquals(dishes_expected, dishes1);

    }


    @Test
    void getAll_Should_Throw_Exception() throws DAOException{

        when(dishRepository.getAll()).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            dishService.getDishes();
        });

    }

    @Test
    void hideDish_Should_Return_Dish_With_Available_False() throws DAOException, ServiceException {

        Dish dish = new Dish(1);

        when(dishRepository.getById(1)).thenReturn(dish);
        when(dishRepository.update(dish)).thenReturn(dish);

        boolean t = dishService.hideDish(dish);

        assertTrue(t);
        assertFalse(dish.isAvailable());


    }

    @Test
    void hideDish_Should_Throw_Exception() throws DAOException {

        Dish dish = new Dish(1);

        when(dishRepository.getById(1)).thenReturn(dish);
        when(dishRepository.update(dish)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            dishService.hideDish(dish);
        });

    }

    @Test
    void showDish_Should_Return_Dish_With_Available_True() throws DAOException, ServiceException {

        Dish dish = new Dish(1);

        when(dishRepository.getById(1)).thenReturn(dish);
        when(dishRepository.update(dish)).thenReturn(dish);

        boolean t = dishService.showDish(dish);

        assertTrue(t);
        assertTrue(dish.isAvailable());

    }

    @Test
    void showDish_Should_Throw_Exception() throws DAOException{

        Dish dish = new Dish(1);

        when(dishRepository.getById(1)).thenReturn(dish);
        when(dishRepository.update(dish)).thenThrow(DAOException.class);

        assertThrows(ServiceException.class, ()->{
            dishService.showDish(dish);
        });

    }

}