package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.constant.SessionAttributes;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.exception.ServiceException;
import com.braintreegateway.*;

import javax.servlet.http.HttpServletResponse;

@Component
public class returnToken implements Command {

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        final BraintreeGateway gateway = Gateway.getGateway();

        String id = String.valueOf(content.getSessionAttr(SessionAttributes.USER_ID));

        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        String clientToken = gateway.clientToken().generate(clientTokenRequest);

        return new RequestResult(clientToken, HttpServletResponse.SC_OK);

    }


}
