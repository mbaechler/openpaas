Feature: create a new user

  create a new user
  login is unique

  Background:
  	Given the following users exist
      | login							| email							| firstname	| lastname	|
      | jaures@parti-socialiste.fr		| jaures@parti-socialiste.fr	| Jean		| Jaures	|
      | lean.blum@front-populaire.fr	| lean.blum@front-populaire.fr	| Léon		| Blum		|

  Scenario: create a new user
    When I create a user with login and email "roger.salengro@front-populaire.fr"
    Then there is one more user in the system
    
  Scenario: create a new user with an already used login
    When I create a user with login and email "jaures@parti-socialiste.fr"
    Then there is an error saying "jean.jaures@parti-socialiste.fr" login is already used
    And there is no new user in the system
   
  Scenario: create a new user with an already used email
    When I create a user with login "devil@parti-socialiste.fr" and email "jaures@parti-socialiste.fr"
	Then there is one more user in the system