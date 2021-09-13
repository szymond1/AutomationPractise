package pl.dubiel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pl.dubiel.utils.SeleniumHelper;

import java.util.List;
import java.util.stream.Collectors;

public class SignUpPage {

    @FindBy(id = "id_gender1")
    private WebElement gender;

    @FindBy(id = "customer_firstname")
    private WebElement firstnameText;

    @FindBy(id = "customer_lastname")
    private WebElement lastnameText;

    @FindBy(id = "email")
    private WebElement emailText;

    @FindBy(id = "passwd")
    private WebElement passwordText;

    @FindBy(id = "days")
    private WebElement daysSelect;

    @FindBy(id = "months")
    private WebElement monthsSelect;

    @FindBy(id = "years")
    private WebElement yearsSelect;

    @FindBy(id = "address1")
    private WebElement addressText;

    @FindBy(id = "city")
    private WebElement cityText;

    @FindBy(id = "id_state")
    private WebElement state;

    @FindBy(id = "postcode")
    private WebElement postcodeText;

    @FindBy(id = "id_country")
    private WebElement country;

    @FindBy(id = "phone_mobile")
    private WebElement phoneText;

    @FindBy(id = "alias")
    private WebElement aliasText;

    @FindBy(id="submitAccount")
    private WebElement submitAccount;

    @FindBy(xpath = "//*[@id='center_column']/div[1]/ol/li")
    private List<WebElement> signUpErrors;

    private WebDriver driver;

    private static final Logger logger = LogManager.getLogger();

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void fillSignUpForm(String firstname, String lastname, String password, String address,
                               String city, String zipcode, String phone, String alias){
        gender.click();
        firstnameText.sendKeys(firstname);
        lastnameText.sendKeys(lastname);
        passwordText.sendKeys(password);
        monthYearSelectFill();
        addressText.sendKeys(address);
        cityText.sendKeys(city);
        Select stateSelect = new Select(state);
        stateSelect.selectByValue("1");
        postcodeText.sendKeys(zipcode);
        phoneText.sendKeys(phone);
        aliasText.clear();
        aliasText.sendKeys(alias);
    }

    public void fillSignUpForm(String firstname, String address, String city, String zipcode, String phone, String alias){
        gender.click();
        firstnameText.sendKeys(firstname);
        monthYearSelectFill();
        addressText.sendKeys(address);
        cityText.sendKeys(city);
        Select stateSelect = new Select(state);
        stateSelect.selectByValue("1");
        postcodeText.sendKeys(zipcode);
        phoneText.sendKeys(phone);
        aliasText.clear();
        aliasText.sendKeys(alias);
    }

    public void monthYearSelectFill(){
        Select daySelect = new Select(daysSelect);
        daySelect.selectByValue("15");
        Select monthSelect = new Select(monthsSelect);
        monthSelect.selectByValue("5");
        Select yearSelect = new Select(yearsSelect);
        yearSelect.selectByValue("1993");
    }

    public void signUp(){
        submitAccount.click();
    }

    public List<String> getSignUpErrorsList(){
        SeleniumHelper.waitForNotEmptyList(driver, By.xpath("//*[@id='center_column']/div[1]/ol/li"));
        return signUpErrors.stream().map(el -> el.getText()).collect(Collectors.toList());
    }

}
