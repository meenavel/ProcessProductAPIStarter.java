package com.ppd.controller;

import com.ppd.entity.Product;
import com.ppd.model.ProdAPIResponse;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ppd.constants.ProcessProductConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PPDControllerTests {

    @Autowired
    ProductController controller;

    @Order(1)
    @Test
    public void testAddProduct(){
        Product product = new Product();
        product.setProductName("iPhone10Pro128");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        ResponseEntity<ProdAPIResponse> response = controller.addProduct(product);
        System.out.println(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_ADD);
    }

    @Order(2)
    @Test
    public void testAddProductList(){
        List<Product> prodList = new ArrayList<>();
        Product product = new Product();
        product.setProductName("iPhone10Pro128");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        prodList.add(product);
        ResponseEntity<ProdAPIResponse> response = controller.addProductDetails(prodList);
        System.out.println(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_ADD);
    }

    @Order(3)
    @Test
    public void testGetById(){
        int id = 1;
        ResponseEntity<ProdAPIResponse> response =  controller.getProductDetailsById(id);
        System.out.println(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_GET);
        assertEquals(response.getBody().getProductList().get(0).getProductName(),"iPhone10Pro128");
    }
    @Order(4)
    @Test
    public void testGetByIdList() {
        List<Integer> idList = Arrays.asList(1, 2);
        ResponseEntity<ProdAPIResponse> response = controller.getProductDetailsByIdList(idList);
        System.out.println(response);
        assertEquals(response.getBody().getProductList().get(0).getProductName(), "iPhone10Pro128");
        assertEquals(response.getBody().getProductList().size(), 2);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_GET);
    }

    @Order(5)
    @Test
    public void testGetByProdName(){
        String prodName = "iph";
        ResponseEntity<ProdAPIResponse> response = controller.getProductDetailsByName(prodName);
        System.out.println(response);
        assertEquals(response.getBody().getProductList().get(0).getProductName(), "iPhone10Pro128");
        assertEquals(response.getBody().getProductList().size(), 2);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_GET);
    }

    @Order(6)
    @Test
    public void testGetAllProd(){
        ResponseEntity<ProdAPIResponse> response = controller.getAllProducts();
        System.out.println(response);
        assertEquals(response.getBody().getProductList().get(0).getProductName(), "iPhone10Pro128");
        assertEquals(response.getBody().getProductList().size(), 2);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_GET);
    }

    @Order(7)
    @Test
    public void updateProdById(){
        int id = 1;
        Product product = new Product();
        product.setProductName("iPhone10Pro256");
        product.setProductDescription("iPhone 10 Pro 256GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        ResponseEntity<ProdAPIResponse> response = controller.updateDetails(1,product);
        System.out.println(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_UPD);
        assertEquals(response.getBody().getProductList().size(),1);
    }

    @Order(8)
    @Test
    public void updateProdByIDList(){
        List<Product> prodList = new ArrayList<>();
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("iPhone10Pro128");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        prodList.add(product);
        Product prod = new Product();
        prod.setProductId(2);
        prod.setProductName("iPhone10Pro128");
        prod.setProductDescription("iPhone 10 Pro 128GB");
        prod.setProductAmount(1099.97);
        prod.setProductSoldCount(0);
        prodList.add(prod);
        ResponseEntity<ProdAPIResponse> response =  controller.updateProductDetails(prodList);
        System.out.println(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_UPD);
        assertEquals(response.getBody().getProductList().size(),2);
    }

    @Order(9)
    @Test
    public void testDeleteByIDList(){
        List<Integer> idList = Arrays.asList(1,2);
        ResponseEntity<ProdAPIResponse> response = controller.deleteProductDetails(idList);
        System.out.println(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_REM);
    }

    @Order(10)
    @Test
    public void testDeleteAll(){
        ResponseEntity<ProdAPIResponse> response = controller.deleteAllProducts();
        System.out.println(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(response.getStatusCodeValue(),200);
        assertEquals(response.getBody().getStatus(),0);
        assertEquals(response.getBody().getOperation(),OP_REM);
        assertEquals(response.getBody().getProductList(),null);
    }




}
