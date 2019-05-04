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

public class StepDefinitions {

    private WebDriver driver;
    private LoginPage loginPage;

    private String sauce_username = System.getenv("SAUCE_USERNAME");
    private String sauce_accesskey = System.getenv("SAUCE_ACCESS_KEY");

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
        sauceOptions.setCapability("build", "cucumber-demo");

        //Assign the Sauce Options to the base capabilities
        caps.setCapability("sauce:options", sauceOptions);

        //Create a new RemoteWebDriver, which will initialize the test execution on Sauce Labs servers
        String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";

        driver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), caps);
        loginPage = new LoginPage(driver);
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


    @When("^I login as (\\w*) / (\\w*)$")
    public void loginAs(String username, String password){
        loginPage.loginAs(username, password);
    }

    @Then("^The login error is displayed$")
    public void theLoginErrorIsDisplayed() {
        Assert.assertTrue(loginPage.isErrorButtonDisplayed());
    }

    @Then("^The url contains \"(\\w*)\"$")
    public void theUrlContains(String expectedValue) {
        Assert.assertTrue(loginPage.getUrl().contains(expectedValue));
    }
}
