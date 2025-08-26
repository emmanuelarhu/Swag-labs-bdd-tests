package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * Page Object for Swag Labs Products page
 * Contains all product listing and interaction methods
 */
public class ProductsPage extends BasePage {

    // Locators
    private final By pageTitle = By.className("title");
    private final By productItems = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By addToCartButtons = By.xpath("//button[contains(text(),'Add to cart')]");
    private final By removeFromCartButtons = By.xpath("//button[contains(text(),'Remove')]");
    private final By sortDropdown = By.className("product_sort_container");

    // Dynamic locators - will be formatted with product name
    private final String addToCartByProductName = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button[text()='Add to cart']";
    private final String removeFromCartByProductName = "//div[text()='%s']/ancestor::div[@class='inventory_item']//button[text()='Remove']";
    private final String productLinkByName = "//div[text()='%s']";

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get page header text
     */
    public String getPageHeader() {
        return getElementText(pageTitle);
    }

    /**
     * Check if products page is displayed
     */
    public boolean isProductsPageDisplayed() {
        return isElementDisplayed(pageTitle) &&
                getPageHeader().equals("Products");
    }

    /**
     * Get all product names
     */
    public List<String> getAllProductNames() {
        return findElements(productNames)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Get number of products displayed
     */
    public int getProductCount() {
        return findElements(productItems).size();
    }

    /**
     * Add specific product to cart by name
     */
    public void addProductToCart(String productName) {
        By addToCartButton = By.xpath(String.format(addToCartByProductName, productName));
        clickElement(addToCartButton);
    }

    /**
     * Remove specific product from cart by name
     */
    public void removeProductFromCart(String productName) {
        By removeButton = By.xpath(String.format(removeFromCartByProductName, productName));
        clickElement(removeButton);
    }

    /**
     * Click on product name to view details
     */
    public void clickProductName(String productName) {
        By productLink = By.xpath(String.format(productLinkByName, productName));
        clickElement(productLink);
    }

    /**
     * Check if product is in cart (Remove button visible)
     */
    public boolean isProductInCart(String productName) {
        By removeButton = By.xpath(String.format(removeFromCartByProductName, productName));
        return isElementDisplayed(removeButton);
    }

    /**
     * Get product price by name
     */
    public String getProductPrice(String productName) {
        By priceLocator = By.xpath(String.format(
                "//div[text()='%s']/ancestor::div[@class='inventory_item']//div[@class='inventory_item_price']",
                productName));
        return getElementText(priceLocator);
    }

    /**
     * Get total number of items in cart from all products
     */
    public int getTotalItemsInCart() {
        return findElements(removeFromCartButtons).size();
    }

    /**
     * Sort products by option
     */
    public void sortProducts(String sortOption) {
        WebElement dropdown = driver.findElement(sortDropdown);
        dropdown.click();
        By option = By.xpath(String.format("//option[text()='%s']", sortOption));
        clickElement(option);
    }

    /**
     * Check if specific product is displayed
     */
    public boolean isProductDisplayed(String productName) {
        By productLocator = By.xpath(String.format(productLinkByName, productName));
        return isElementDisplayed(productLocator);
    }
}