Feature: Inventory List

  Scenario: Verify First Item
    Given I go to the inventory page
    When I get the first inventory item
    Then The inventory item is a backpack