Feature: Login as Invalid User

  Scenario: Login with Invalid Credentials
    Given I open chrome in Sauce Labs
    When I go to the login page
    When I enter the username "Invalid"
    And I enter the password "invalid"
    And I click the submit button
    Then The login error is displayed
    Then The browser is closed
