package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;

public record UpdateProductUseCase(ProductGateway productGateway) {

    public Product execute(ProductDto productDto) {
        Product product = new Product(productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getCategory(),
                productDto.getAvailable());
        product = productGateway.updateProduct(product);
        return product;
    }
}
