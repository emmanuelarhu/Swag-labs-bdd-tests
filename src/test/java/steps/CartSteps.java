package steps;

import io.cucumber.java.en.*;
import utils.TestContext;
import static org.junit.Assert.*;

/**
 * Step definitions for Shopping Cart functionality
 * Contains all cart-related step implementations
 */
public class CartSteps {

    private final TestContext testContext;

    public CartSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I click on the shopping cart")
    public void i_click_on_the_shopping_cart() {
        testContext.getProductsPage().clickCartIcon();
    }

    @Then("I should see {string} in the cart")
    public void i_should_see_item_in_the_cart(String itemName) {
        assertTrue("Cart page should be displayed",
                testContext.getCartPage().isCartPageDisplayed());
        assertTrue("Item should be in cart",
                testContext.getCartPage().isItemInCart(itemName));
    }

    @When("I remove {string} from cart")
    public void i_remove_item_from_cart(String itemName) {
        testContext.getCartPage().removeItemFromCart(itemName);
    }

    @Then("the cart should be empty")
    public void the_cart_should_be_empty() {
        assertTrue("Cart should be empty",
                testContext.getCartPage().isCartEmpty());
    }

    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        testContext.getCartPage().proceedToCheckout();
    }

    @When("I continue shopping")
    public void i_continue_shopping() {
        testContext.getCartPage().continueShopping();
    }

    @Then("I should be on the cart page")
    public void i_should_be_on_the_cart_page() {
        assertTrue("Should be on cart page",
                testContext.getCartPage().isCartPageDisplayed());
    }

    @Then("the cart should contain {int} items")
    public void the_cart_should_contain_items(Integer expectedCount) {
        int actualCount = testContext.getCartPage().getCartItemCount();
        assertEquals("Cart should contain correct number of items",
                expectedCount.intValue(), actualCount);
    }

    @Then("I should see the cart page header {string}")
    public void i_should_see_the_cart_page_header(String expectedHeader) {
        String actualHeader = testContext.getCartPage().getPageHeader();
        assertEquals("Cart page header should match", expectedHeader, actualHeader);
    }

    @When("I remove all items from cart")
    public void i_remove_all_items_from_cart() {
        testContext.getCartPage().removeAllItems();
    }

    @Then("the checkout button should be enabled")
    public void the_checkout_button_should_be_enabled() {
        assertTrue("Checkout button should be enabled",
                testContext.getCartPage().isCheckoutButtonEnabled());
    }

    @Then("the continue shopping button should be displayed")
    public void the_continue_shopping_button_should_be_displayed() {
        assertTrue("Continue shopping button should be displayed",
                testContext.getCartPage().isContinueShoppingButtonDisplayed());
    }

    @When("I verify the item {string} price is {string}")
    public void i_verify_the_item_price_is(String itemName, String expectedPrice) {
        String actualPrice = testContext.getCartPage().getItemPrice(itemName);
        assertEquals("Item price should match", expectedPrice, actualPrice);
    }

    @Then("I should not see {string} in the cart")
    public void i_should_not_see_item_in_the_cart(String itemName) {
        assertFalse("Item should not be in cart",
                testContext.getCartPage().isItemInCart(itemName));
    }

    @Given("I have {string} in my cart")
    public void i_have_item_in_my_cart(String itemName) {
        // This step assumes user is already logged in and on products page
        testContext.getProductsPage().addProductToCart(itemName);
        testContext.getProductsPage().clickCartIcon();
        assertTrue("Item should be in cart",
                testContext.getCartPage().isItemInCart(itemName));
    }

    @Given("my cart is empty")
    public void my_cart_is_empty() {
        if (!testContext.getCartPage().isCartPageDisplayed()) {
            testContext.getProductsPage().clickCartIcon();
        }

        if (!testContext.getCartPage().isCartEmpty()) {
            testContext.getCartPage().removeAllItems();
        }

        assertTrue("Cart should be empty",
                testContext.getCartPage().isCartEmpty());
    }
}