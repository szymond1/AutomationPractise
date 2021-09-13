package pl.dubiel.tests;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pl.dubiel.utils.DriverFactory;

import java.io.IOException;

public class BaseHotelTest {

    protected WebDriver driver;

    @BeforeTest
    public void beforeTest(){
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false); // true - nie widzimy wykonywania testu
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
    }

    @BeforeMethod
    public void beforeMethod() throws IOException {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get("http://automationpractice.com/index.php");
    }

    @AfterMethod
    public void afterMethod(){
        driver.quit();
    }

}
