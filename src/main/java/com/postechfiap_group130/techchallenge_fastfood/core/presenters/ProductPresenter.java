package com.postechfiap_group130.techchallenge_fastfood.core.presenters;

import java.util.List;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;

public class ProductPresenter {
    private ProductPresenter(){}
    public static ProductDto toDto(Product product) {
        return new ProductDto(
                        product.id(),
                        product.name(),
                        product.description(),
                        product.price(),
                        product.category(),
                        product.available()
                    );
    }

    public static List<ProductDto> toDtoList(List<Product> listProduct) {
        return listProduct.stream()
                .map((product -> new ProductDto(
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
