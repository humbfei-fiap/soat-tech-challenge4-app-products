package com.postechfiap_group130.techchallenge_fastfood.core.presenters;

import java.util.List;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;

public class ProductPresenter {

    public static ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getAvailable()
                    );
    }

    public static List<ProductResponseDto> toDtoList(List<Product> listProduct) {
        List<ProductResponseDto> productDtoList = listProduct.stream()
                .map((product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getAvailable()
                    )))
                .toList();
        return productDtoList;
    }
}
