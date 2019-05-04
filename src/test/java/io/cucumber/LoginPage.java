package io.cucumber;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final String BASE_URL = "https://www.saucedemo.com";

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    public void visit() {
        driver.get(BASE_URL);
    }

    public void loginAs(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user-name")));
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.cssSelector(".btn_action")).click();
    }

    public boolean isErrorButtonDisplayed() {
        try {
            return driver.findElement(By.cssSelector(".error-button")).isDisplayed();
        }
        catch (NoSuchElementException e){
            return false;
        }
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
