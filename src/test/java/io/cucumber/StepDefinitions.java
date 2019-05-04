package io.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class StepDefinitions {
    private WebDriver driver;

    private String sauce_username = System.getenv("SAUCE_USERNAME");
    private String sauce_accesskey = System.getenv("SAUCE_ACCESS_KEY");

    private final String BASE_URL = "https://www.saucedemo.com";

    @Before
    public void setUp() throws MalformedURLException {
        //Set up the ChromeOptions object, which will store the capabilities for the Sauce run
        ChromeOptions caps = new ChromeOptions();
        caps.setCapability("version", "latest");
        caps.setCapability("platform", "Windows 10");
        caps.setExperimentalOption("w3c", true);

        //Create a map of capabilities called "sauce:options", which contain the info necessary to run on Sauce
        // Labs, using the credentials stored in the environment variables. Also runs using the new W3C standard.
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauce_username);
        sauceOptions.setCapability("accessKey", sauce_accesskey);
        sauceOptions.setCapability("seleniumVersion", "3.141.59");

        //Assign the Sauce Options to the base capabilities
        caps.setCapability("sauce:options", sauceOptions);

        //Create a new RemoteWebDriver, which will initialize the test execution on Sauce Labs servers
        String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
        driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Given("^I go to the login page$")
    public void goToLoginPage() {
        driver.get(BASE_URL);
    }


    @When("^I login as (\\w*) / (\\w*)$")
    public void loginAs(String username, String password){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(username);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.cssSelector(".btn_action")).click();
    }

    @Then("The item list is not displayed")
    public void itemListIsNotDisplayed() {
        Assert.assertEquals(driver.findElements(By.id("inventory_container")).size(), 0);
    }

    @Then("The item list is displayed")
    public void itemListIsDisplayed() {
        Assert.assertTrue(driver.findElement(By.id("inventory_container")).isDisplayed());
    }

    @Then("^The login error is displayed$")
    public void theLoginErrorIsDisplayed() {
        driver.findElement(By.cssSelector(".error-button"));
    }

    @Then("^The url contains \"(\\w*)\"$")
    public void theUrlContains(String expectedValue) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedValue));
    }
}
