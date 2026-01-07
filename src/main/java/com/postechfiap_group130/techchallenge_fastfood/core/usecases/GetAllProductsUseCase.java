package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;

import java.util.List;

public record GetAllProductsUseCase(ProductGateway productGateway) {

    public List<Product> execute() {
        return productGateway.findAll();
    }
}
