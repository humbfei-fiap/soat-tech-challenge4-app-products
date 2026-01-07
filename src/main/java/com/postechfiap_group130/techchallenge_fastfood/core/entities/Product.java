package com.postechfiap_group130.techchallenge_fastfood.core.entities;

import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, String name, String description, BigDecimal price, Category category,
                      Boolean available) {

    public Product {
        Validate(name, description, price, category);
    }

    private void Validate(String name, String description, BigDecimal price, Category category) throws DomainException {
        if (name == null || name.isEmpty()) {
            throw new DomainException("Product name cannot be null or empty");
        }

        if (name.length() > 100) {
            throw new DomainException("Product name is too long. Max length is 100");
        }

        if (description == null || description.isEmpty()) {
            throw new DomainException("Product description cannot be null or empty");
        }

        if (price == null) {
            throw new DomainException("Product price must be not null");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Product price must be greater than 0");
        }

        if (category == null) {
            throw new DomainException("Category cannot be null");
        }
    }

    public enum Category {
        LANCHE,
        BEBIDA,
        ACOMPANHAMENTO,
        SOBREMESA
    }
}
