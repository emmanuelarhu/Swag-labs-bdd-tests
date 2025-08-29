@swag_labs
Feature: Swag Labs Product Test Flow
  As a customer
  I want to browse and purchase products from Swag Labs
  Background:
    Given I am on the Swag Labs login page

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
      | standard_user | secret_sauce  | Sauce Labs Bike Light | $9.99  | 1         |