Feature: Testing Amphibian

  Scenario: Getting all amphibians
    Given I have 2 amphibians
    When I request all amphibians
    Then The total number of amphibians is 2