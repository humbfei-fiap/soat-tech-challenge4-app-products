package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;

import java.util.UUID;

public record GetProductByIdUseCase(ProductGateway productGateway) {
    public Product execute(UUID id) {
        return productGateway.findById(id);
    }
}
