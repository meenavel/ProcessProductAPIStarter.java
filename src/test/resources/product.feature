Feature: Test CRUD Operations in Product REST API testing

  Scenario: Add Products
    Given A product to be Added
    When I try to add a product
    Then Product was added with success response

    Scenario: Add List of Products
      Given List of Products to be added
      When I try to add List of Products
      Then Products are added successfully with success response

      Scenario: Get Product Details By ID
        Given
        When I try to search for the product by ID
        Then Product was searched with success response

        Scenario: Get All Products
          Given
          When I try to fetch all product details
          Then Products are returned with valid success response

          Scenario: Get Product Details By Name
            Given
            When I try to fetch Product details By Name
            Then Product with matching name returned with success response

            Scenario: Get Product Details By Name
              Given
              When I try to fetch Product details by Invalid Name
              Then returned a Not Found Response

              Scenario: To Update Product Details
                Given A Product to be Updated
                When I try to Update Product details
                Then Product updated with success response

                Scenario: To Delete Products
                  Given
                  When I try to Delete all Products
                  Then Products deleted with success response