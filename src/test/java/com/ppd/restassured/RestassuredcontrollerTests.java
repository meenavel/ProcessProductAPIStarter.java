package com.ppd.restassured;

import com.ppd.entity.Product;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
//import org.json.JSONObject;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestassuredcontrollerTests {

    @BeforeEach
    public void init() {
//        RequestSpecification request = RestAssured.given();
        RestAssured.baseURI = "http://localhost";
        System.out.println(baseURI);
    }

    @Test
    public void testbase() {
        System.out.println(baseURI);
    }

    @Test
    public void testAddProd() {
        //RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("productName", "iPhone10Pro128");
        requestParams.put("productDescription", "iPhone 10 Pro 128GB");
        requestParams.put("productAmount", "1099.97");
        requestParams.put("productSoldCount", "0");
        given()
                .body(requestParams.toJSONString()).contentType("application/json")
                .when()
                .post(baseURI + ":8082/ppd/v1/product" + "/add")
                .then().statusCode(200).log().all();

//        request.header("Content-Type", "application/json"); // Add the Json to the body of the request
//        request.body(requestParams.toString(1)); // Post the request and check the response
//        System.out.println(requestParams);
//        System.out.println(requestParams.toString());
//        Response response = request.post(baseURI + "/add");
//        System.out.println("The status received: " + response.statusLine());
//        given()
//                .body(requestParams.toString())
//                .when().post(baseURI,"/add")
//                .then().statusCode(200);

    }

    @Test
    public void testAddListProd(){
        Map<String,Object> requestOne = new HashMap<String,Object>();
        requestOne.put("productId","2");
        requestOne.put("productName","iPhone11Pro256");
        requestOne.put("productDescription","iPhone 11 Pro 256GB");
        requestOne.put("productAmount","1178.56");
        requestOne.put("productSoldCount","0");
        Map<String,Object> requestTwo = new HashMap<String,Object>();
        requestTwo.put("productId","3");
        requestTwo.put("productName","iPhone12Pro512");
        requestTwo.put("productDescription","iPhone 12 Pro 512GB");
        requestTwo.put("productAmount","1399.78");
        requestTwo.put("productSoldCount","0");
        List<Map<String,Object>> jsonArrayPayload = new ArrayList<>();
        jsonArrayPayload.add(requestOne);
        jsonArrayPayload.add(requestTwo);
        given().body(jsonArrayPayload).contentType("application/json").when()
                .post(baseURI  +":8082/ppd/v1/product/addByList")
                .then().statusCode(200).log().all();

    }
    @Test
    public void testGetAllProd() {
        given().when()
                .get(baseURI + ":8082/ppd/v1/product/getAll")
                .then()
                .statusCode(200).log().all();
    }

    @Test
    public void testGetByID() {
        int id = 1;
        Response response = RestAssured.get(baseURI + ":8082/ppd/v1/product/get/{id}", id);
        System.out.println(response);
        if (response.getStatusCode() != 404) {
            RestAssured.given().when()
                    .get(baseURI + ":8082/ppd/v1/product/get/{id}", id)
                    .then()
                    .statusCode(200).body("productList.productId", hasItems(1),
                            "productList.productName", hasItems("iPhone11Pro256"));
        } else {
            assertEquals(response.getStatusCode(), 200);
        }
    }

    @Test
    public void testUpdateProduct(){
        int id = 1;
        Response response = RestAssured.get(baseURI+":8082/ppd/v1/product/get/{id}",id);
        System.out.println(response);
        if(response.getStatusCode() != 404){
            JSONObject request = new JSONObject();
            request.put("productName","iPhone");
            request.put("productDescription","iPhone11");
            request.put("productAmount","1500");
            request.put("productSoldCount","0");
            RestAssured.given()
                    .body(request.toJSONString()).contentType("application/json").when()
                    .put(baseURI +":8082/ppd/v1/product/update/1").then()
                    .statusCode(200);
        }
        else{
            assertEquals(response.getStatusCode(),404);
        }
    }

    @Test
    public void testUpdateProdFailure(){
        int id = 1000;
        Response response = RestAssured.get(baseURI+":8082/ppd/v1/product/get/{id}",id);
        if(response.getStatusCode() == 404){
            JSONObject request = new JSONObject();
            request.put("productName","iPhone");
            request.put("productDescription","iPhone11");
            request.put("productAmount","1500");
            request.put("productSoldCount","0");
            RestAssured.given()
                    .body(request.toJSONString()).contentType("application/json").when()
                    .put(baseURI +":8082/ppd/v1/product/update/1000").then().statusCode(404);
        }
    }

    @Test
    public void testUpdateProdList(){
        Map<String,Object> requestOne = new HashMap<String,Object>();
        requestOne.put("productId","2");
        requestOne.put("productName","iPhone11Pro256");
        requestOne.put("productDescription","iPhone 11 Pro 256GB");
        requestOne.put("productAmount","1178.56");
        requestOne.put("productSoldCount","0");
        Map<String,Object> requestTwo = new HashMap<String,Object>();
        requestTwo.put("productId","3");
        requestTwo.put("productName","iPhone12Pro512");
        requestTwo.put("productDescription","iPhone 12 Pro 512GB");
        requestTwo.put("productAmount","1399.78");
        requestTwo.put("productSoldCount","0");
        List<Map<String,Object>> jsonArrayPayload = new ArrayList<>();
        jsonArrayPayload.add(requestOne);
        jsonArrayPayload.add(requestTwo);
        given().body(jsonArrayPayload).contentType("application/json").when()
                .post(baseURI  +":8082/ppd/v1/product/updateByIdList")
                .then().statusCode(200).body("productList.productId", hasItems(1),
                        "productList.productName", hasItems("iPhone11Pro256"));

    }

    @Test
    public void testDeleteAll(){
        given().contentType("application/json")
                .when().delete(baseURI+":8082/ppd/v1/product/deleteAll")
                .then().statusCode(200).log().all();

    }
}
