@swag_labs
Feature: Swag Labs Login
  As a customer
  I want to login to Swag Labs
  So that I can access the products

  @smoke @login
  Scenario Outline: User can login with valid credentials
    Given I am on the Swag Labs login page
    When I login with username "<username>" and password "<password>"
    Then I should be redirected to the products page
    And I should see the products page header "Products"

    Examples:
    | username        | password      |
    | standard_user   | secret_sauce  |
    | visual_user     | secret_sauce  |