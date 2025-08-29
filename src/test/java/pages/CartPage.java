package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Page Object for Swag Labs Shopping Cart page
 * Handles cart operations and checkout initiation
 */
public class CartPage extends BasePage {

    // Locators
    private final By pageTitle = By.className("title");
    private final By cartItems = By.className("cart_item");
    private final By cartItemNames = By.className("inventory_item_name");
    private final By cartItemPrices = By.className("inventory_item_price");
    private final By removeButtons = By.xpath("//button[contains(text(),'Remove')]");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");
    private final By emptyCartMessage = By.xpath("//div[@class='cart_list']");

    // Dynamic locators
    private final String removeItemByName = "//button[contains(@data-test, 'remove')]";
    private final String cartItemByName = "//div[@class='inventory_item_name' and text()='%s']";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if cart page is displayed
     */
    public boolean isCartPageDisplayed() {
        return isElementDisplayed(pageTitle) &&
                getCurrentUrl().contains("cart.html");
    }

    /**
     * Get page header text
     */
    public String getPageHeader() {
        return getElementText(pageTitle);
    }

    /**
     * Get all items in cart
     */
    public List<String> getCartItemNames() {
        return findElements(cartItemNames)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Get number of items in cart
     */
    public int getCartItemCount() {
        return findElements(cartItems).size();
    }

    /**
     * Check if specific item is in cart
     */
    public boolean isItemInCart(String itemName) {
        By itemLocator = By.xpath(String.format(cartItemByName, itemName));
        return isElementDisplayed(itemLocator);
    }

    /**
     * Remove specific item from cart
     */
    public void removeItemFromCart(String itemName) {
        By removeButton = By.xpath(String.format(removeItemByName, itemName));
        clickElement(removeButton);
    }

    /**
     * Check if cart is empty
     */
    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }

    /**
     * Get item price by name
     */
    public String getItemPrice(String itemName) {
        By priceLocator = By.xpath(String.format(
                "//div[text()='%s']/ancestor::div[@class='cart_item']//div[@class='inventory_item_price']",
                itemName));
        return getElementText(priceLocator);
    }

    /**
     * Continue shopping (go back to products page)
     */
    public void continueShopping() {
        clickElement(continueShoppingButton);
    }

    /**
     * Proceed to checkout
     */
    public void proceedToCheckout() {
        clickElement(checkoutButton);
    }

    /**
     * Get total number of remove buttons (items that can be removed)
     */
    public int getRemoveButtonCount() {
        return findElements(removeButtons).size();
    }

    /**
     * Remove all items from cart
     */
    public void removeAllItems() {
        List<WebElement> removeButtonsList = findElements(removeButtons);
        for (WebElement button : removeButtonsList) {
            button.click();
            // Wait a moment for DOM to update
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Check if checkout button is enabled
     */
    public boolean isCheckoutButtonEnabled() {
        return driver.findElement(checkoutButton).isEnabled();
    }

    /**
     * Check if continue shopping button is displayed
     */
    public boolean isContinueShoppingButtonDisplayed() {
        return isElementDisplayed(continueShoppingButton);
    }
}