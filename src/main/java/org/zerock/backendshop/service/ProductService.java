package org.zerock.backendshop.service;

import org.zerock.backendshop.model.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    void deleteProduct(Long id);

    List<Product> findAllProducts();

}
