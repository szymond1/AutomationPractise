package pl.dubiel.tests;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pl.dubiel.pages.LoggedUserPage;
import pl.dubiel.pages.RegistrationLoginPage;
import pl.dubiel.pages.SignUpPage;
import pl.dubiel.utils.SeleniumHelper;

import java.util.List;

public class AutomationPractiseTest extends BaseHotelTest{

    @Test
    public void registerValidUser(){
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        SignUpPage signUpPage = new SignUpPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        driver.findElement(By.className("login")).click();
        int randomNum = (int) (Math.random() * 10000);
        String email = "admin" + randomNum + "@onet.eu";
        registrationLoginPage.createAccount(email);
        registrationLoginPage.submitRegister();
        SeleniumHelper.waitForElementToExist(driver,By.id("customer_firstname"));
        signUpPage.fillSignUpForm("Szymon", "Dubiel", "password", "Stanczyka", "Krakow", "30126",
                "724570120", "SD");
        signUpPage.signUp();
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Szymon Dubiel");
        Assert.assertTrue(loggedUserPage.myAccountDisplayed());

        String actualUrl = "http://automationpractice.com/index.php?controller=my-account";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);
    }

    @Test
    public void registerWithoutLastnameAndPassword(){
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        SignUpPage signUpPage = new SignUpPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        driver.findElement(By.className("login")).click();
        int randomNum = (int) (Math.random() * 10000);
        String email = "admin" + randomNum + "@onet.eu";
        registrationLoginPage.createAccount(email);
        registrationLoginPage.submitRegister();
        SeleniumHelper.waitForElementToExist(driver,By.id("customer_firstname"));
        signUpPage.fillSignUpForm("Szymon", "Stanczyka", "Krakow", "30126",
                "724570120", "SD");
        signUpPage.signUp();

        List<String> errorsList = signUpPage.getSignUpErrorsList();
        Assert.assertTrue(!errorsList.isEmpty());
        Assert.assertTrue(errorsList.contains("lastname is required."));
        Assert.assertTrue(errorsList.contains("passwd is required."));
    }

    @Test
    public void incorrectEmailFormatRegister(){
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        SignUpPage signUpPage = new SignUpPage(driver);

        driver.findElement(By.className("login")).click();
        registrationLoginPage.createAccount("dubiel.szymon");
        registrationLoginPage.submitRegister();

        Assert.assertNotNull(registrationLoginPage.getEmailAdressRegisterError());
        Assert.assertTrue(registrationLoginPage.getEmailAdressRegisterError().equals("Invalid email address."));

    }

    @Test
    public void existsEmailRegister(){
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        SignUpPage signUpPage = new SignUpPage(driver);

        driver.findElement(By.className("login")).click();
        registrationLoginPage.createAccount("dubiel.szymon@gmail.com");
        registrationLoginPage.submitRegister();

        Assert.assertNotNull(registrationLoginPage.getEmailAdressRegisterError());
        Assert.assertTrue(registrationLoginPage.getEmailAdressRegisterError().equals("An account using this email address has already been registered. Please enter a valid password or request a new one."));
    }

    @Test
    public void loggedInWithCorrectData(){
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        driver.findElement(By.className("login")).click();
        SeleniumHelper.waitForElementToExist(driver,By.id("email"));
        registrationLoginPage.setLoginEmail("dubiel.szymon@gmail.com").setPassword("password").submitLogin();

        Assert.assertEquals(loggedUserPage.getHeadingText(), "Szymon Dubiel");
        Assert.assertTrue(loggedUserPage.myAccountDisplayed());

        String actualUrl = "http://automationpractice.com/index.php?controller=my-account";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);
    }

    @Test
    public void userTryToLogInWithNotAuthenticatedEmail(){
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        driver.findElement(By.className("login")).click();
        SeleniumHelper.waitForElementToExist(driver,By.id("email"));
        registrationLoginPage.setLoginEmail("dubiel.szymon223323232@gmail.com").setPassword("password").submitLogin();

        Assert.assertNotNull(registrationLoginPage.getLoginError());
        Assert.assertTrue(registrationLoginPage.getLoginError().equals("Authentication failed."));
    }

    @Test
    public void userTryToLogInWithNoPassword(){
        RegistrationLoginPage registrationLoginPage = new RegistrationLoginPage(driver);
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);

        driver.findElement(By.className("login")).click();
        SeleniumHelper.waitForElementToExist(driver,By.id("email"));
        registrationLoginPage.setLoginEmail("dubiel.szymon@gmail.com").submitLogin();

        Assert.assertNotNull(registrationLoginPage.getLoginError());
        Assert.assertTrue(registrationLoginPage.getLoginError().equals("Password is required."));
    }


}
