package Test.Tap;

import Page.ApiGeneral.ApiCollection;
import Page.ApiGeneral.PaymentOrderBody;
import Page.TapDesign.PaymentPageTap;
import com.shaft.db.DatabaseActions.DatabaseType;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.shaft.driver.SHAFT.*;


public class Tap_kuwait

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
    PaymentPageTap paymentPageTapOBJ = new PaymentPageTap(driver);
    String tenant="3a070412-6c55-04e0-3ee5-e2438d7a5765";
    String Channel="3a07f983-3549-c71e-949c-6b3b0c8c826c";
    String Division="3a0cb92b-4b17-948f-8917-9ea06a31c448";
    double Total_amount=3005.60;


    public SHAFT.API Response;



    @Test(priority = 1)
    public void Check_Create_Order_Tap_kuwait()
    {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);

        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test (priority = 2)
    public void Visa_kuwait_Tap() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL( Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fTapPayment_Ifram();
        paymentPageTapOBJ.fadd_Card("4508750015741019");
        paymentPageTapOBJ.fExpiration_date("01/39");
        paymentPageTapOBJ.fCVV("100");
        paymentPageTapOBJ.fButton_Pay();
        paymentPageTapOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(25);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        Validations.assertThat().object((String) status).equals((String) "Completed");

    }
    @Test (priority = 3)
    public void MasterCard_kuwait_Tap() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL( Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fTapPayment_Ifram();
        paymentPageTapOBJ.fadd_Card("5123450000000008");
        paymentPageTapOBJ.fExpiration_date("01/39");
        paymentPageTapOBJ.fCVV("100");
        paymentPageTapOBJ.fButton_Pay();
        TimeUnit.SECONDS.sleep(10);
        paymentPageTapOBJ.femulator_submit_Ifram3DS();
        TimeUnit.SECONDS.sleep(25);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        Validations.assertThat().object((String) status).equals((String) "Completed");

    }

    @Test (priority = 4)
    public void Knet_kuwait_Tap() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fKNET_Option();
        paymentPageTapOBJ.fSelect_Your_Bank("Knet Test Card [KNET1]");

        paymentPageTapOBJ.fKent_add_Card("000 000 000 1");
        paymentPageTapOBJ.fKent_Expired_date_month("09");
        paymentPageTapOBJ.fKent_Expired_date_year("2025");
        paymentPageTapOBJ.fKent_pin("1234");
        paymentPageTapOBJ.fKent_Submit_Button();
        paymentPageTapOBJ.fKent_confirmation_Button();

        TimeUnit.SECONDS.sleep(25);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        Validations.assertThat().object((String) status).equals((String) "Completed");
        //  SHAFT.Validations.assertThat().number(statusCode).isEqualTo(200).perform();
    }
    @Test (priority = 5)
    public void Knet_kuwait_TapـNOTـCAPTURED() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fKNET_Option();
        paymentPageTapOBJ.fSelect_Your_Bank("Knet Test Card [KNET1]");

        paymentPageTapOBJ.fKent_add_Card("000 000 000 1");
        paymentPageTapOBJ.fKent_Expired_date_month("09");
        paymentPageTapOBJ.fKent_Expired_date_year("2025");
        paymentPageTapOBJ.fKent_pin("1234");
        paymentPageTapOBJ.fKent_Submit_Button();
        paymentPageTapOBJ.fKent_confirmation_Button();

        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        Validations.assertThat().object((String) status).equals((String) "Completed");
        //  SHAFT.Validations.assertThat().number(statusCode).isEqualTo(200).perform();
    }


    @AfterTest
    public void closeBrowser() {
        driver.browser().closeCurrentWindow();
        driver.quit();
    }


}

