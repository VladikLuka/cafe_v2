package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.User;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IUserService;
import by.javatr.cafe.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ChangePassTest {

    IUserService userService;

    @BeforeEach
    void setUp() throws DIException {

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(absolutePath);

        userService = (UserService) BeanFactory.getInstance().getBean("userService");
        userService = Mockito.mock(IUserService.class);

    }

    @Test
    void execute() throws ServiceException {

        when(userService.find(1)).thenReturn(new User(1));

        RequestContent content = new RequestContent();

        content.addRequestParams(RequestParameters.NEW_PASSWORD, new String[]{"qwerty"});
        content.addSessionAttr(SessionAttributes.USER_ID, 1);

        final ChangePass changePass =(ChangePass) BeanFactory.getInstance().getBean("changePass");

        final RequestResult execute = changePass.execute(content);

        assertEquals(200, execute.getStatus());

    }
}