@qa
Feature: Adding product to cart
  In this file we are going to test adding products to cart

  Background:
    Given User is on main application page
    And User is logging in with email "dubiel.szymon@gmail.com" and password "password"
    And User click button to Sign In
    And User is logged and is redirect to user account page

  Scenario: User add single cloth into cart

    When User is clicking on Woman Sector Button
    And Add first item to cart
    And Cart Layer is displayed
    Then Product is successfully added to cart

  Scenario: User add multiple clothes into cart

    When User is clicking on Woman Sector Button
    And Add first item to cart
    And Cart Layer is displayed
    And Secont item is added to cart
    And Cart Layer is displayed
    Then The list had 2 items inside


  Scenario: User remove single cloth from cart

    When User is clicking on Woman Sector Button
    And Add first item to cart
    And Cart Layer is displayed
    And Secont item is added to cart
    And Cart Layer is displayed
    And Click on Cart Button
    And Click on First Item Delete Button
    Then The first item is removed and the shopping list had 1 item inside


