Feature: Login with Invalid Credentials

  Scenario: Blank Values
    Given I go to the login page
    When I login with blank credentials
    Then The login error is displayed

