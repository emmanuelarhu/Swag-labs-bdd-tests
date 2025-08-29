@swag_labs
Feature: Swag Labs Invalid Login Flow
  As a customer
  I want to browse and purchase products from Swag Labs
  So that I can complete my shopping successfully

  Background:
    Given I am on the Swag Labs login page


  @negative @login @negative_login
Scenario Outline: User cannot login with invalid credentials
When I login with username "<username>" and password "<password>"
Then I should see an error message "<errorMessage>"
And I should remain on the login page

Examples:
| username      | password        | errorMessage                                                             |
| invalid_user  | wrong_password  | Epic sadface: Username and password do not match any user in this service|
