package com.ppd.service;

import com.ppd.DAO.ProductDAO;
import com.ppd.entity.Product;
import com.ppd.model.ProdAPIResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static com.ppd.constants.ProcessProductConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PPDServiceTests {

    @Autowired
    ProductService service;

    @InjectMocks
    ProductService serviceIM;

    @Mock
    ProductDAO dao;

    @Test
    public void testAddProductEmpty(){
        Product product = new Product();
        ResponseEntity<ProdAPIResponse> response = service.addProduct(product);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getOperation(),OP_ADD);
        assertEquals(response.getBody().getError().getCode(),200);
        assertEquals(response.getStatusCodeValue(),400);
    }
    @Test
    public void testProductNameEmpty(){
        Product product = new Product();
        product.setProductName("");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        ResponseEntity<ProdAPIResponse> response = service.addProduct(product);
        System.out.println(response);
    }

    @Test
    public void addProductNameInvalid(){
        Product product = new Product();
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        ResponseEntity<ProdAPIResponse> response = service.addProduct(product);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getOperation(),OP_ADD);
        assertEquals(response.getBody().getError().getCode(),200);
        assertEquals(response.getStatusCodeValue(),400);
        assertEquals(response.getBody().getError().getDescription(),"|Product Name is Mandatory|");
    }

    @Test
    public void addProductDBError(){
        Product product = new Product();
        product.setProductName("iPhone10Pro128");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        Mockito.when(dao.save(product)).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.addProduct(product);
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_ADD, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    @Test
    public void addProductListEmpty(){
        List<Product> prodList = new ArrayList<>();
        ResponseEntity<ProdAPIResponse> response = service.addProductDetails(prodList);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getOperation(),OP_ADD);
        assertEquals(response.getBody().getError().getCode(),200);
        assertEquals(response.getBody().getError().getDescription(),"Product List Cannot be Empty.");
        assertEquals(response.getStatusCodeValue(),400);
    }

    @Test
    public void addProdListNameInvalid(){
        List<Product> prodList = new ArrayList<>();
        Product product = new Product();
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        prodList.add(product);
        ResponseEntity<ProdAPIResponse> response = service.addProductDetails(prodList);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getOperation(),OP_ADD);
        assertEquals(response.getBody().getError().getCode(),200);
        assertEquals(response.getStatusCodeValue(),400);
        assertEquals(response.getBody().getError().getDescription(),"|Product Name is Mandatory|");
    }

    @Test
    public void addProdListDescriptionInvalid() {
        List<Product> prodList = new ArrayList<>();
        Product product = new Product();
        product.setProductName("iPhone 10 Pro 128");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        prodList.add(product);
        ResponseEntity<ProdAPIResponse> response = service.addProductDetails(prodList);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getOperation(),OP_ADD);
        assertEquals(response.getBody().getError().getCode(),200);
        assertEquals(response.getStatusCodeValue(),400);
        assertEquals(response.getBody().getError().getDescription(),"|Product Description is Mandatory|");

    }

    @Test
    public void addProdListDBError(){
        List<Product> prodList = new ArrayList<>();
        Product product = new Product();
        product.setProductName("iPhone10Pro128");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        prodList.add(product);
        Mockito.when(dao.saveAll(prodList)).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.addProductDetails(prodList);
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_ADD, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    @Test
    public void getProdByIdInvalid(){
        int id = 0;
        ResponseEntity<ProdAPIResponse> response = service.getProductDetailsById(id);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getOperation(),OP_GET);
        assertEquals(response.getBody().getError().getCode(),100);
        assertEquals(response.getStatusCodeValue(),404);
        assertEquals(response.getBody().getError().getDescription(),"Product Id Cannot Be Found");
    }

    @Test
    public void getprodDetailsByIdDBError(){
        int id = 1;
        Mockito.when(dao.findById(id)).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.getProductDetailsById(id);
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_GET, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    @Test
    public void getProdByIDListInvalid(){
        List<Integer> idList = Arrays.asList(10,1000);
        ResponseEntity<ProdAPIResponse> response = service.getProductDetailsByIdList(idList);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getOperation(),OP_GET);
        assertEquals(response.getBody().getProductList().size(),0);
        assertEquals(response.getBody().getError().getCode(),100);
        assertEquals(response.getStatusCodeValue(),404);
        assertEquals(response.getBody().getError().getDescription(),"Record Not Found for [10, 1000]");

    }

    @Test
    public void getProdByIDListDBError(){
        List<Integer> idList = Arrays.asList(1,2,10,1000);
        Mockito.when(dao.getByIdCollection(idList)).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.getProductDetailsByIdList(idList);
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_GET, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    @Test
    public void getProdByNameInvalid(){
        String prodName = "samsung";
        ResponseEntity<ProdAPIResponse> response = service.getProductDetailsByName(prodName);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getOperation(),OP_GET);
        assertEquals(response.getBody().getProductList().size(),0);
        assertEquals(response.getBody().getError().getCode(),100);
        assertEquals(response.getStatusCodeValue(),404);
        assertEquals(response.getBody().getError().getDescription(),"No Records Found");
    }

    @Test
    public void getProdNameDBError(){
        Mockito.when(dao.getByName(Mockito.anyString())).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.getProductDetailsByName(Mockito.anyString());
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_GET, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    @Test
    public void updateProdIDInvalid(){
        Product product = new Product();
        product.setProductName("iPhone10Pro128");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        ResponseEntity<ProdAPIResponse> response = service.updateProductDetails(1000,product);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getOperation(),OP_UPD);
        assertEquals(response.getBody().getProductList(),null);
        assertEquals(response.getBody().getError().getCode(),100);
        assertEquals(response.getStatusCodeValue(),404);
        assertEquals(response.getBody().getError().getDescription(),"ID cannot Be Found");
    }

    @Test
    public void updateProdDBError(){
        Product product = new Product();
        product.setProductName("iPhone10Pro128");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        Mockito.when(dao.findById(1)).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.updateProductDetails(1,product);
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_UPD, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    @Test
    public void deleteByIDListInvalid(){
        List<Integer> idList = Arrays.asList(1000);
        ResponseEntity<ProdAPIResponse> response = service.deleteproductDetails(idList);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getOperation(),OP_REM);
        assertEquals(response.getBody().getProductList(),null);
        assertEquals(response.getBody().getError().getCode(),100);
        assertEquals(response.getStatusCodeValue(),404);
        assertEquals(response.getBody().getError().getDescription(),"ID Cannot Be Found");
    }

    @Test
    public void deleteByIDListDBError(){
        List<Integer> idList = Arrays.asList(1000);
        Mockito.when(serviceIM.deleteproductDetails(idList)).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.deleteproductDetails(idList);
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_REM, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    @Test
    public void deleteAllDBError(){
        Mockito.when(serviceIM.deleteAllproducts()).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.deleteAllproducts();
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_REM, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    @Test
    public void getAllDBError(){
        Mockito.when(serviceIM.getAllProducts()).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.getAllProducts();
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_GET, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

//    @Test
//    public void updateProdByListInvalidName(){
//        List<Product> prodList = new ArrayList<>();
//        Product product = new Product();
//        product.setProductId(1000);
//        product.setProductDescription("iPhone 10 Pro 128GB");
//        product.setProductAmount(1099.97);
//        product.setProductSoldCount(0);
//        prodList.add(product);
//        ResponseEntity<ProdAPIResponse> response =  service.updateProductDetailsByList(prodList);
//        System.out.println(response);
//        assertEquals(response.getBody().getStatus(),-1);
//        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
//        assertEquals(response.getBody().getOperation(),OP_UPD);
//        assertEquals(response.getBody().getError().getCode(),200);
//        assertEquals(response.getStatusCodeValue(),400);
//        assertEquals(response.getBody().getError().getDescription(),"|Product Name is Mandatory|");
//    }
    @Test
    public void updateProdListByEmpty(){
        List<Product> prodList = new ArrayList<>();
        ResponseEntity<ProdAPIResponse> response = service.updateProductDetailsByList(prodList);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getOperation(),OP_UPD);
        assertEquals(response.getBody().getError().getCode(),100);
        assertEquals(response.getStatusCodeValue(),400);
        assertEquals(response.getBody().getError().getDescription(),"Product List Cannot Be Empty");
    }

    @Test
    public void updateProdByListInvlaidID(){
        List<Product> prodList = new ArrayList<>();
        Product product = new Product();
        product.setProductId(100);
        product.setProductName("iPhone10Pro128");
        product.setProductDescription("iPhone 10 Pro 128GB");
        product.setProductAmount(1099.97);
        product.setProductSoldCount(0);
        prodList.add(product);
        Product prod = new Product();
        prod.setProductId(101);
        prod.setProductName("iPhone10Pro128");
        prod.setProductDescription("iPhone 10 Pro 128GB");
        prod.setProductAmount(1099.97);
        prod.setProductSoldCount(0);
        prodList.add(prod);
        ResponseEntity<ProdAPIResponse> response =  service.updateProductDetailsByList(prodList);
        System.out.println(response);
        assertEquals(response.getBody().getStatus(),-1);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getOperation(),OP_UPD);
        assertEquals(response.getBody().getProductList(),null);
        assertEquals(response.getBody().getError().getCode(),200);
        assertEquals(response.getStatusCodeValue(),404);
        assertEquals(response.getBody().getError().getDescription(),"One or more Product requested for update are not present. Please check");
    }

    @Test
    public void updateProdByListDBError(){
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
        Mockito.when(serviceIM.updateProductDetailsByList(prodList)).thenThrow(new IllegalArgumentException("Table Doesn't Exist"));
        ResponseEntity<ProdAPIResponse> response = serviceIM.updateProductDetailsByList(prodList);
        System.out.println(response);
        assertEquals(500, response.getBody().getError().getCode());
        assertEquals("Table Doesn't Exist", response.getBody().getError().getDescription());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(-1, response.getBody().getStatus());
        assertEquals(OP_UPD, response.getBody().getOperation());
        assertEquals(null, response.getBody().getProductList());
    }

    }
