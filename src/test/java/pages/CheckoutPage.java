package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Page Object for Swag Labs Checkout process
 * Handles checkout information, overview, and completion
 */
public class CheckoutPage extends BasePage {

    // Checkout Information Page Locators
    private final By pageTitle = By.className("title");
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    // Checkout Overview Page Locators
    private final By summaryItems = By.className("cart_item");
    private final By summaryItemNames = By.className("inventory_item_name");
    private final By summaryItemPrices = By.className("inventory_item_price");
    private final By summarySubTotal = By.className("summary_subtotal_label");
    private final By summaryTax = By.className("summary_tax_label");
    private final By summaryTotal = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    // Checkout Complete Page Locators
    private final By completeHeader = By.className("complete-header");
    private final By completeText = By.className("complete-text");
    private final By backToProductsButton = By.id("back-to-products");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if checkout information page is displayed
     */
    public boolean isCheckoutInformationPageDisplayed() {
        return isElementDisplayed(firstNameField) &&
                getCurrentUrl().contains("checkout-step-one.html");
    }

    /**
     * Check if checkout overview page is displayed
     */
    public boolean isCheckoutOverviewPageDisplayed() {
        return isElementDisplayed(finishButton) &&
                getCurrentUrl().contains("checkout-step-two.html");
    }

    /**
     * Check if checkout complete page is displayed
     */
    public boolean isCheckoutCompletePageDisplayed() {
        return isElementDisplayed(completeHeader) &&
                getCurrentUrl().contains("checkout-complete.html");
    }

    /**
     * Fill in first name
     */
    public void enterFirstName(String firstName) {
        typeText(firstNameField, firstName);
    }

    /**
     * Fill in last name
     */
    public void enterLastName(String lastName) {
        typeText(lastNameField, lastName);
    }

    /**
     * Fill in postal code
     */
    public void enterPostalCode(String postalCode) {
        typeText(postalCodeField, postalCode);
    }

    /**
     * Fill in all checkout information
     */
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    /**
     * Click continue button to proceed to overview
     */
    public void clickContinue() {
        clickElement(continueButton);
    }

    /**
     * Click cancel button to go back to cart
     */
    public void clickCancel() {
        clickElement(cancelButton);
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
     * Get page title text
     */
    public String getPageTitle() {
        return getElementText(pageTitle);
    }

    // Checkout Overview Page Methods

    /**
     * Get all items in checkout summary
     */
    public List<String> getSummaryItemNames() {
        return findElements(summaryItemNames)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Check if specific item is in checkout summary
     */
    public boolean isItemInSummary(String itemName) {
        return getSummaryItemNames().contains(itemName);
    }

    /**
     * Get subtotal amount
     */
    public String getSubTotal() {
        return getElementText(summarySubTotal);
    }

    /**
     * Get tax amount
     */
    public String getTaxAmount() {
        return getElementText(summaryTax);
    }

    /**
     * Get total amount
     */
    public String getTotalAmount() {
        return getElementText(summaryTotal);
    }

    /**
     * Get number of items in summary
     */
    public int getSummaryItemCount() {
        return findElements(summaryItems).size();
    }

    /**
     * Click finish button to complete order
     */
    public void clickFinish() {
        clickElement(finishButton);
    }

    // Checkout Complete Page Methods

    /**
     * Get completion header text
     */
    public String getCompletionHeader() {
        return getElementText(completeHeader);
    }

    /**
     * Get completion message text
     */
    public String getCompletionMessage() {
        return getElementText(completeText);
    }

    /**
     * Click back to products button
     */
    public void clickBackToProducts() {
        clickElement(backToProductsButton);
    }

    /**
     * Verify order completion success
     */
    public boolean isOrderCompleted() {
        return isCheckoutCompletePageDisplayed() &&
                getCompletionHeader().contains("Thank you for your order!");
    }
}