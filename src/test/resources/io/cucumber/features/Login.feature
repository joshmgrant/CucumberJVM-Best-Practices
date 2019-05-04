Feature: Login

  Scenario: Login as users
    Given I open chrome in Sauce Labs
    When I go to the login page
    When I enter the username "Invalid"
    And I enter the password "invalid"
    And I click the submit button
    Then The login error is displayed
    When I enter the username "standard_user"
    When I enter the password "secret_sauce"
    And I click the submit button
    Then The url contains "inventory"
    Then The browser is closed
