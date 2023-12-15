package Test.Payfort;

import Page.ApiGeneral.ApiCollection;
import Page.ApiGeneral.PaymentOrderBody;
import Page.ApiGeneral.PaymentOrderSource;
import Page.ApiGeneral.RefundBody;
import Page.PayfortDesign.PaymentPagePayfort;
import com.shaft.db.DatabaseActions;
import com.shaft.driver.SHAFT;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Payfort_Saudi_Arabia {

    

    public SHAFT.API Response;
    SHAFT.GUI.WebDriver driver = new SHAFT.GUI.WebDriver();
    String tenant = "3a08c777-c12f-2b20-006e-6c3480120f87";
    String Channel = "3a08c77a-e181-7c91-b4a9-d702395cf1e1";
    String Division = "3a08c787-266f-3f29-4242-9acb7a020a12";
    double Total_amount = 3005.60;
    SHAFT.DB data = new SHAFT.DB(
            DatabaseActions.DatabaseType.POSTGRES_SQL,
            "dev-database-v2.csm6dywjcf4g.eu-central-1.rds.amazonaws.com",
            "5432",
            "payment_qa",
            "payment_app",
            "payment_app@payment_dev_db123"

    );
    String CardIdMada;
    String CardIdVisa;
    UUID orderIdVisaRefund;
    UUID orderIdMasterCardPartialRefund;
    UUID orderIdMadaRefund;
    UUID OrderIdMadaPartialRefund;
    ApiCollection ApiCollectionOBJ = new ApiCollection(driver);
    PaymentPagePayfort paymentPageOBJ = new PaymentPagePayfort(driver);

    @Test
    public void Check_Create_Order_Saudi_Arabia_Payfort() {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, null), tenant);
        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test(priority = 1)
    public void Visa_Saudi_Arabia_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, null), tenant);
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("4508750015741019");
        paymentPageOBJ.fExpiration_date("01/39");
        paymentPageOBJ.fCVV("100");
        paymentPageOBJ.fButton_Pay();
        paymentPageOBJ.femulator_submit();
        TimeUnit.SECONDS.sleep(15);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        orderIdVisaRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");


    }

    @Test(priority = 2)
    public void MasterCard_Saudi_Arabia_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, null), tenant);
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("5123450000000008");
        paymentPageOBJ.fExpiration_date("01/39");
        paymentPageOBJ.fCVV("100");
        paymentPageOBJ.fButton_Pay();
        paymentPageOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        orderIdMasterCardPartialRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");

    }

    @Test(priority = 3)
    public void American_Express_Saudi_Arabia_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, null), tenant);
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("3742000000000041");
        paymentPageOBJ.fExpiration_date("05/25");
        paymentPageOBJ.fCVV("1234");
        paymentPageOBJ.fButton_Pay();
        paymentPageOBJ.fOTP("12345");
        paymentPageOBJ.fConfiramtion_Button();
        // paymentPageOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }

    @Test(priority = 4)
    public void Mada_Saudi_Arabia_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, null), tenant);
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("2223000000000007");
        paymentPageOBJ.fExpiration_date("01/39");
        paymentPageOBJ.fCVV("123");
        paymentPageOBJ.fButton_Pay();
        paymentPageOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        orderIdMadaRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }


    @Test(priority = 5)
    public void STC_Saudi_Arabia_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody("3a0707e3-3117-fdcf-b867-3be1518ba01f", "3a0bf054-83c6-2297-212b-5f1a63606660", "Regular", 0.1, null), "3a070412-6c55-04e0-3ee5-e2438d7a5765");
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fStc_Pay();
        paymentPageOBJ.fStc_Phone_Number("0548220713");
        paymentPageOBJ.fStc_Otp_button();
        paymentPageOBJ.fSTC_Otp_number("1234");
        TimeUnit.SECONDS.sleep(1);
        paymentPageOBJ.fStc_Otp_button();
        TimeUnit.SECONDS.sleep(20);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }

    @Test(priority = 6)
    public void save_card_Mada_Saudi_Arabia_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "SaveCard", Total_amount, null), tenant);
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("2223000000000007");
        paymentPageOBJ.fExpiration_date("01/39");
        paymentPageOBJ.fCVV("123");
        paymentPageOBJ.fButton_Pay();
        paymentPageOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(15);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }

    @Test(priority = 7)
    public void save_card_Visa_Saudi_Arabia_payfort() throws InterruptedException {
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "SaveCard", Total_amount, null), tenant);
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.fClick_On_Payment_Option();
        paymentPageOBJ.fadd_Card("4508750015741019");
        paymentPageOBJ.fExpiration_date("01/39");
        paymentPageOBJ.fCVV("100");
        paymentPageOBJ.fButton_Pay();
        paymentPageOBJ.femulator_submit();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }

    @Test(priority = 8 , dependsOnMethods = {"save_card_Mada_Saudi_Arabia_payfort", "save_card_Visa_Saudi_Arabia_payfort"})
    public void Get_Cards() {
        Response = ApiCollectionOBJ.Get_Cards(Channel, Division, tenant);

        for (int i = 0; i < Response.getResponseJSONValueAsList("$").size(); i++) {
            if (Response.getResponseJSONValue("$[" + i + "].scheme").equals("Mada")) {
                CardIdMada = Response.getResponseJSONValue("$[" + i + "].id");
                System.out.println("CardIdMada" + CardIdMada);
            }
            if (Response.getResponseJSONValue("$[" + i + "].scheme").equals("Visa")) {
                CardIdVisa = Response.getResponseJSONValue("$[" + i + "].id");
                System.out.println("CardIdVisa" + CardIdVisa);
            }

        }

        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test(priority = 9 ,dependsOnMethods = {"Get_Cards","save_card_Mada_Saudi_Arabia_payfort","save_card_Visa_Saudi_Arabia_payfort"} )
    public void saved_card_Mada_Saudi_Arabia_payfort() throws InterruptedException {
        PaymentOrderSource paymentOrderSource = new PaymentOrderSource("SavedCard", CardIdMada, "123","192.0.2.146");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, paymentOrderSource), tenant);
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        paymentPageOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        OrderIdMadaPartialRefund = UUID.fromString(Response.getResponseJSONValue("$.id"));
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }

    @Test(priority = 10 ,dependsOnMethods = {"Get_Cards","save_card_Mada_Saudi_Arabia_payfort","save_card_Visa_Saudi_Arabia_payfort"} )
    public void saved_card_Visa_Saudi_Arabia_payfort() throws InterruptedException {
        PaymentOrderSource paymentOrderSource = new PaymentOrderSource("SavedCard", CardIdVisa, "123","192.0.2.146");
        Response = ApiCollectionOBJ.createOrder(new PaymentOrderBody(Channel, Division, "Regular", Total_amount, paymentOrderSource), tenant);
        driver.browser().navigateToURL(Response.getResponseJSONValue("$.redirectUrl"));
        // paymentPageOBJ.femulator_submit_Ifram();
        TimeUnit.SECONDS.sleep(10);
        data.executeSelectQuery("SELECT status FROM payment_orders  WHERE  id = '" + Response.getResponseJSONValue("$.id") + "'");
        String status = data.getResult();
        SHAFT.Validations.assertThat().object((String) status).equals((String) "Completed");
    }


    @Test(priority = 11,dependsOnMethods = {"Get_Cards"})
    public void Mada_delete_Cards() {
        Response = ApiCollectionOBJ.Delete_Cards(CardIdMada, tenant);
        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(204).perform();
    }

    @Test(priority = 12,dependsOnMethods = {"Get_Cards"})
    public void Visa_delete_Cards() {
        Response = ApiCollectionOBJ.Delete_Cards(CardIdVisa, tenant);
        SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(204).perform();
    }

    @Test(priority = 13)
    public void Empty_Get_Cards() {
        Response = ApiCollectionOBJ.Get_Cards(Channel, Division, tenant);
        SHAFT.Validations.assertThat().object((Object) Response.getResponseJSONValue("$")).isEqualTo((Object) "[]").perform();
    }

    @Test(priority = 14,dependsOnMethods = {"Visa_Saudi_Arabia_payfort"} )
    public void refundTotalAmountForVisa() {
        Response = ApiCollectionOBJ.Refund(new RefundBody(Total_amount), orderIdVisaRefund, tenant);
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + orderIdVisaRefund + "'and action_type ='Refunded'");
        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(1).perform();
      //  SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test(priority = 15,dependsOnMethods = {"MasterCard_Saudi_Arabia_payfort"} )
    public void ParialrefundForMasterCard() {
        double amount = Total_amount / 4;
        for (int i = 0; i < 4; i++) {
            ApiCollectionOBJ.Refund(new RefundBody(amount), orderIdMasterCardPartialRefund, tenant);
        }
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + orderIdMasterCardPartialRefund + "'and action_type ='PartialRefunded'");
        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(4).perform();
    }

    @Test(priority = 16,dependsOnMethods = {"Mada_Saudi_Arabia_payfort"})
    public void refundTotalAmountForMada() {
        Response = ApiCollectionOBJ.Refund(new RefundBody(Total_amount), orderIdMadaRefund, tenant);
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + orderIdMadaRefund + "'and action_type ='Refunded'");
        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(1).perform();
      //  SHAFT.Validations.assertThat().number(Response.getResponseStatusCode()).isEqualTo(200).perform();
    }

    @Test(priority = 17,dependsOnMethods = {"saved_card_Mada_Saudi_Arabia_payfort","Get_Cards","save_card_Mada_Saudi_Arabia_payfort","save_card_Visa_Saudi_Arabia_payfort"} )
    public void ParialrefundForMada() throws InterruptedException {
     //  saved_card_Mada_Saudi_Arabia_payfort();

        double amount = Total_amount / 4;
        for (int i = 0; i < 4; i++) {
            ApiCollectionOBJ.Refund(new RefundBody(amount), OrderIdMadaPartialRefund, tenant);
        }
        data.executeSelectQuery("select * from payment_transactions pt where payment_order_id = '" + OrderIdMadaPartialRefund + "'and action_type ='PartialRefunded'");

        SHAFT.Validations.assertThat().number(data.getRowCount()).isEqualTo(4).perform();

    }


    @AfterTest
    public void closeBrowser() {
        driver.browser().closeCurrentWindow();
        driver.quit();
    }


}


