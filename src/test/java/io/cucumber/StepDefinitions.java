package io.cucumber;

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

    @Given("I open chrome in Sauce Labs")
    public void open_chrome_in_sauce_labs() throws MalformedURLException {
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

    @Then("The browser is closed")
    public void browser_is_closed(){
        driver.quit();
    }

    @When("^I go to the login page$")
    public void go_to_login_page() {
        driver.get(BASE_URL);
    }


    @Then("The item list is not displayed")
    public void item_list_is_not_diplayed() {
        Assert.assertEquals(driver.findElements(By.xpath("//*[@id=\"inventory_container\"]")).size(), 0);
    }

    @Then("The item list is displayed")
    public void item_list_is_diplayed() {
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"inventory_container\"]")).isDisplayed());
    }

    @When("^I enter the username \"(\\w*)\"$")
    public void iEnterTheUsername(String username) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//*[@id=\"user-name\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"user-name\"]")).sendKeys(username);
    }

    @And("^I enter the password \"(\\w*)\"$")
    public void iEnterThePassword(String password) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//*[@id=\"password\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(password);
    }

    @And("^I click the submit button$")
    public void iClickTheSubmitButton() {
        driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/input[3]")).click();
    }

    @Then("^The login error is displayed$")
    public void theLoginErrorIsDisplayed() {
        driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3/button"));
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
