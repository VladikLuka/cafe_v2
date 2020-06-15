package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.container.annotation.Component;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

@Component
public class Gateway {

    public static BraintreeGateway getGateway(){
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                "qv476w2p2gpc5nyg",
                "wh4g3sdmsrhkxrj8",
                "6d5e8a5fc8d7450473b4d21c93695499"
        );
        return gateway;
    }

}
