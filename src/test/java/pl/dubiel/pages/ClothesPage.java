package pl.dubiel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pl.dubiel.utils.SeleniumHelper;

import java.util.List;
import java.util.stream.Collectors;

public class ClothesPage {

    @FindBy(xpath = "//div[@id='block_top_menu']/ul/li[1]/a")
    private WebElement womanClothesSection;

    @FindBy(xpath = "//div[@id='block_top_menu']/ul/li[2]/a")
    private WebElement womanDressesSection;

    //@FindBy(xpath = "(//a[@title='Add to cart'])[1]")
    @FindBy(xpath = "//*[@id='center_column']/ul/li[1]/div/div[2]/div[2]/a[1]")
    private WebElement firstItem;

    @FindBy(xpath = "//*[@id='center_column']/ul/li[2]/div/div[2]/div[2]/a[1]")
    private WebElement secondItem;

    @FindBy(id="layer_cart")
    private WebElement cartLayer;

    @FindBy(className = "ajax_cart_product_txt")
    private WebElement itemCounterText;

    @FindBy(xpath = "//span[@title='Continue shopping']")
    private WebElement continueShoppingButton;

    @FindBy(xpath = "//a[@title='View my shopping cart']")
    private WebElement viewShoppingCartButton;

    @FindBy(xpath = "(//a[@class='cart_quantity_delete'])[1]")
    private WebElement firstItemDeleteButton;

    @FindBy(id = "summary_products_quantity")
    private WebElement productsSummaryText;

    @FindBy(xpath = "//table[@id='cart_summary']/tbody/tr")
    private List<WebElement> productsCartList;


    private WebDriver driver;

    private static final Logger logger = LogManager.getLogger();

    public ClothesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void womanClothesSubmit(){
        womanClothesSection.click();
    }

    public void womanDressesSubmit(){
        womanDressesSection.click();
    }

    public void waitForFirstProduct(){
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//ul[@class='product_list grid row']/li[1]"))).perform();
        SeleniumHelper.waitForElementToBeClickable(driver, By.xpath("//*[@id='center_column']/ul/li[1]/div/div[2]/div[2]/a[1]"));
    }

    public void waitForSecondProduct(){
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//ul[@class='product_list grid row']/li[2]"))).perform();
        SeleniumHelper.waitForElementToBeClickable(driver, By.xpath("//*[@id='center_column']/ul/li[2]/div/div[2]/div[2]/a[1]"));
    }

    public void waitForProductList(){
        SeleniumHelper.waitForElementToExist(driver, By.id("center_column"));
    }

    public void waitForCartLayer(){
        SeleniumHelper.waitForElementToBeVisible(driver, driver.findElement(By.id("layer_cart")));
    }

    public void waitForProductSummaryText(){
        SeleniumHelper.waitForElementToBeVisible(driver, driver.findElement(By.id("summary_products_quantity")));
    }

    public void waitForCartSummary(){
        SeleniumHelper.waitForElementToBeVisible(driver, driver.findElement(By.id("cart_summary")));
    }

    public Boolean isCartLayerDisplayed(){
        return cartLayer.isDisplayed();
    }

    public void firstItemSubmit(){
        firstItem.click();
    }

    public void secondItemSubmit(){
        secondItem.click();
    }

    public void continueButtonSubmit(){
        continueShoppingButton.click();
    }

    public void viewShoppingCartLink(){
        viewShoppingCartButton.click();
    }

    public void firstItemDeleteSubmit(){
        firstItemDeleteButton.click();
    }

    public String productSummaryText(){
        return productsSummaryText.getText();
    }

    public int productsInCart(){
        return productsCartList.size();
    }



}
