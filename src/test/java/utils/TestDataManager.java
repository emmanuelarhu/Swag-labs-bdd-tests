package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Test Data Manager - Handles external test data configuration
 */
public class TestDataManager {

    private static Properties properties;
    private static TestDataManager instance;

    private TestDataManager() {
        loadProperties();
    }

    public static TestDataManager getInstance() {
        if (instance == null) {
            instance = new TestDataManager();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("test-data.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("test-data.properties file not found in resources");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test-data.properties", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    // User Credential Methods
    public String getStandardUsername() {
        return getProperty("user.standard.username");
    }

    public String getStandardPassword() {
        return getProperty("user.standard.password");
    }

    public String getLockedUsername() {
        return getProperty("user.locked.username");
    }

    public String getLockedPassword() {
        return getProperty("user.locked.password");
    }

    public String getInvalidUsername() {
        return getProperty("user.invalid.username");
    }

    public String getInvalidPassword() {
        return getProperty("user.invalid.password");
    }

    // Product Methods
    public String getBackpackName() {
        return getProperty("product.backpack");
    }

    public String getBikeLightName() {
        return getProperty("product.bikelight");
    }

    public String getBoltTshirtName() {
        return getProperty("product.bolt.tshirt");
    }

    public String getFleeceJacketName() {
        return getProperty("product.fleece.jacket");
    }

    public String getOnesieName() {
        return getProperty("product.onesie");
    }

    public String getRedTshirtName() {
        return getProperty("product.red.tshirt");
    }

    // Product Price Methods
    public String getBackpackPrice() {
        return getProperty("price.backpack");
    }

    public String getBikeLightPrice() {
        return getProperty("price.bikelight");
    }

    // Checkout Information Methods
    public String getCheckoutFirstName() {
        return getProperty("checkout.firstname");
    }

    public String getCheckoutLastName() {
        return getProperty("checkout.lastname");
    }

    public String getCheckoutPostalCode() {
        return getProperty("checkout.postalcode");
    }

    // Message Methods
    public String getLoginErrorMessage() {
        return getProperty("message.login.error");
    }

    public String getOrderSuccessMessage() {
        return getProperty("message.order.success");
    }

    public String getFirstNameRequiredMessage() {
        return getProperty("message.checkout.firstname.required");
    }

    // Page Header Methods
    public String getProductsHeader() {
        return getProperty("header.products");
    }

    public String getCartHeader() {
        return getProperty("header.cart");
    }

    public String getCheckoutHeader() {
        return getProperty("header.checkout");
    }
}