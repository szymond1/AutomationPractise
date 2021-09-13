@qa
Feature: Application Login
  In this file we are going to test login user to application

  Background:
    Given User is on loggin page

  Scenario Outline: User use valid data

    And User with email "<email>" and password "<password>" exist in database
    When User is using email"<email>" and password "<password>"
    And User click button Sign In
    Then User is logged and is redirect to user account page "<name>"
    And Information about correct login is printed

    Examples:
      | email                   | password | name          |
      | admin@gmail.com         | admin    | admin admin   |
      | dubiel.szymon@gmail.com | password | Szymon Dubiel |


  Scenario: User has not provided a password

    And User with email "dubiel.szymon@gmail.com" and password "password" exist in database
    When User is using email "dubiel.szymon@gmail.com"
    And User click button Sign In
    Then User is not logged and information about failed attempt is printed "Password is required."



  Scenario: User use an email which does not exist in database

    And User with email "dubiel.szymon24565@gmail.com" and password "password" not exist in database
    When User is using email"dubiel.szymon24565@gmail.com" and password "password"
    And User click button Sign In
    Then User is not logged and information about failed attempt is printed "Authentication failed."


