package by.javatr.cafe.controller.command.impl;

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

@Component
public class DeleteAddress implements Command {
    @Autowired
    IAddressService service;

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        String id = content.getRequestParam("id");
        int address_id =  Integer.parseInt(id);

        Address address = new Address();
        address.setId(address_id);
        address.setUser_id((Integer) content.getSessionAttr(SessionAttributes.USER_ID));

        boolean delete = service.delete(address);

        if (delete){
            return  new RequestResult(Navigation.REDIRECT, Path.USER, HttpServletResponse.SC_OK);
        } else {
            return new RequestResult(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

}
