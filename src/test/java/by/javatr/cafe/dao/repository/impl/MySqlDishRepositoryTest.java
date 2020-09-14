package by.javatr.cafe.dao.repository.impl;

import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DAOException;
import by.javatr.cafe.exception.DIException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MySqlDishRepositoryTest {

    static MySqlDishRepository dishRepository;
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

        dishRepository = new MySqlDishRepository(connection);

        dishRepository.create(new Dish(1, "Pizza", "The best pizza", new BigDecimal(12), true, 250, 1, "/static/img/pizza/photo.png"));
        dishRepository.create(new Dish(2, "Pizza2", "The best pizza", new BigDecimal(35), true, 250, 2, "/static/img/pizza/photo2.png"));

    }

    @AfterAll
    static void tearDown() throws SQLException {

        connection.prepareStatement("truncate dish").execute();

        connection.close();

    }

    @Test
    void getAll() throws DAOException {

        final List<Dish> all = dishRepository.getAll();

        assertEquals(2, all.size());

    }

    @Test
    void getById() throws DAOException {

        final Dish dish = dishRepository.getById(1);

        Dish expectedDish = new Dish(1, "Pizza", "The best pizza", new BigDecimal(12), true, 250, 1, "/static/img/pizza/photo.png");

        assertEquals(expectedDish, dish);

    }

    @Test
    void delete() throws DAOException {

        Dish expectedDish = new Dish(3, "Pizza3", "The best pizza", new BigDecimal(12), true, 250, 1, "/static/img/pizza/photo3.png");

        final Dish dish = dishRepository.create(expectedDish);

        final boolean delete = dishRepository.delete(dish.getId());

        assertTrue(delete);

    }

    @Test
    void create() throws DAOException {

        Dish expectedDish = new Dish(4, "Pizza4", "The best pizza", new BigDecimal(12), true, 250, 1, "/static/img/pizza/photo3.png");

        final Dish dish = dishRepository.create(expectedDish);

        final Dish actualDish = dishRepository.getById(dish.getId());

        assertEquals(expectedDish, actualDish);

        dishRepository.delete(actualDish.getId());
    }

    @Test
    void update() throws DAOException {

        Dish expectedDish = new Dish(5, "Pizza5", "The best pizza", new BigDecimal(12), true, 250, 1, "/static/img/pizza/photo3.png");

        Dish dish = dishRepository.create(expectedDish);

        dish.setName("Pizza6");

        final Dish update = dishRepository.update(dish);

        assertEquals(dish.getName(), update.getName());

        dishRepository.delete(dish.getId());

    }
}