package io.cucumber;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepDefinitions {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    private String sauce_username = System.getenv("SAUCE_USERNAME");
    private String sauce_accesskey = System.getenv("SAUCE_ACCESS_KEY");

    private String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }



    @Before
    public void setUp(Scenario scenario) throws MalformedURLException {
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
        sauceOptions.setCapability("name", scenario.getName());
        sauceOptions.setCapability("build", "cucumber-jvm-" + getTimestamp());
        sauceOptions.setCapability("extendedDebugging", true);

        //Assign the Sauce Options to the base capabilities
        caps.setCapability("sauce:options", sauceOptions);

        //Create a new RemoteWebDriver, which will initialize the test execution on Sauce Labs servers
        String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";

        driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @After
    public void tearDown(Scenario scenario){
        String sauceResult = scenario.isFailed() ? "failed" : "passed";
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + sauceResult);
        driver.quit();
    }

    @Given("^I go to the login page$")
    public void goToLoginPage() {
        loginPage.visit();
    }

    @Given("^I go to the inventory page$")
    public void goToInventoryPage() {
        inventoryPage.visit();
    }

    @When("^I login as (\\w*) / (\\w*)$")
    public void loginAs(String username, String password){
        loginPage.loginAs(username, password);
    }

    @When("^I get the first inventory item$")
    public void getFirstInventoryItem(){
        inventoryPage.getInventoryList();
    }

    @When("^I login with blank credentials$")
    public void loginBlank(){
        loginPage.loginAs("", "");
    }

    @Then("^The login error is displayed$")
    public void theLoginErrorIsDisplayed() {
        Assert.assertTrue(loginPage.isErrorButtonDisplayed());
    }

    @Then("^The url contains \"(\\w*)\"$")
    public void theUrlContains(String expectedValue) {
        Assert.assertTrue(loginPage.getUrl().contains(expectedValue));
    }

    @Then("^The inventory item is a (\\w+)$")
    public void theFirstItemis(String expected){
        String actual = inventoryPage.getInventoryList().get(0).getText().toLowerCase();

        Assert.assertTrue(actual.contains(expected));
    }
}
