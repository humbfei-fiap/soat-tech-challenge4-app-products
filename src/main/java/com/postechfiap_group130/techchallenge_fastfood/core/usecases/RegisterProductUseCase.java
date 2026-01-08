package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;

import java.util.UUID;

public record RegisterProductUseCase(ProductGateway productGateway) {

    public Product execute(ProductDto productDto) {
        Product product = new Product(UUID.randomUUID(), productDto.getName(), productDto.getDescription(),
                productDto.getPrice(), productDto.getCategory(), true);
        product = productGateway.saveProduct(product);
        return product;
    }
}
