package by.javatr.cafe.config;

import by.javatr.cafe.container.annotation.Component;

import java.util.ResourceBundle;

@Component
public class PaymentConfiguration {

    private String merchantId;
    private String publicKey;
    private String privateKey;

    private PaymentConfiguration(){
        initPayment();
    }

    public void initPayment(){

        ResourceBundle bundle = ResourceBundle.getBundle("payment");

        merchantId = bundle.getString("merchantId");
        publicKey = bundle.getString("publicKey");
        privateKey = bundle.getString("privateKey");
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

}
