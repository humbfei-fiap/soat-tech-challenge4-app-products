package com.postechfiap_group130.techchallenge_fastfood.core.interfaces;

import java.util.List;
import java.util.UUID;


import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;

public interface DataSource {

    //Products
    ProductDto saveProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto);
    ProductDto findById(UUID id);
    List<ProductDto> getAll();
    List<ProductDto> getByCategory(Product.Category  category);
    Boolean existsByName(String name);
}
