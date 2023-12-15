package Page.ApiGeneral;
import com.shaft.api.RestActions;
import com.shaft.driver.SHAFT;
import io.restassured.http.ContentType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ApiCollection

{
    private SHAFT.GUI.WebDriver driver;
    public ApiCollection(SHAFT.GUI.WebDriver driver) {
        this.driver= driver;
    }
    //paymentOrderSource paymentOrderSourceOBJ=new paymentOrderSource(null,null,null);



    public SHAFT.API createOrder(PaymentOrderBody body, String tenant)
{
    SHAFT.API api = new SHAFT.API("https://payment-api.qa.svc.jahez.net");
    api.post("/api/app/payment-order")
            .addHeader("Accept-Language", "en").addHeader("__tenant",tenant)
            .setRequestBody(body).setContentType(ContentType.JSON).perform();
   return api;
}

    public SHAFT.API Get_Cards(String channeel,String Devision ,String tenant)

    {
        SHAFT.API api = new SHAFT.API("https://payment-api.qa.svc.jahez.net");
        List<List<Object>> parameters = Arrays.asList(Arrays.asList("ChannelId", ""+channeel+""), Arrays.asList("DivisionId",""+Devision+""));
        api.get("/api/app/customer/1105554752/saved-cards")
             .setParameters(parameters, RestActions.ParametersType.QUERY)
                .addHeader("Accept-Language", "en").addHeader("__tenant",tenant).setContentType(ContentType.JSON).perform();

        return api;
    }


    public  SHAFT.API Delete_Cards(String Cardid,String tenant)

    {
        SHAFT.API api = new SHAFT.API("https://payment-api.qa.svc.jahez.net");
        api.delete("/api/app/customer/1105554752/saved-card/"+Cardid)
                .addHeader("Accept-Language", "en").addHeader("__tenant",tenant).perform();

        return api;
    }


    public  SHAFT.API Refund(RefundBody body , UUID orderId, String tenant)

    {
        SHAFT.API api = new SHAFT.API("https://payment-api.qa.svc.jahez.net");
        api.post("/api/app/payment-order/"+orderId+"/refunds")
                .addHeader("accept","text/plain").addHeader("Content-Type","application/json").addHeader("__tenant",tenant)
                .setRequestBody(body).setContentType(ContentType.JSON).perform();
        return api;
    }


}
