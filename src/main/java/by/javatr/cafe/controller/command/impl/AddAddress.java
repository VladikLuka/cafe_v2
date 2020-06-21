package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.constant.Path;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.Navigation;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.entity.Address;
import by.javatr.cafe.exception.ServiceException;
import by.javatr.cafe.service.IAddressService;

import javax.servlet.http.HttpServletResponse;

@Component
public class AddAddress implements Command {
    @Autowired
    IAddressService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String city = content.getRequestParam("city");
        String street = content.getRequestParam("street");
        String house = content.getRequestParam("house");
        String flat = content.getRequestParam("flat");
        int user_id = (int) content.getSessionAttr(SessionAttributes.USER_ID);

        Address address = new Address(city, street, house, flat, user_id);

        service.create(address);

        return new RequestResult(Navigation.REDIRECT, Path.USER, HttpServletResponse.SC_OK);
    }

    private AddAddress() {}

}
