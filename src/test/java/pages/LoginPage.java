package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By errorButton = By.className("error-button");

    // Page URL
    private final String LOGIN_URL = "https://www.saucedemo.com/";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to login page
     */
    public void navigateToLoginPage() {
        driver.get(LOGIN_URL);
    }

    /**
     * Enter username
     */
    public void enterUsername(String username) {
        typeText(usernameField, username);
    }

    /**
     * Enter password
     */
    public void enterPassword(String password) {
        typeText(passwordField, password);
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        clickElement(loginButton);
    }

    /**
     * Perform complete login action
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Check if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(loginButton) &&
                getCurrentUrl().contains("saucedemo.com");
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        return getElementText(errorMessage);
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    /**
     * Clear error message by clicking X button
     */
    public void clearErrorMessage() {
        if (isElementDisplayed(errorButton)) {
            clickElement(errorButton);
        }
    }

    /**
     * Check if username field is empty
     */
    public boolean isUsernameFieldEmpty() {
        return driver.findElement(usernameField).getAttribute("value").isEmpty();
    }

    /**
     * Check if password field is empty
     */
    public boolean isPasswordFieldEmpty() {
        return driver.findElement(passwordField).getAttribute("value").isEmpty();
    }
}