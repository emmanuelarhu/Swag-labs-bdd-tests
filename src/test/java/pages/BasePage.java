package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

/**
 * Base Page Object class containing common methods and utilities
 * Following Page Object Model best practices
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // Common locators
    protected By cartBadge = By.className("shopping_cart_badge");
    protected By cartIcon = By.className("shopping_cart_link");
    protected By menuButton = By.id("react-burger-menu-btn");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Wait for element to be visible and return it
     */
    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable and return it
     */
    protected WebElement waitForClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get text from element
     */
    protected String getElementText(By locator) {
        return waitForElement(locator).getText();
    }

    /**
     * Click on element
     */
    protected void clickElement(By locator) {
        waitForClickableElement(locator).click();
    }

    /**
     * Type text into input field
     */
    protected void typeText(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get cart badge count
     */
    public String getCartBadgeCount() {
        try {
            return getElementText(cartBadge);
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * Check if cart badge is visible
     */
    public boolean isCartBadgeVisible() {
        return isElementDisplayed(cartBadge);
    }

    /**
     * Click on cart icon
     */
    public void clickCartIcon() {
        clickElement(cartIcon);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Find elements by locator
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
}