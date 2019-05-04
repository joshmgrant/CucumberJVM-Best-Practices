Feature: Login as Invalid User

  Scenario: Login with Invalid Credentials
    Given I go to the login page
    When I login as Invalid / Invalid
    Then The login error is displayed
