package io.cucumber;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final String BASE_URL = "https://www.saucedemo.com";

    private WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void visit() {
        driver.get(BASE_URL);
    }

    public void loginAs(String username, String password) {
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

    public boolean isErrorButtonDisplayed() {
        return driver.findElement(By.cssSelector(".error-button")).isDisplayed();
    }

    public String getUrl() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return driver.getCurrentUrl();
    }
}
