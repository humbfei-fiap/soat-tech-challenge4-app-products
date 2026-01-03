package com.postechfiap_group130.techchallenge_fastfood.core.controllers;

import java.util.List;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.ProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdateProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import com.postechfiap_group130.techchallenge_fastfood.core.presenters.ProductPresenter;
import com.postechfiap_group130.techchallenge_fastfood.core.usecases.GetAllProductsUseCase;
import com.postechfiap_group130.techchallenge_fastfood.core.usecases.GetProductsByCategoryUseCase;
import com.postechfiap_group130.techchallenge_fastfood.core.usecases.RegisterProductUseCase;
import com.postechfiap_group130.techchallenge_fastfood.core.usecases.UpdateProductUseCase;

public record ProductController(DataSource dataSource) {

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) throws Exception {
        ProductGateway productGateway = new ProductGateway(dataSource);
        Boolean existProduct = productGateway.existsByName(productRequestDto.getName());
        if (existProduct) {
            throw new DomainException("Product name already registered in the database!");
        }
        RegisterProductUseCase registerProductUseCase = new RegisterProductUseCase(productGateway);
        Product product = registerProductUseCase.execute(productRequestDto);
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

    public List<ProductResponseDto> getAll() {
        ProductGateway productGateway = new ProductGateway(dataSource);
        GetAllProductsUseCase getAllProductsUseCase = new GetAllProductsUseCase(productGateway);
        List<Product> products = getAllProductsUseCase.execute();
        return ProductPresenter.toDtoList(products);
    }
}
