package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.DIException;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IAddressService;
import by.javatr.cafe.service.impl.AddressService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AddAddressTest {

    static IAddressService addressService;

    @BeforeAll
    static void setUp() throws DIException {

        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.replaceAll("\\\\", "/");
        absolutePath = absolutePath + "/target/test/WEB-INF/classes/by/javatr/cafe/";
        BeanFactory.getInstance().run(absolutePath);

        addressService = (AddressService) BeanFactory.getInstance().getBean("addressService");
        addressService = Mockito.mock(IAddressService.class);

    }

    @AfterAll
    static void tearDown() {
    }

    @Test
    void execute_with_status_code_200() throws ServiceException {

        when(addressService.create(new Address("Minsk", "Ratomskaya", "52", "15",1,true))).thenReturn(new Address(1));

        RequestContent content = new RequestContent();

        content.addRequestParams(RequestParameters.ADDRESS_CITY, new String[]{"Minsk"});
        content.addRequestParams(RequestParameters.ADDRESS_STREET, new String[]{"Ratomskaya"});
        content.addRequestParams(RequestParameters.ADDRESS_HOUSE, new String[]{"52"});
        content.addRequestParams(RequestParameters.ADDRESS_FLAT, new String[]{"15"});
        content.addSessionAttr(SessionAttributes.USER_ID, 1);

        AddAddress addAddress = (AddAddress) BeanFactory.getInstance().getBean("addAddress");

        addAddress.setService(addressService);

        final RequestResult execute = addAddress.execute(content);

        assertEquals(200, execute.getStatus());

    }

    @Test
    void execute_with_status_code_400() throws ServiceException {

        when(addressService.create(new Address("Minsk", "Ratomskaya", "52", "15",1,true))).thenReturn(null);

        RequestContent content = new RequestContent();

        content.addRequestParams(RequestParameters.ADDRESS_CITY, new String[]{"Minsk"});
        content.addRequestParams(RequestParameters.ADDRESS_STREET, new String[]{"Ratomskaya"});
        content.addRequestParams(RequestParameters.ADDRESS_HOUSE, new String[]{"52"});
        content.addRequestParams(RequestParameters.ADDRESS_FLAT, new String[]{"15"});
        content.addSessionAttr(SessionAttributes.USER_ID, 1);

        AddAddress addAddress = (AddAddress) BeanFactory.getInstance().getBean("addAddress");

        addAddress.setService(addressService);

        final RequestResult execute = addAddress.execute(content);

        assertEquals(400, execute.getStatus());

    }
}