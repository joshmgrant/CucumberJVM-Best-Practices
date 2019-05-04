Feature: Login as Valid User

  Scenario: Standard User
    Given I go to the login page
    When I login as standard_user / secret_sauce
    Then The url contains "inventory"

