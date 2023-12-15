package Page.PayfortDesign;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

public class PaymentPagePayfort {
    //General for payment method
    By Click_On_Payment_Option = By.xpath("//img[@src='/images/card-icons/visa.svg']");
    By add_Card = By.id("cardnumber");
    By Expiration_date = By.id("expirationdate");
    By CVV = By.id("securitycode");
    By Button_Pay = By.id("pay-button");
    By OTP = By.id("Secure3dsForm_password");
    By Confiramtion_Button = By.id("submit-simulator");
    By emulator_submit = By.xpath("//input[@type='submit']");
    By emulator_submit_Ifram = By.id("acssubmit");


    //STC_Pay
    By Stc_Pay = By.xpath("//img[@src='/images/stcpay.svg']");
    By Stc_Phone_Number = By.id("cardNoInput");
    By Stc_Otp_button = By.id("submitBtn");
    By STC_Otp_number = By.id("otpInput");


    //Benefit
    By Benefit = By.xpath("//img[@src='/images/benefit.png']");
    By Benefit_add_Card = By.id("debitCardNumber");
    By Benefit_Expired_date_month = By.id("debitMonthSelect");
    By Benefit_Expired_date_year = By.id("debitYearSelect");
    By Benefit_Card_holders_name = By.id("debitCardholderName");
    By Benefit_pin = By.xpath("//input[@id='cardPin']");
    By Benefit_pin_keyboard1 = By.xpath("//input[@id='cardPinbtn1']");
    By Benefit_pin_keyboard2 = By.xpath("//input[@id='cardPinbtn2']");
    By Benefit_pin_keyboard3 = By.xpath("//input[@id='cardPinbtn3']");
    By Benefit_pin_keyboard4 = By.xpath("//input[@id='cardPinbtn4']");
    By Benefit_pay_Button = By.id("proceed");


    //KNET
    By KNET = By.xpath("//img[@src='/images/KNET.svg']");
    By Select_Your_Bank = By.id("bankBrand");
    By Kent_add_Card = By.id("debitNumber");
    By Kent_Expired_date_month = By.xpath("(//select[@class='paymentselect'])[2]");
    By Kent_Expired_date_year = By.xpath("(//select[@class='paymentselect'])[3]");
    By Kent_pin = By.id("cardPin");
    By Kent_Submit_Button = By.id("proceed");
    By Kent_confirmation_Button = By.id("proceedConfirm");


    private SHAFT.GUI.WebDriver driver;
    public PaymentPagePayfort(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    // general for payment method
    public void fClick_On_Payment_Option() {driver.element().click(Click_On_Payment_Option);}
    public void fadd_Card(String CardNumber) {driver.element().type(add_Card, CardNumber);}
    public void fExpiration_date(String Expirationdate) {driver.element().type(Expiration_date, Expirationdate);}
    public void fCVV(String CVVnumber) {driver.element().type(CVV, CVVnumber);}
    public void fButton_Pay() {driver.element().click(Button_Pay);}
    public void fOTP(String OTPNumber) {
        driver.element().type(OTP, OTPNumber);
    }
    public void fConfiramtion_Button() {driver.element().click(Confiramtion_Button);}
    public void femulator_submit_Ifram() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        driver.getDriver().switchTo().frame("challengeFrame");
        driver.element().click(emulator_submit_Ifram);
    }
    public void femulator_submit() {driver.element().click(emulator_submit);}


    // Benefit
    public void fBenefit_Option() {driver.element().click(Benefit);}
    public void fBenefit_add_Card(String BenefitCardNumber) {
        driver.element().type(Benefit_add_Card, BenefitCardNumber);
    }
    public void fBenefit_Expired_date_month(String month) {driver.element().select(Benefit_Expired_date_month, month);}
    public void fBenefit_Expired_date_year(String year) {
        driver.element().select(Benefit_Expired_date_year, year);
    }
    public void fBenefit_Card_holders_name(String Card_holders_nam) {driver.element().type(Benefit_Card_holders_name, Card_holders_nam);}
    public void fBenefit_pin() throws InterruptedException {
        driver.element().click(Benefit_pin);
        TimeUnit.SECONDS.sleep(3);
        driver.element().click(Benefit_pin_keyboard1);
        driver.element().click(Benefit_pin_keyboard2);
        driver.element().click(Benefit_pin_keyboard3);
        driver.element().click(Benefit_pin_keyboard4);
    }
    public void fBenefit_pay_Button() {
        driver.element().click(Benefit_pay_Button);
    }





    //KNET
    public void fKNET_Option() {driver.element().click(KNET);}
    public void fSelect_Your_Bank(String Bank_Name) {driver.element().select(Select_Your_Bank, Bank_Name);}
    public void fKent_add_Card(String KentCardNumber) {
        driver.element().type(Kent_add_Card, KentCardNumber);
    }
    public void fKent_Expired_date_month(String month) {driver.element().select(Kent_Expired_date_month, month);}
    public void fKent_Expired_date_year(String year) {
        driver.element().select(Kent_Expired_date_year, year);
    }
    public void fKent_pin(String KentPinNumber) {
        driver.element().type(Kent_pin, KentPinNumber);
    }
    public void fKent_Submit_Button() {
        driver.element().click(Kent_Submit_Button);
    }
    public void fKent_confirmation_Button() {
        driver.element().click(Kent_confirmation_Button);
    }


    //STC_ Pay
    public void fStc_Pay() {
        driver.element().click(Stc_Pay);
    }
    public void fStc_Phone_Number(String Phone_Number) {driver.element().type(Stc_Phone_Number, Phone_Number);}
    public void fStc_Otp_button() {
        driver.element().click(Stc_Otp_button);
    }
    public void fSTC_Otp_number(String otp) {
        driver.element().type(STC_Otp_number,otp);
    }









}
