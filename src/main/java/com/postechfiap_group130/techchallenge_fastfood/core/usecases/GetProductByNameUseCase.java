package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;

public record GetProductByNameUseCase(ProductGateway productGateway) {
    public Product execute(String name) {
        return productGateway.findByName(name);
    }
}
