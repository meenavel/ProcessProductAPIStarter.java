package com.ppd.model;

import com.ppd.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdAPIResponse {
    private int status;
    private String operation;
    private List<Product> productList;
    private double productAmount;
    private ErrorDetails error;
}
