package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Dish;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.ICartService;
import by.javatr.cafe.service.IDishService;
import by.javatr.cafe.service.impl.CartService;
import by.javatr.cafe.service.impl.DishService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AddToCartTest {

    static IDishService dishService;
    static ICartService cartService;

    @BeforeAll
    static void setUp() throws DIException {

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(absolutePath);

        dishService = (DishService) BeanFactory.getInstance().getBean("dishService");
        cartService = (CartService) BeanFactory.getInstance().getBean("cartService");
        dishService = Mockito.mock(IDishService.class);
        cartService = Mockito.mock(ICartService.class);

    }

    @AfterAll
    static void tearDown() {
    }

    @Test
    void execute() throws ServiceException {

        when(dishService.get(1)).thenReturn(new Dish(1));

        RequestContent content = new RequestContent();

        content.addRequestParams(RequestParameters.ID, new String[]{"1"});

        final AddToCart addToCart =(AddToCart) BeanFactory.getInstance().getBean("addToCart");

        final RequestResult execute = addToCart.execute(content);

        assertEquals(200, execute.getStatus());

    }
}