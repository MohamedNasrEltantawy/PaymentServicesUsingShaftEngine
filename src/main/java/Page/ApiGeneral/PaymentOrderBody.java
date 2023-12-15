package Page.ApiGeneral;

import java.util.UUID;

public class PaymentOrderBody {

    //create order
    PaymentOrderSource paymentOrderSource;
    String channelId;
    String divisionId;
    String type;
    double totalAmount;
    String customerReference;
    String customerName;
    String customerPhone;
    String customerEmail;
    String reference;
    String successUrl;
    String failureUrl;

    //refund
    double amount ;


    //Body for Create order
    public PaymentOrderBody(String channel, String division, String typeOfOrder, double TotalAmount,
                            PaymentOrderSource paymentOrderSource) {
        this.channelId = channel;
        this.divisionId = division;
        this.type = typeOfOrder;
        this.totalAmount = TotalAmount;
        this.paymentOrderSource = paymentOrderSource;
        this.customerReference = "1105554752";
        this.customerName = "Tantawi";
        this.customerPhone = "568018870";
        this.customerEmail = "meltantawy1-e@jahez.net";
        this.reference = UUID.randomUUID().toString();
        this.successUrl = "https://jahezco.atlassian.net/wiki/spaces/PAY/pages/544964634/Create+Division";
        this.failureUrl = "https://guidgenerator.com/online-guid-generator.aspx";
    }

    //Body for refund


}
