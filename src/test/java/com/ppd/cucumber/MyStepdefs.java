package com.ppd.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@SpringBootTest
public class MyStepdefs {

    Logger logger = LoggerFactory.getLogger(MyStepdefs.class);

    private static final String BASE_URL = "http://localhost:8082/ppd/v1/product";

    RequestSpecification request;
    Response response;
    int statusCode;

    //Add Product
    @Given("A product to be Added")
    public void aProductToBeAdded() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(BASE_URL);
        request = RestAssured.given();
        request.header("Content-Type","application/json");

    }

    @When("I try to add a product")
    public void iTryToAddAProduct() {
        Response response = request.get("get/1");
        statusCode = response.getStatusCode();
        System.out.println(statusCode);
    }

    @Then("Product was added with success response")
    public void productWasAddedWithSuccessResponse() {
        if(statusCode == 404){
            JSONObject requestParams = new JSONObject();
            requestParams.put("productName", "iPhone10Pro128");
            requestParams.put("productDescription", "iPhone 10 Pro 128GB");
            requestParams.put("productAmount", "1099.97");
            requestParams.put("productSoldCount", "0");
            response = request.body(requestParams).post("/add");
            assertEquals(response.getStatusCode(), 200);
        }
    }

    @Given("List of Products to be added")
    public void listOfProductsToBeAdded() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(BASE_URL);
        request = RestAssured.given();
        request.header("Content-Type","application/json");
    }

    @When("I try to add List of Products")
    public void iTryToAddListOfProducts() {
        Map<String,Object> requestOne = new HashMap<String,Object>();
//        requestOne.put("productId","2");
        requestOne.put("productName","iPhone11Pro256");
        requestOne.put("productDescription","iPhone 11 Pro 256GB");
        requestOne.put("productAmount","1178.56");
        requestOne.put("productSoldCount","0");
        Map<String,Object> requestTwo = new HashMap<String,Object>();
//        requestTwo.put("productId","3");
        requestTwo.put("productName","iPhone12Pro512");
        requestTwo.put("productDescription","iPhone 12 Pro 512GB");
        requestTwo.put("productAmount","1399.78");
        requestTwo.put("productSoldCount","0");
        List<Map<String,Object>> jsonArrayPayload = new ArrayList<>();
        jsonArrayPayload.add(requestOne);
        jsonArrayPayload.add(requestTwo);
        response = request.body(jsonArrayPayload).post("/addByList");
        System.out.println(response.getStatusCode());
    }

    @Then("Products are added successfully with success response")
    public void productsAreAddedSuccessfullyWithSuccessResponse() {
        assertEquals(response.getStatusCode(),200);
    }

    @When("I try to search for the product by ID")
    public void iTryToSearchForTheProductByID() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        response = request.get("/get/1");
    }

    @Then("Product was searched with success response")
    public void productWasSearchedWithSuccessResponse() {
        assertEquals(response.getStatusCode(),200);
    }


    @When("I try to fetch all product details")
    public void iTryToFetchAllProductDetails() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        response = request.get("/getAll");
    }

    @Then("Products are returned with valid success response")
    public void productsAreReturnedWithValidSuccessResponse() {
        assertEquals(response.getStatusCode(),200);
    }

    @When("I try to fetch Product details By Name")
    public void iTryToFetchProductDetailsByName() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        response = request.get("/getByName/iph");
    }

    @Then("Product with matching name returned with success response")
    public void productWithMatchingNameReturnedWithSuccessResponse() {
        assertEquals(response.getStatusCode(),200);
    }

    @When("I try to fetch Product details by Invalid Name")
    public void iTryToFetchProductDetailsByInvalidName() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        response = request.get("/getByName/abc");
    }

    @Then("returned a Not Found Response")
    public void returnedANotFoundResponse() {
        assertEquals(response.getStatusCode(),404);
    }

    @Given("A Product to be Updated")
    public void aProductToBeUpdated() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(BASE_URL);
        request = RestAssured.given();
        request.header("Content-Type","application/json");

    }

    @When("I try to Update Product details")
    public void iTryToUpdateProductDetails() {
        JSONObject requestParam = new JSONObject();
        requestParam.put("productName","iPhone");
        requestParam.put("productDescription","iPhone11");
        requestParam.put("productAmount","1500");
        requestParam.put("productSoldCount","0");
        response = request.body(requestParam).put("/update/1");
        System.out.println(response.getStatusCode());
    }

    @Then("Product updated with success response")
    public void productUpdatedWithSuccessResponse() {
        assertEquals(response.getStatusCode(),200);
    }

    @When("I try to Delete all Products")
    public void iTryToDeleteAllProducts() {
        RestAssured.baseURI = BASE_URL;
        System.out.println(BASE_URL);
        request = RestAssured.given();
        response = request.delete("/deleteAll");

    }

    @Then("Products deleted with success response")
    public void productsDeletedWithSuccessResponse() {
        assertEquals(response.getStatusCode(),200);
    }
}
