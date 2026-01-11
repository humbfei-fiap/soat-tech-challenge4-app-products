Feature: Product creation validation
  The system must validate product fields before creating a new product.

  Scenario: Successfully create a valid product
    Given a product name "Cheeseburger"
    And a product description "Delicious grilled cheeseburger"
    And a product price of 15.90
    And a product category "LANCHE"
    When the product is created
    Then the product should be created successfully

  Scenario: Fail to create product with empty name
    Given a product name ""
    And a product description "Some description"
    And a product price of 10.00
    And a product category "BEBIDA"
    When the product is created
    Then a validation error should occur with message "Product name cannot be null or empty"

  Scenario: Fail to create product with name longer than 100 characters
    Given a product name longer than 100 characters
    And a product description "Some description"
    And a product price of 10.00
    And a product category "ACOMPANHAMENTO"
    When the product is created
    Then a validation error should occur with message "Product name is too long. Max length is 100"

  Scenario: Fail to create product with empty description
    Given a product name "Soda"
    And a product description ""
    And a product price of 5.00
    And a product category "BEBIDA"
    When the product is created
    Then a validation error should occur with message "Product description cannot be null or empty"

  Scenario: Fail to create product with null price
    Given a product name "Ice Cream"
    And a product description "Chocolate ice cream"
    And a null product price
    And a product category "SOBREMESA"
    When the product is created
    Then a validation error should occur with message "Product price must be not null"

  Scenario: Fail to create product with zero or negative price
    Given a product name "Fries"
    And a product description "Crispy fries"
    And a product price of -3.00
    And a product category "ACOMPANHAMENTO"
    When the product is created
    Then a validation error should occur with message "Product price must be greater than 0"

  Scenario: Fail to create product with null category
    Given a product name "Milkshake"
    And a product description "Vanilla milkshake"
    And a product price of 12.00
    And a null product category
    When the product is created
    Then a validation error should occur with message "Category cannot be null"