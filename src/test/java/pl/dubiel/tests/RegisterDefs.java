package pl.dubiel.tests;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import pl.dubiel.pages.LoggedUserPage;
import pl.dubiel.pages.RegistrationLoginPage;
import pl.dubiel.pages.SignUpPage;
import pl.dubiel.utils.DriverFactory;
import pl.dubiel.utils.SeleniumHelper;

import java.util.List;

public class RegisterDefs {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger();

    @Before
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php");
    }



    @Given("^User is on main registration page$")
    public void userIsOnMainRegistrationPage() {
        driver.findElement(By.className("login")).click();
    }

    @When("^User is using not existing email \"([^\"]*)\" in database$")
    public void userIsUsingNotExistingEmailInDatabase(String email) throws Throwable {
        // W tym miejscu należaloby wykonac skrypt/ albo sprawdzic  czy na pewno nie istnieje w bazie danych
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        int randomNum = (int) (Math.random() * 10000);
        String emailParts[] = email.split("@");
        String newEmail = emailParts[0] + randomNum + "@" + emailParts[1];
        registrationLoginPage.createAccount(newEmail);
        logger.info("User not exists in database");

    }

    @And("^User click button Create an account$")
    public void userClickButtonCreateAnAccount() {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        registrationLoginPage.submitRegister();
    }

    @And("^The action redirect to Personal data form$")
    public void theActionRedirectToPersonalDataForm() {
        SeleniumHelper.waitForElementToExist(driver,By.id("customer_firstname"));
    }

    @And("^User Fill the form with all required data \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\"$")
    public void userFillTheFormWithAllRequiredData(String firstname, String lastname, String password, String address, String city, String zipcode, String phone, String alias) throws Throwable {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.fillSignUpForm(firstname, lastname, password, address, city, zipcode, phone, alias);
    }

    @And("^User click button Register$")
    public void userClickButtonRegister() {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp();
    }

    @Then("^User is registered and is redirect to user account page \"([^\"]*)\"$")
    public void userIsRegisteredAndIsRedirectToUserAccountPage(String name) throws Throwable {
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        Assert.assertEquals(loggedUserPage.getHeadingText(), name);
        Assert.assertTrue(loggedUserPage.myAccountDisplayed());
        String actualUrl = "http://automationpractice.com/index.php?controller=my-account";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);
    }

    @And("^User Fill the form without password and lastname \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\",\"([^\"]*)\", \"([^\"]*)\"$")
    public void userFillTheFormWithoutPasswordAndLastname(String firstname, String address, String city, String zipcode, String phone, String alias) throws Throwable {
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.fillSignUpForm(firstname, address, city, zipcode, phone, alias);
    }

    @Then("^User is not registered and information about failed attempt is printed \"([^\"]*)\", \"([^\"]*)\"$")
    public void userIsNotRegisteredAndInformationAboutFailedAttemptIsPrinted(String arg0, String arg1) throws Throwable {
        SignUpPage signUpPage = new SignUpPage(driver);
        List<String> errorsList = signUpPage.getSignUpErrorsList();
        Assert.assertTrue(!errorsList.isEmpty());
        Assert.assertTrue(errorsList.contains(arg0));
        Assert.assertTrue(errorsList.contains(arg1));
    }

    @When("^User is using incorrect email format \"([^\"]*)\"$")
    public void userIsUsingIncorrectEmailFormat(String email) throws Throwable {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        registrationLoginPage.createAccount(email);
    }

    @Then("^User is not directed to personal data form and information about failed attempt is printed \"([^\"]*)\"$")
    public void userIsNotDirectedToPersonalDataFormAndInformationAboutFailedAttemptIsPrinted(String arg0) throws Throwable {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        Assert.assertNotNull(registrationLoginPage.getEmailAdressRegisterError());
        Assert.assertTrue(registrationLoginPage.getEmailAdressRegisterError().equals(arg0));
    }

    @When("^User is using an email which exists in database \"([^\"]*)\"$")
    public void userIsUsingAnEmailWhichExistsInDatabase(String email) throws Throwable {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        registrationLoginPage.createAccount(email);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

}
