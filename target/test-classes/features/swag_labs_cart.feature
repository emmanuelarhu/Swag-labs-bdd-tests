@swag_labs
Feature: Swag Labs Cart Test Flow
  As a customer
  I want to browse and purchase products from Swag Labs

  Background:
    Given I am on the Swag Labs login page

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
      | standard_user | secret_sauce  | Sauce Labs Bike Light |