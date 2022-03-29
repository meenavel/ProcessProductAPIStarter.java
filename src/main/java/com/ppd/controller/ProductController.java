package com.ppd.controller;

import com.ppd.model.ProdAPIResponse;
import com.ppd.entity.Product;
import com.ppd.service.ProductService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

@RequestMapping(path = "/ppd/v1/product", produces = MediaType.APPLICATION_JSON_VALUE)
//@Api(value="Product Management", protocols = "http")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    //To Add Product Details
    //@ApiOperation(value = "To add Product Details", response = ProdAPIResponse.class,code = 200)
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdAPIResponse> addProduct(@Valid @RequestBody Product product){

        return productService.addProduct(product);
    }

    //To Add Product Details
    //@ApiOperation(value = "To add Product Details by passing List of Products", response = ProdAPIResponse.class,code = 200)
    @PostMapping(path = "/addByList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdAPIResponse> addProductDetails(@RequestBody List<Product> productList){
        return productService.addProductDetails(productList);
    }

    //To Get Product by Id
    //@ApiOperation(value = "To Get Product Details by passing Product ID", response = ProdAPIResponse.class,code = 200)
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<ProdAPIResponse> getProductDetailsById(@PathVariable("id") int id){
        return productService.getProductDetailsById(id);
    }

    //To Get product by List of Id(s)
   // @ApiOperation(value = "To Get Product Details by passing List of Product ID(s)", response = ProdAPIResponse.class,code = 200)
    @PostMapping(path = "/getByIdList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdAPIResponse> getProductDetailsByIdList(@RequestBody List<Integer> IdList){
        return productService.getProductDetailsByIdList(IdList);
    }

    //To Get Product Details By Name
    //@ApiOperation(value = "To Get Product Details by passing name of the Product", response = ProdAPIResponse.class,code = 200)
    @GetMapping(path = "/getByName/{name}")
    public ResponseEntity<ProdAPIResponse> getProductDetailsByName(@PathVariable("name") String productName){
        return productService.getProductDetailsByName(productName);
    }

    //To Get All Product Details
    //@ApiOperation(value = "To Get All the Product Details", response = ProdAPIResponse.class,code = 200)
    @GetMapping(path = "/getAll")
    public ResponseEntity<ProdAPIResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    //To Update Product Details by multiple id(s)
    //@ApiOperation(value = "To Update Product Details", response = ProdAPIResponse.class,code = 200)
    @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdAPIResponse> updateDetails(@PathVariable ("id") int id, @RequestBody Product product){
        return productService.updateProductDetails(id,product);
    }

    //To Update Product Details
    //@ApiOperation(value = "To Update Product Details By passing List of Products", response = ProdAPIResponse.class,code = 200)
    @PostMapping(path = "/updateByIdList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProdAPIResponse> updateProductDetails(@RequestBody List<Product> productList){
        return productService.updateProductDetailsByList(productList);
    }

    //To Delete Product Details By multiple Id(s)
   // @ApiOperation(value = "To Delete Product Details By ID List", response = ProdAPIResponse.class,code = 200)
    @DeleteMapping(path="/deleteByIdList")
    public ResponseEntity<ProdAPIResponse> deleteProductDetails(@RequestBody List<Integer> idList){
        return productService.deleteproductDetails(idList);
    }

    //To Delete All products
    //@ApiOperation(value = "To Delete all the Products", response = ProdAPIResponse.class,code = 200)
    @DeleteMapping(path = "/deleteAll")
    public ResponseEntity<ProdAPIResponse> deleteAllProducts(){
        return productService.deleteAllproducts();
    }

}
