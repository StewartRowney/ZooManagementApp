Feature: Testing Get Animals

  Scenario: Getting all animal
    Given I have 12 amphibians
    When I request all animals
    Then The total number of amphibians is correct

  Scenario Outline: Getting all animal types
    Given I have <amount> amphibians
    When I request all <animalType>
    Then The total number of amphibians is correct

    Examples:
      | amount | animalType |
      | 2      | amphibians |
      | 2      | fish       |
      | 2      | reptiles   |
      | 2      | mammals    |
      | 2      | birds      |
      | 2      | insects    |

  Scenario: Get animal by id
    Given I have an animal with id: 9b2d9232-9385-4707-965f-e5a90cbcfc88
    When I request animal by id
    Then I receive animal with name: Sally