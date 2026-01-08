package com.postechfiap_group130.techchallenge_fastfood.core.interfaces;

import java.util.List;
import java.util.UUID;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;

public interface IProductGateway {
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    Product findById(UUID id);
    List<Product> findAll();
    List<Product> findAllByCategory(Product.Category category);
    Boolean existsByName(String name);
}
