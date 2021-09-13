package pl.dubiel.tests;

import cucumber.api.PendingException;
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
import pl.dubiel.pages.ClothesPage;
import pl.dubiel.pages.LoggedUserPage;
import pl.dubiel.pages.RegistrationLoginPage;
import pl.dubiel.utils.DriverFactory;
import pl.dubiel.utils.SeleniumHelper;

public class ClothesDefs {

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

    @Given("^User is on main application page$")
    public void userIsOnMainApplicationPage() {
        driver.findElement(By.className("login")).click();
    }

    @And("^User is logging in with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void userIsLoggingInWithEmailAndPassword(String email, String password) throws Throwable {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        SeleniumHelper.waitForElementToExist(driver,By.id("email"));
        registrationLoginPage.setLoginEmail(email).setPassword(password);
    }

    @And("^User click button to Sign In$")
    public void userClickButtonToSignIn() {
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        registrationLoginPage.submitLogin();
    }

    @And("^User is logged and is redirect to user account page$")
    public void userIsLoggedAndIsRedirectToUserAccountPage() {
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        Assert.assertTrue(loggedUserPage.myAccountDisplayed());
        String actualUrl = "http://automationpractice.com/index.php?controller=my-account";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);
    }

    @When("^User is clicking on Woman Sector Button$")
    public void userIsClickingOnWomanSectorButton() {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.womanClothesSubmit();
        clothesPage.waitForProductList();
    }

    @And("^Add first item to cart$")
    public void addFirstItemToCart() {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.waitForFirstProduct();
        clothesPage.firstItemSubmit();
    }

    @And("^Cart Layer is displayed$")
    public void cartLayerIsDisplayed() {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.waitForCartLayer();
        Assert.assertTrue(clothesPage.isCartLayerDisplayed());
        clothesPage.continueButtonSubmit();
    }

    @Then("^Product is successfully added to cart$")
    public void productIsSuccessfullyAddedToCart() {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.viewShoppingCartLink();
        clothesPage.waitForProductSummaryText();
        Assert.assertEquals(clothesPage.productSummaryText(), "1 Product");
    }

    @And("^Secont item is added to cart$")
    public void secontItemIsAddedToCart() {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.waitForSecondProduct();
        clothesPage.secondItemSubmit();
    }

    @Then("^The list had (\\d+) items inside$")
    public void theListHadItemsInside(int arg0) {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.viewShoppingCartLink();
        clothesPage.waitForProductSummaryText();
        Assert.assertEquals(clothesPage.productSummaryText(), "2 Products");
    }

    @And("^Click on Cart Button$")
    public void clickOnCartButton() {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.viewShoppingCartLink();
        clothesPage.waitForProductSummaryText();
    }

    @And("^Click on First Item Delete Button$")
    public void clickOnFirstItemDeleteButton() {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.firstItemDeleteSubmit();
    }

    @Then("^The first item is removed and the shopping list had (\\d+) item inside$")
    public void theFirstItemIsRemovedAndTheShoppingListHadItemInside(int arg0) {
        ClothesPage clothesPage = new ClothesPage(driver);
        clothesPage.viewShoppingCartLink();
        clothesPage.waitForCartSummary();
        Assert.assertEquals(clothesPage.productsInCart(), 1);

    }
}
