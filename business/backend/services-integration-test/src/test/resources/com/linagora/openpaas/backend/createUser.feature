Feature: create a new user

  create a new user
  login, mail is unique

  Scenario: create a new user
    Given we have a number of user in the system
    When we create one user which does not exist
    Then there is one more user in the system