package com.postechfiap_group130.techchallenge_fastfood.core.controllers;

import java.util.List;
import java.util.UUID;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdateProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import com.postechfiap_group130.techchallenge_fastfood.core.presenters.ProductPresenter;
import com.postechfiap_group130.techchallenge_fastfood.core.usecases.*;

public record ProductController(DataSource dataSource) {

    public ProductResponseDto createProduct(ProductDto productDto) throws DomainException {
        ProductGateway productGateway = new ProductGateway(dataSource);
        Boolean existProduct = productGateway.existsByName(productDto.getName());
        if (Boolean.TRUE.equals(existProduct)) {
            throw new DomainException("Product name already registered in the database!");
        }
        RegisterProductUseCase registerProductUseCase = new RegisterProductUseCase(productGateway);
        Product product = registerProductUseCase.execute(productDto);
        return ProductPresenter.toDto(product);
    }

    public void updateProduct(UpdateProductRequestDto updateProductRequestDto) throws DomainException {
        ProductGateway productGateway = new ProductGateway(dataSource);
        UpdateProductUseCase updateProductUseCase = new UpdateProductUseCase(productGateway);
        Product product = updateProductUseCase.execute(updateProductRequestDto);
        ProductPresenter.toDto(product);
    }

    public List<ProductResponseDto> getProductsByCategory(Product.Category category) {
        ProductGateway productGateway = new ProductGateway(dataSource);
        GetProductsByCategoryUseCase getProductsByCategoryUseCase = new GetProductsByCategoryUseCase(productGateway);
        List<Product> products = getProductsByCategoryUseCase.execute(category);
        return ProductPresenter.toDtoList(products);
    }

    public ProductResponseDto getProductById(UUID id) {
        ProductGateway productGateway = new ProductGateway(dataSource);
        GetProductByIdUseCase getProductByIdUseCase = new GetProductByIdUseCase(productGateway);
        Product product = getProductByIdUseCase.execute(id);
        return ProductPresenter.toDto(product);
    }

    public ProductResponseDto getProductByName(String name) {
        ProductGateway productGateway = new ProductGateway(dataSource);
        GetProductByNameUseCase ggtProductByNameUseCase = new GetProductByNameUseCase(productGateway);
        Product product = ggtProductByNameUseCase.execute(name);
        return ProductPresenter.toDto(product);
    }
}
