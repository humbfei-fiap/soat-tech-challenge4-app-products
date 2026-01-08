package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import java.util.List;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;

public record GetProductsByCategoryUseCase(ProductGateway productGateway) {

    public List<Product> execute(Product.Category category) {
        return productGateway.findAllByCategory(category);
    }
}
