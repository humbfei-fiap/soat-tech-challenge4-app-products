package com.postechfiap_group130.techchallenge_fastfood.core.presenters;

import java.util.List;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;

public class ProductPresenter {
    private ProductPresenter(){}
    public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                        product.id(),
                        product.name(),
                        product.description(),
                        product.price(),
                        product.category(),
                        product.available()
                    );
    }

    public static List<ProductResponseDto> toDtoList(List<Product> listProduct) {
        return listProduct.stream()
                .map((product -> new ProductResponseDto(
                        product.id(),
                        product.name(),
                        product.description(),
                        product.price(),
                        product.category(),
                        product.available()
                    )))
                .toList();
    }
}
