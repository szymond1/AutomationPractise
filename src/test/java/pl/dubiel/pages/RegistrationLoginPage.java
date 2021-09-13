package pl.dubiel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.dubiel.utils.SeleniumHelper;

import java.util.List;

public class RegistrationLoginPage {

    @FindBy(id = "email_create")
    private WebElement registrationEmailText;

    @FindBy(id = "SubmitCreate")
    private WebElement registerSubmit;

    @FindBy(id = "email")
    private WebElement loginEmailText;

    @FindBy(name = "passwd")
    private WebElement passwordText;

    @FindBy(id = "SubmitLogin")
    private WebElement loginSubmit;

    @FindBy(xpath ="//a[text()='Forgot your password?']")
    private WebElement forgotPasswdLink;

    @FindBy(id = "create_account_error")
    private WebElement createAccountError;

    @FindBy(xpath = "//div[@id='create_account_error']/ol/li")
    private WebElement createAccountErrorText;

    @FindBy(xpath = "//*[@id='center_column']/div[1]")
    private WebElement loginErrorDiv;

    @FindBy(xpath = "//*[@id='center_column']/div[1]/ol/li")
    private WebElement loginErrorText;

    private WebDriver driver;

    private static final Logger logger = LogManager.getLogger();

    public RegistrationLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public RegistrationLoginPage createAccount(String email){
        SeleniumHelper.waitForElementToExist(driver, By.id("email_create"));
        registrationEmailText.sendKeys(email);
        return this;
    }

    public String getEmailAdressRegisterError(){
        SeleniumHelper.waitForElementToBeVisible(driver, createAccountError);
        return createAccountErrorText.getText();
    }

    public String getLoginError(){
        SeleniumHelper.waitForElementToBeVisible(driver, loginErrorDiv);
        return loginErrorText.getText();
    }

    public RegistrationLoginPage setLoginEmail(String email){
        loginEmailText.sendKeys(email);
        return this;
    }

    public RegistrationLoginPage setPassword(String password){
        passwordText.sendKeys(password);
        return this;
    }

    public void submitRegister(){
        registerSubmit.click();
    }

    public void submitLogin(){
        loginSubmit.click();
    }

    public RegistrationLoginPage forgotPassword(){
        forgotPasswdLink.click();
        return this;
    }

}
