package io.cucumber;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class InventoryPage {

    private final String BASE_URL = "https://www.saucedemo.com/inventory.html";

    private WebDriver driver;
    private WebDriverWait wait;

    public InventoryPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    public void visit() {
        driver.get(BASE_URL);
    }

    public List<WebElement> getInventoryList(){
        return driver.findElements(By.className("inventory_item_name"));
    }
}
