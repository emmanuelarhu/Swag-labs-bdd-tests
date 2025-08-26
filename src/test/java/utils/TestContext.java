package utils;

import pages.*;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;
import java.util.Map;

/**
 * Test Context class to share data between step definitions
 * Uses dependency injection with PicoContainer
 */
public class TestContext {

    private WebDriver driver;
    private Map<String, Object> testData;

    // Page Objects
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    public TestContext() {
        this.testData = new HashMap<>();
    }

    /**
     * Initialize all page objects
     */
    public void initializePages() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
        this.productsPage = new ProductsPage(driver);
        this.cartPage = new CartPage(driver);
        this.checkoutPage = new CheckoutPage(driver);
    }

    // Getters for Page Objects
    public LoginPage getLoginPage() {
        if (loginPage == null) {
            initializePages();
        }
        return loginPage;
    }

    public ProductsPage getProductsPage() {
        if (productsPage == null) {
            initializePages();
        }
        return productsPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            initializePages();
        }
        return cartPage;
    }

    public CheckoutPage getCheckoutPage() {
        if (checkoutPage == null) {
            initializePages();
        }
        return checkoutPage;
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    // Test Data Management
    public void setTestData(String key, Object value) {
        testData.put(key, value);
    }

    public Object getTestData(String key) {
        return testData.get(key);
    }

    public String getTestDataAsString(String key) {
        Object value = testData.get(key);
        return value != null ? value.toString() : null;
    }

    public Integer getTestDataAsInteger(String key) {
        Object value = testData.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public Boolean getTestDataAsBoolean(String key) {
        Object value = testData.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return null;
    }

    public void clearTestData() {
        testData.clear();
    }

    public boolean hasTestData(String key) {
        return testData.containsKey(key);
    }

    // Common test data keys
    public static final String CURRENT_USER = "current_user";
    public static final String CURRENT_PRODUCT = "current_product";
    public static final String CART_ITEMS = "cart_items";
    public static final String ORDER_TOTAL = "order_total";
    public static final String CHECKOUT_INFO = "checkout_info";

    // User credentials
    public static class UserCredentials {
        public static final String STANDARD_USER = "standard_user";
        public static final String LOCKED_OUT_USER = "locked_out_user";
        public static final String PROBLEM_USER = "problem_user";
        public static final String PERFORMANCE_GLITCH_USER = "performance_glitch_user";
        public static final String VISUAL_USER = "visual_user";
        public static final String SECRET_SAUCE = "secret_sauce";
    }

    // Test data for checkout
    public static class CheckoutData {
        public static final String FIRST_NAME = "John";
        public static final String LAST_NAME = "Smith";
        public static final String POSTAL_CODE = "233";
    }
}