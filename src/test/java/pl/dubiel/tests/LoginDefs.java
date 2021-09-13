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
import pl.dubiel.utils.DriverFactory;
import pl.dubiel.utils.SeleniumHelper;

public class LoginDefs {

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

    @Given("^User is on loggin page$")
    public void userIsOnLogginPage() {
        driver.findElement(By.className("login")).click();
    }

    @And("^User with email \"([^\"]*)\" and password \"([^\"]*)\" exist in database$")
    public void userWithEmailAndPasswordExistInDatabase(String arg0, String arg1) throws Throwable {
        logger.info("User exists in database");
    }

    @When("^User is using email\"([^\"]*)\" and password \"([^\"]*)\"$")
    public void userIsUsingEmailAndPassword(String email, String password) throws Throwable {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        SeleniumHelper.waitForElementToExist(driver,By.id("email"));
        registrationLoginPage.setLoginEmail(email).setPassword(password);
    }

    @And("^User click button Sign In$")
    public void userClickButtonSignIn() {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        registrationLoginPage.submitLogin();
    }

    @Then("^User is logged and is redirect to user account page \"([^\"]*)\"$")
    public void userIsLoggedAndIsRedirectToUserAccountPage(String name) throws Throwable {
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        Assert.assertEquals(loggedUserPage.getHeadingText(), name);
        Assert.assertTrue(loggedUserPage.myAccountDisplayed());
    }

    @And("^Information about correct login is printed$")
    public void informationAboutCorrectLoginIsPrinted() {
        String actualUrl = "http://automationpractice.com/index.php?controller=my-account";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);

    }

    @When("^User is using email \"([^\"]*)\"$")
    public void userIsUsingEmail(String email) throws Throwable {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        SeleniumHelper.waitForElementToExist(driver,By.id("email"));
        registrationLoginPage.setLoginEmail(email);
    }


    @Then("^User is not logged and information about failed attempt is printed \"([^\"]*)\"$")
    public void userIsNotLoggedAndInformationAboutFailedAttemptIsPrinted(String msg) throws Throwable {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        Assert.assertNotNull(registrationLoginPage.getLoginError());
        Assert.assertTrue(registrationLoginPage.getLoginError().equals(msg));
    }

    @And("^User with email \"([^\"]*)\" and password \"([^\"]*)\" not exist in database$")
    public void userWithEmailAndPasswordNotExistInDatabase(String arg0, String arg1) throws Throwable {
        // W tym miejscu należaloby wykonac skrypt/ albo sprawdzic  czy na pewno nie istnieje w bazie danych
        logger.info("User not exists in database");
    }

    @After
    public void tearDown(){
        driver.quit();
    }

}
