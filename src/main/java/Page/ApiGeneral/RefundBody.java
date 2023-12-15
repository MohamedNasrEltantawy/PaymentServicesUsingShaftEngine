package Page.ApiGeneral;

import java.util.UUID;

public class RefundBody {


    String reference;
    double amount;

    public RefundBody(double amount) {
        this.reference = (UUID.randomUUID()).toString().replaceAll("-", "");
        this.amount = amount;
    }
}