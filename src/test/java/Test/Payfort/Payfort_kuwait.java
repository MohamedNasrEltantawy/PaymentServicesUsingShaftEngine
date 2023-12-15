package Test.Payfort;
import Page.ApiGeneral.ApiCollection;
import Page.ApiGeneral.PaymentOrderBody;
import Page.PayfortDesign.PaymentPagePayfort;
import Page.ApiGeneral.RefundBody;
import com.shaft.db.DatabaseActions.DatabaseType;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import static com.shaft.driver.SHAFT.*;

public class Payfort_kuwait
{
    GUI.WebDriver driver =new GUI.WebDriver();
    SHAFT.DB data = new DB(
            DatabaseType.POSTGRES_SQL,
            "dev-database-v2.csm6dywjcf4g.eu-central-1.rds.amazonaws.com",
            "5432",
            "payment_qa",
            "payment_app",
            "payment_app@payment_dev_db123"
    );
    ApiCollection ApiCollectionOBJ = new ApiCollection(driver);
    PaymentPagePayfort paymentPageOBJ= new PaymentPagePayfort(driver);

    String Channel="3a07f983-3549-c71e-949c-6b3b0c8c826c";
    String Division="3a07f983-35d8-db6e-37a7-cc2bf34c5e65";
    double Total_amount=3005.60;
    String tenant="3a070412-6c55-04e0-3ee5-e2438d7a5765";
    UUID OrderIdKentPartialRefund;

    public SHAFT.API Response;



    @Test(priority = 1)
    public void Check_Create_Order_Payfort_kuwait()
    {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);

        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test (priority = 2)
    public void Visa_kuwait_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL( Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("4508750015741019");
        paymentPageOBJ.fExpiration_date("01/39");
        paymentPageOBJ.fCVV("100");
        paymentPageOBJ.fButton_Pay();
        paymentPageOBJ.femulator_submit();
        //paymentPageOBJ.fOTP("12345");
        //paymentPageOBJ.fConfiramtion_Button();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        System.out.println("@@@@******************??????????????////////????////"+status);
        System.out.println("@@@@******************??????????????////////????////"+ Response.getResponseJSONValue("$.id"));
        Validations.assertThat().object((String) status).equals((String) "Completed");

    }
    @Test (priority = 3)
    public void MasterCard_kuwait_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL( Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("5313581000123430");
        paymentPageOBJ.fExpiration_date("05/25");
        paymentPageOBJ.fCVV("123");
        paymentPageOBJ.fButton_Pay();
        paymentPageOBJ.fOTP("12345");
        paymentPageOBJ.fConfiramtion_Button();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        Validations.assertThat().object((String) status).equals((String) "Completed");

    }
       @Test (priority = 4)
       public void American_Express_kuwait_payfort() throws InterruptedException {
           //  paymentOrderSource source=new paymentOrderSource("SavedCard","3a0b6000-08e5-d474-870c-7bc458354f67","123");
           Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
           driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
           paymentPageOBJ.fClick_On_Payment_Option();
           paymentPageOBJ.fadd_Card("345678901234564");
           paymentPageOBJ.fExpiration_date("07/27");
           paymentPageOBJ.fCVV("1234");
           paymentPageOBJ.fButton_Pay();
       //    paymentPageOBJ.fOTP("12345");
       //    paymentPageOBJ.fConfiramtion_Button();
           TimeUnit.SECONDS.sleep(10);
           data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
           String status= data.getResult();
           Validations.assertThat().object((String) status).equals((String) "Completed");
           //  SHAFT.Validations.assertThat().number(statusCode).isEqualTo(200).perform();
       }
    @Test (priority = 5)
    public void Knet_kuwait_payfort() throws InterruptedException {
        //  paymentOrderSource source=new paymentOrderSource("SavedCard","3a0b6000-08e5-d474-870c-7bc458354f67","123");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fKNET_Option();
        paymentPageOBJ.fSelect_Your_Bank("Knet Test Card [KNET1]");

        paymentPageOBJ.fKent_add_Card("000 000 000 1");
        paymentPageOBJ.fKent_Expired_date_month("09");
        paymentPageOBJ.fKent_Expired_date_year("2025");
        paymentPageOBJ.fKent_pin("1234");
        paymentPageOBJ.fKent_Submit_Button();
        paymentPageOBJ.fKent_confirmation_Button();

        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        OrderIdKentPartialRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));

        Validations.assertThat().object((String) status).equals((String) "Completed");
        //  SHAFT.Validations.assertThat().number(statusCode).isEqualTo(200).perform();
    }

    @Test(priority = 6 ,dependsOnMethods = "Knet_kuwait_payfort" )
    public void ParialrefundForKent() throws InterruptedException {
        double amount = Total_amount / 4;
        for (int i = 0; i < 4; i++) {
            ApiCollectionOBJ.Refund(new RefundBody(amount), OrderIdKentPartialRefund, tenant);
        }
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + OrderIdKentPartialRefund + "'and action_type ='PartialRefunded'");
        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(4).perform();

    }

    @AfterTest
    public void closeBrowser() {
        driver.browser().closeCurrentWindow();
        driver.quit();
    }


}


