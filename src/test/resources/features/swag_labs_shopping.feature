@swag_labs @e2e_flow
Feature: Swag Labs End To End Test Flow
  As a customer
  I want to browse and purchase products from Swag Labs
  So that I can complete my shopping successfully

  Background:
    Given I am on the Swag Labs login page

  @smoke @login
  Scenario Outline: User can login with valid credentials
    When I login with username "<username>" and password "<password>"
    Then I should be redirected to the products page
    And I should see the products page header "<expectedoutcome>"

    Examples:
    | username      | password      | expectedoutcome    |
    | standard_user | secret_sauce  | Products           |

  @critical @shopping_flow
  Scenario Outline: Complete shopping flow - Add item to cart and checkout
    Given I login with username "<username>" and password "<password>"
    And I am on the products page
    When I add "Sauce Labs Backpack" to cart
    And I add "Sauce Labs Bike Light" to cart
    Then the cart badge should show "2" items
    When I click on the shopping cart
    Then I should see "Sauce Labs Backpack" in the cart
    And I should see "Sauce Labs Bike Light" in the cart
    When I proceed to checkout
    And I fill in checkout information with valid dta:
      | firstName | lastName | postalCode |
      | Emmanuel  | Arhu     | 0242       |
    And I continue to checkout overview
    Then I should see the checkout overview page
    And I should see "Sauce Labs Backpack" in the order summary
    And I should see "Sauce Labs Bike Light" in the order summary
    When I finish the order
    Then I should see the order confirmation page
    And I should see "Thank you for your order!" message

    Examples:
      | username      | password      |
      | standard_user | secret_sauce  |

  @negative @login
  Scenario Outline: User cannot login with invalid credentials
    When I login with username "<username>" and password "<password>"
    Then I should see an error message "<errorMessage>"
    And I should remain on the login page

    Examples:
    | username      | password        | errorMessage                                                             |
    | invalid_user  | wrong_password  | Epic sadface: Username and password do not match any user in this service|

  @cart_operations
  Scenario Outline: User can remove items from cart
    Given I login with username "<username>" and password "<password>"
    And I am on the products page
    When I add "<productName>" to cart
    And I click on the shopping cart
    Then I should see "<productName>" in the cart
    When I remove "<productName>" from cart
    Then the cart should be empty
    And the cart badge should not be visible

    Examples:
      | username      | password      | productName           |
      | standard_user | secret_sauce  | Sauce Labs Backpack   |

  @product_details
  Scenario Outline: User can view product details
    Given I login with username "<username>" and password "<password>"
    And I am on the products page
    When I click on product "<productName>"
    Then I should see the product details page
    And I should see the product name "<productName>"
    And I should see the product price "<amount>"
    When I add the product to cart from details page
    Then the cart badge should show "<cartCount>" items

    Examples:
      | username      | password      | productName           | amount | cartCount |
      | standard_user | secret_sauce  | Sauce Labs Backpack   | $29.99 | 1         |