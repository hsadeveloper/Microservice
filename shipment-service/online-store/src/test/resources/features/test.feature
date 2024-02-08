Feature: Your API Feature

  Scenario: Make a GET request to the greet endpoint
    Given the API is running at http://localhost:8080
    When a GET request is made to the endpoint /product/greet
    Then the response should be "Hello, World!"
