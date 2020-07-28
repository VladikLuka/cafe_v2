package by.javatr.cafe.controller.command.impl;

import by.javatr.cafe.constant.RequestParameters;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.container.annotation.Autowired;
import by.javatr.cafe.container.annotation.Component;
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
 * Delete user address
 */
@Component
public class DeleteAddress implements Command {
    @Autowired
    private IAddressService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String id = content.getRequestParam(RequestParameters.ID);
        int addressId =  Integer.parseInt(id);

        Address address = new Address();
        address.setId(addressId);
        address.setUserId((Integer) content.getSessionAttr(SessionAttributes.USER_ID));

        address = service.find(address);

        address.setAvailable(false);

        address = service.update(address);

        if (address != null){
            return  new RequestResult(Navigation.REDIRECT, Path.URL_USER, HttpServletResponse.SC_OK);
        } else {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private DeleteAddress() {
    }
}
