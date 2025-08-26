@swag_labs @e2e_flow
Feature: Swag Labs Shopping Flow
  As a customer
  I want to browse and purchase products from Swag Labs
  So that I can complete my shopping successfully

  Background:
    Given I am on the Swag Labs login page

  @smoke @login
  Scenario: User can login with valid credentials
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be redirected to the products page
    And I should see the products page header "Products"

  @critical @shopping_flow
  Scenario: Complete shopping flow - Add item to cart and checkout
    Given I login with username "standard_user" and password "secret_sauce"
    And I am on the products page
    When I add "Sauce Labs Backpack" to cart
    And I add "Sauce Labs Bike Light" to cart
    Then the cart badge should show "2" items
    When I click on the shopping cart
    Then I should see "Sauce Labs Backpack" in the cart
    And I should see "Sauce Labs Bike Light" in the cart
    When I proceed to checkout
    And I fill in checkout information:
      | firstName | lastName | postalCode |
      | John      | Smith    | 233        |
    And I continue to checkout overview
    Then I should see the checkout overview page
    And I should see "Sauce Labs Backpack" in the order summary
    And I should see "Sauce Labs Bike Light" in the order summary
    When I finish the order
    Then I should see the order confirmation page
    And I should see "Thank you for your order!" message

  @negative @login
  Scenario: User cannot login with invalid credentials
    When I login with username "invalid_user" and password "wrong_password"
    Then I should see an error message "Epic sadface: Username and password do not match any user in this service"
    And I should remain on the login page

  @cart_operations
  Scenario: User can remove items from cart
    Given I login with username "standard_user" and password "secret_sauce"
    And I am on the products page
    When I add "Sauce Labs Backpack" to cart
    And I click on the shopping cart
    Then I should see "Sauce Labs Backpack" in the cart
    When I remove "Sauce Labs Backpack" from cart
    Then the cart should be empty
    And the cart badge should not be visible

  @product_details
  Scenario: User can view product details
    Given I login with username "standard_user" and password "secret_sauce"
    And I am on the products page
    When I click on product "Sauce Labs Backpack"
    Then I should see the product details page
    And I should see the product name "Sauce Labs Backpack"
    And I should see the product price "$29.99"
    When I add the product to cart from details page
    Then the cart badge should show "1" items