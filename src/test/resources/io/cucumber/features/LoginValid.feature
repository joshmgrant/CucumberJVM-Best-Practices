Feature: Login as Valid User

  Scenario: Standard User
    Given I go to the login page
    When I enter the username "standard_user"
    When I enter the password "secret_sauce"
    And I click the submit button
    Then The url contains "inventory"

