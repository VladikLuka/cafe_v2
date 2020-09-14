package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.RequestParameters;
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


/**
 * Class for processing user request.
 * Add address to user
 */
@Component
public class AddAddress implements Command {
    @Autowired
    private IAddressService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String city = content.getRequestParam(RequestParameters.ADDRESS_CITY);
        String street = content.getRequestParam(RequestParameters.ADDRESS_STREET);
        String house = content.getRequestParam(RequestParameters.ADDRESS_HOUSE);
        String flat = content.getRequestParam(RequestParameters.ADDRESS_FLAT);
        int userId = (int) content.getSessionAttr(SessionAttributes.USER_ID);

        Address address = new Address(city, street, house, flat, userId, true);

        address = service.create(address);

        if (address == null) {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

        return new RequestResult(Navigation.REDIRECT, Path.URL_USER, HttpServletResponse.SC_OK);
    }

    public void setService(IAddressService service) {
        this.service = service;
    }

    private AddAddress() {}

}
