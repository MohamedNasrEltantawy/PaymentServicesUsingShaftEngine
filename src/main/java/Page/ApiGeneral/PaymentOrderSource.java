package Page.ApiGeneral;

public class PaymentOrderSource {
    public  PaymentOrderSource  (String type, String savedCardId, String cvv,String paymentIp )
    {
        this.type=type;
        this.savedCardId=savedCardId;
        this.cvv=cvv;
        this.paymentIp=paymentIp;

    }
    String type ;
    String savedCardId;
    String   cvv;
    String paymentIp;
}
