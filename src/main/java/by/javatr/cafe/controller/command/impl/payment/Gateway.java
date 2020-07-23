package by.javatr.cafe.controller.command.impl.payment;

import by.javatr.cafe.config.PaymentConfiguration;
import by.javatr.cafe.container.BeanFactory;
import by.javatr.cafe.container.annotation.Component;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

/**
 * Returns braintree gateway
 */
@Component
public class Gateway {

    private static final PaymentConfiguration configuration = (PaymentConfiguration) BeanFactory.getInstance().getBean("paymentConfiguration");

    public static BraintreeGateway getGateway(){
        return new BraintreeGateway(
                Environment.SANDBOX, configuration.getMerchantId(),
                configuration.getPublicKey(),
                configuration.getPrivateKey()
        );
    }

    private Gateway() {}
}

