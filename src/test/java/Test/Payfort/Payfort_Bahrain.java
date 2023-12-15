package Test.Payfort;
import Page.ApiGeneral.ApiCollection;
import Page.ApiGeneral.PaymentOrderBody;
import Page.ApiGeneral.RefundBody;
import Page.PayfortDesign.PaymentPagePayfort;
import com.shaft.db.DatabaseActions.DatabaseType;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import static com.shaft.driver.SHAFT.*;


public class Payfort_Bahrain

{
    GUI.WebDriver driver =new GUI.WebDriver();

    String tenant="3a070412-6c55-04e0-3ee5-e2438d7a5765";
    String Channel="3a0716a6-55ff-b943-7ee1-510874330cba";
    String Division="3a0716a6-5607-1813-db4f-42ea323c90cf";
    double Total_amount=3005.605;

    UUID orderIdBenefitRefund;
    UUID OrderIdBenefitPartialRefund;
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
    public SHAFT.API Response;



    @Test
   public void Check_Create_Order_Payfort()
   {
       Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);

      SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
   }

   @Test (priority = 1)
   public void Visa_Bahrain_payfort() throws InterruptedException {
     //  paymentOrderSource source=new paymentOrderSource("SavedCard","3a0b6000-08e5-d474-870c-7bc458354f67","123");

       Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
       driver.browser().navigateToURL( Response.getResponseJSONValue("$.redirectUrl"));
       paymentPageOBJ.fClick_On_Payment_Option();
       paymentPageOBJ.fadd_Card("4508750015741019");
       paymentPageOBJ.fExpiration_date("01/39");
       paymentPageOBJ.fCVV("100");
       paymentPageOBJ.fButton_Pay();
       paymentPageOBJ.femulator_submit();
       // paymentPageOBJ.femulator_submit();
       //paymentPageOBJ.fOTP("12345");
       //paymentPageOBJ.fConfiramtion_Button();
       TimeUnit.SECONDS.sleep(10);
     data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
       String status= data.getResult();
       System.out.println("@@@@******************??????????????////////????////"+status);
       System.out.println("@@@@******************??????????????////////????////"+ Response.getResponseJSONValue("$.id"));
       Validations.assertThat().object((String) status).equals((String) "Completed");

   }
    @Test (priority = 2)
    public void MasterCard_Bahrain_payfort() throws InterruptedException {
        //  paymentOrderSource source=new paymentOrderSource("SavedCard","3a0b6000-08e5-d474-870c-7bc458354f67","123");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL( Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("5313581000123430");
        paymentPageOBJ.fExpiration_date("05/25");
       paymentPageOBJ.fCVV("123");
        paymentPageOBJ.fButton_Pay();
       // paymentPageOBJ.femulator_submit_Ifram();
      //  paymentPageOBJ.femulator_submit();
        paymentPageOBJ.fOTP("12345");
        paymentPageOBJ.fConfiramtion_Button();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        System.out.println("@@@@******************??????????????////////????////"+status);
        System.out.println("@@@@******************??????????????////////????////"+ Response.getResponseJSONValue("$.id"));
        Validations.assertThat().object((String) status).equals((String) "Completed");


    }

    @Test (priority = 3)
    public void Benefit_Bahrain_payfort() throws InterruptedException {
        //  paymentOrderSource source=new paymentOrderSource("SavedCard","3a0b6000-08e5-d474-870c-7bc458354f67","123");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fBenefit_Option();
        paymentPageOBJ.fBenefit_add_Card("4600410123456789");
        paymentPageOBJ.fBenefit_Expired_date_month("08");
        paymentPageOBJ.fBenefit_Expired_date_year("2025");
        paymentPageOBJ.fBenefit_Card_holders_name("sdsdsd");
        paymentPageOBJ.fBenefit_pin();
        paymentPageOBJ.fBenefit_pay_Button();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        orderIdBenefitRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        System.out.println("@@@@******************??????????????////////????////"+status);
        System.out.println("@@@@******************??????????????////////????////"+ Response.getResponseJSONValue("$.id"));
        Validations.assertThat().object((String) status).equals((String) "Completed");
        //  SHAFT.Validations.assertThat().number(statusCode).isEqualTo(200).perform();
    }
    @Test (priority = 5)
    public void Order_Benefit_Bahrain_Payfort_ForPartialRefund() throws InterruptedException {
        //  paymentOrderSource source=new paymentOrderSource("SavedCard","3a0b6000-08e5-d474-870c-7bc458354f67","123");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fBenefit_Option();
        paymentPageOBJ.fBenefit_add_Card("4600410123456789");
        paymentPageOBJ.fBenefit_Expired_date_month("08");
        paymentPageOBJ.fBenefit_Expired_date_year("2025");
        paymentPageOBJ.fBenefit_Card_holders_name("sdsdsd");
        paymentPageOBJ.fBenefit_pin();
        paymentPageOBJ.fBenefit_pay_Button();
        TimeUnit.SECONDS.sleep(30);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        OrderIdBenefitPartialRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
        //  SHAFT.Validations.assertThat().number(statusCode).isEqualTo(200).perform();
    }


    @Test(priority = 16,dependsOnMethods = {"Benefit_Bahrain_payfort"})
    public void refundTotalAmountForBenefit_payfort() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        Response = ApiCollectionOBJ.Refund(new RefundBody(Total_amount), orderIdBenefitRefund, tenant);
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + orderIdBenefitRefund + "'and action_type ='Refunded'");
        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(1).perform();
        //  SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test(priority = 17,dependsOnMethods = {"Order_Benefit_Bahrain_Payfort_ForPartialRefund"} )
    public void ParialrefundForBenefit_payfort() throws InterruptedException {
        //  saved_card_Mada_Saudi_Arabia_payfort();

        double amount = Total_amount / 4;
        TimeUnit.SECONDS.sleep(10);
        for (int i = 0; i < 4; i++) {
            ApiCollectionOBJ.Refund(new RefundBody(amount), OrderIdBenefitPartialRefund, tenant);
            TimeUnit.SECONDS.sleep(50);
        }
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + OrderIdBenefitPartialRefund + "'and action_type ='PartialRefunded'");

        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(4).perform();


    }

    @AfterTest
    public void closeBrowser() {
        driver.browser().closeCurrentWindow();
        driver.quit();
    }


}

