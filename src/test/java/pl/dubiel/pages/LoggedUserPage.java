package pl.dubiel.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.dubiel.utils.SeleniumHelper;

public class LoggedUserPage {

    @FindBy(xpath = "//a[@class='account']/span")
    private WebElement profileUserHeading;

    @FindBy(xpath = "//h1[contains(text(),'My account')]")
    private WebElement myAccountText;

    private WebDriver driver;

    public LoggedUserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public String getHeadingText() {
        SeleniumHelper.waitForElementToBeVisible(driver, profileUserHeading);
        return profileUserHeading.getText();
    }

    public Boolean headingTextDisplayed() {
        return profileUserHeading.isDisplayed();
    }

    public Boolean myAccountDisplayed(){return myAccountText.isDisplayed();};

}
