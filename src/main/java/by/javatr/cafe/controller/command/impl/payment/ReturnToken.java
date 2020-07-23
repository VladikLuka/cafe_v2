package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.container.annotation.Component;
import by.javatr.cafe.controller.command.Command;
import by.javatr.cafe.controller.content.RequestContent;
import by.javatr.cafe.controller.content.RequestResult;
import by.javatr.cafe.exception.ServiceException;
import com.braintreegateway.ClientTokenRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Generate client token and return to user
 */
@Component
public class ReturnToken implements Command {

    @Override
    public RequestResult execute(RequestContent content) throws ServiceException {

        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        String clientToken = Gateway.getGateway().clientToken().generate(clientTokenRequest);

        return new RequestResult(clientToken, HttpServletResponse.SC_OK);

    }


}
