package Test.Tap;

import Page.ApiGeneral.ApiCollection;
import Page.ApiGeneral.PaymentOrderBody;
import Page.ApiGeneral.PaymentOrderSource;
import Page.ApiGeneral.RefundBody;
import Page.TapDesign.PaymentPageTap;
import com.shaft.db.DatabaseActions;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Tap_Bahrain {

    SHAFT.GUI.WebDriver driver =new SHAFT.GUI.WebDriver();
    SHAFT.DB data = new SHAFT.DB(
            DatabaseActions.DatabaseType.POSTGRES_SQL,
            "dev-database-v2.csm6dywjcf4g.eu-central-1.rds.amazonaws.com",
            "5432",
            "payment_qa",
            "payment_app",
            "payment_app@payment_dev_db123"
    );
    ApiCollection ApiCollectionOBJ = new ApiCollection(driver);
    PaymentPageTap paymentPageTapOBJ = new PaymentPageTap(driver);
    String tenant="3a070412-6c55-04e0-3ee5-e2438d7a5765";
    String Channel="3a0d3483-e9c9-1e00-37b3-c36641d69746";
    String Division="3a0f508d-f1c2-ecdb-dca5-d14bfd7a651f";
    double Total_amount=400.20;
    String CardIdVisa;
    String CardIdMasterCard;
    UUID orderIdVisaRefund;
    UUID orderIdMasterCardPartialRefund;
    UUID orderIdBenefitRefund;
    UUID OrderIdBenefitPartialRefund;


    public SHAFT.API Response;



    @Test(priority = 1)
    public void Check_Create_Order_Tap_Bahrain()
    {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);

        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test (priority = 2)
    public void Visa_Bahrain_Tap() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL( Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fTapPayment_Ifram();
        paymentPageTapOBJ.fadd_Card("4508750015741019");
        paymentPageTapOBJ.fExpiration_date("01/39");
        paymentPageTapOBJ.fCVV("100");
        paymentPageTapOBJ.fButton_Pay();
        paymentPageTapOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(30);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        orderIdVisaRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");

    }
    @Test (priority = 3)
    public void MasterCard_Bahrain_Tap() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL( Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fTapPayment_Ifram();
        paymentPageTapOBJ.fadd_Card("5123450000000008");
        paymentPageTapOBJ.fExpiration_date("01/39");
        paymentPageTapOBJ.fCVV("100");
        paymentPageTapOBJ.fButton_Pay();
        TimeUnit.SECONDS.sleep(10);
        paymentPageTapOBJ.femulator_submit_Ifram3DS();
        TimeUnit.SECONDS.sleep(30);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        orderIdMasterCardPartialRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");

    }
    @Test (priority = 4)
    public void Benefit_Bahrain_Tap() throws InterruptedException {
        //  paymentOrderSource source=new paymentOrderSource("SavedCard","3a0b6000-08e5-d474-870c-7bc458354f67","123");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fBenefit_Option();
        paymentPageTapOBJ.fBenefit_add_Card("4600410123456789");
        paymentPageTapOBJ.fBenefit_Expired_date_month("08");
        paymentPageTapOBJ.fBenefit_Expired_date_year("2025");
        paymentPageTapOBJ.fBenefit_Card_holders_name("sdsdsd");
        paymentPageTapOBJ.fBenefit_pin();
        paymentPageTapOBJ.fBenefit_pay_Button();
        TimeUnit.SECONDS.sleep(30);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        OrderIdBenefitPartialRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
        //  SHAFT.Validations.assertThat().number(statusCode).isEqualTo(200).perform();
    }

    @Test (priority = 5)
    public void Order_Benefit_Bahrain_Tap_ForPartialRefund() throws InterruptedException {
        //  paymentOrderSource source=new paymentOrderSource("SavedCard","3a0b6000-08e5-d474-870c-7bc458354f67","123");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel,Division,"Regular",Total_amount,null),tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fBenefit_Option();
        paymentPageTapOBJ.fBenefit_add_Card("4600410123456789");
        paymentPageTapOBJ.fBenefit_Expired_date_month("08");
        paymentPageTapOBJ.fBenefit_Expired_date_year("2025");
        paymentPageTapOBJ.fBenefit_Card_holders_name("sdsdsd");
        paymentPageTapOBJ.fBenefit_pin();
        paymentPageTapOBJ.fBenefit_pay_Button();
        TimeUnit.SECONDS.sleep(30);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        OrderIdBenefitPartialRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
        //  SHAFT.Validations.assertThat().number(statusCode).isEqualTo(200).perform();
    }


    @Test(priority = 6)
    public void save_card_Visa_Bahrain_Tap() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "SaveCard", Total_amount, null), tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fTapPayment_Ifram();
        paymentPageTapOBJ.fadd_Card("4508750015741019");
        paymentPageTapOBJ.fExpiration_date("01/39");
        paymentPageTapOBJ.fCVV("100");
        paymentPageTapOBJ.fButton_Pay();
        paymentPageTapOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(30);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }
    @Test(priority = 7)
    public void save_card_MasterCard_Bahrain_Tap() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "SaveCard", Total_amount, null), tenant);
        driver.browser().navigateToURL(  Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageTapOBJ.fTapPayment_Ifram();
        paymentPageTapOBJ.fadd_Card("5123450000000008");
        paymentPageTapOBJ.fExpiration_date("01/39");
        paymentPageTapOBJ.fCVV("100");
        paymentPageTapOBJ.fButton_Pay();
        TimeUnit.SECONDS.sleep(10);
        paymentPageTapOBJ.femulator_submit_Ifram3DS();
        TimeUnit.SECONDS.sleep(35);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id")+"'");
        String status= data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }

    @Test(priority = 8 , dependsOnMethods = {"save_card_Visa_Bahrain_Tap","save_card_MasterCard_Bahrain_Tap"})
    public void Get_Cards() {
        Response = ApiCollectionOBJ.Get_Cards(Channel, Division, tenant);

        for (int i = 0; i < Response.getResponseJSONValueAsList("$").size(); i++) {
            if (Response.getResponseJSONValue("$[" + i + "].scheme").equals("Visa")) {
                CardIdVisa = Response.getResponseJSONValue("$[" + i + "].id");
                System.out.println("Tantawi" + CardIdVisa);
            }
            if (Response.getResponseJSONValue("$[" + i + "].scheme").equals("Mastercard")) {
                CardIdMasterCard = Response.getResponseJSONValue("$[" + i + "].id");
                System.out.println("Nasr" + CardIdMasterCard);
            }

        }

        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test(priority = 9 ,dependsOnMethods = {"Get_Cards","save_card_Visa_Bahrain_Tap","save_card_MasterCard_Bahrain_Tap"} )
    public void saved_card_Visa_Bahrain_Tap() throws InterruptedException {
        PaymentOrderSource paymentOrderSource = new PaymentOrderSource("SavedCard", CardIdVisa, "123","192.0.2.146");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, paymentOrderSource), tenant);
        TimeUnit.SECONDS.sleep(30);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }

    @Test(priority = 10 ,dependsOnMethods = {"Get_Cards","save_card_Visa_Bahrain_Tap","save_card_MasterCard_Bahrain_Tap"} )
    public void saved_card_MasterCard_Bahrain_Tap() throws InterruptedException {
        PaymentOrderSource paymentOrderSource = new PaymentOrderSource("SavedCard", CardIdMasterCard, "123","192.0.2.146");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, paymentOrderSource), tenant);
        //driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        //TimeUnit.SECONDS.sleep(10);
        //paymentPageTapOBJ.fTapPayment_Ifram();
        TimeUnit.SECONDS.sleep(30);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }

    @Test(priority = 11,dependsOnMethods = {"Get_Cards"})
    public void Visa_delete_Cards() {
        Response = ApiCollectionOBJ.Delete_Cards(CardIdVisa, tenant);
        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(204).perform();
    }

    @Test(priority = 12,dependsOnMethods = {"Get_Cards"})
    public void Mastercard_delete_Cards() {
        Response = ApiCollectionOBJ.Delete_Cards(CardIdMasterCard, tenant);
        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(204).perform();
    }

    @Test(priority = 13)
    public void Empty_Get_Cards() {
        Response = ApiCollectionOBJ.Get_Cards(Channel, Division, tenant);
        SHAFT.Validations.assertThat().object((Object) Response.getResponseJSONValue("$")).isEqualTo((Object) "[]").perform();
    }



    @Test(priority = 14,dependsOnMethods = {"Visa_Bahrain_Tap"} )
    public void refundTotalAmountForVisa() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        Response = ApiCollectionOBJ.Refund(new RefundBody(Total_amount), orderIdVisaRefund, tenant);
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + orderIdVisaRefund + "'and action_type ='Refunded'");
        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(1).perform();
        //  SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test(priority = 15,dependsOnMethods = {"MasterCard_Bahrain_Tap"} )
    public void ParialrefundForMasterCard() throws InterruptedException {

        double amount = Total_amount / 4;
        TimeUnit.SECONDS.sleep(10);
        for (int i = 0; i < 4; i++) {
            ApiCollectionOBJ.Refund(new RefundBody(amount), orderIdMasterCardPartialRefund, tenant);
            TimeUnit.SECONDS.sleep(30);
        }
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + orderIdMasterCardPartialRefund + "'and action_type ='PartialRefunded'");
        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(4).perform();
    }

    @Test(priority = 16,dependsOnMethods = {"Benefit_Bahrain_Tap"})
    public void refundTotalAmountForBenefit() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        Response = ApiCollectionOBJ.Refund(new RefundBody(Total_amount), orderIdBenefitRefund, tenant);
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + orderIdBenefitRefund + "'and action_type ='Refunded'");
        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(1).perform();
        //  SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test(priority = 17,dependsOnMethods = {"Order_Benefit_Bahrain_Tap_ForPartialRefund"} )
    public void ParialrefundForBenefit() throws InterruptedException {
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
