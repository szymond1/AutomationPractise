@qa
Feature: User registration
  In this file we are going to test user registration

  Background:
    Given User is on main registration page

  Scenario Outline: User use all correct data

    When User is using not existing email "<email>" in database
    And User click button Create an account
    And The action redirect to Personal data form
    And User Fill the form with all required data "<firstname>", "<lastname>", "<password>", "<address>", "<city>", "<zipcode>","<phone>", "<alias>"
    And User click button Register
    Then User is registered and is redirect to user account page "<name>"
    And Information about correct login is printed

    Examples:
      | firstname | lastname | password | address   | city   | zipcode | phone     | alias | email           | name          |  |
      | Szymon    | Dubiel   | password | Stanczyka | Krakow | 30126   | 724570120 | SD    | admin@gmail.com | Szymon Dubiel |  |

  Scenario Outline: User does not introduce lastname and password to register form

    When User is using not existing email "<email>" in database
    And User click button Create an account
    And The action redirect to Personal data form
    And User Fill the form without password and lastname "<firstname>", "<address>", "<city>", "<zipcode>","<phone>", "<alias>"
    And User click button Register
    Then User is not registered and information about failed attempt is printed "lastname is required.", "passwd is required."

    Examples:
      | firstname | address   | city   | zipcode | phone     | alias | email           |
      | Szymon    | Stanczyka | Krakow | 30126   | 724570120 | SD    | admin@gmail.com |


  Scenario: User use an incorrect email format in register form

    When User is using incorrect email format "dubiel.szymon"
    And User click button Create an account
    Then User is not directed to personal data form and information about failed attempt is printed "Invalid email address."

  Scenario: User use an incorrect email format in register form

    When User is using an email which exists in database "dubiel.szymon@gmail.com"
    And User click button Create an account
    Then User is not directed to personal data form and information about failed attempt is printed "An account using this email address has already been registered. Please enter a valid password or request a new one."


