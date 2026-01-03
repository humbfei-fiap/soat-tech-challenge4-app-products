package com.postechfiap_group130.techchallenge_fastfood.core.entities;

import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Product.Category category;
    private final Boolean available;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Product(UUID id, String name, String description, BigDecimal price, Category category, Boolean available){
        Validate(name, description, price, category);
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = available;
    }

    public Product(String name, String description, BigDecimal price, Category category) {
        Validate(name, description, price, category);
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = true;
    }

    private void Validate(String name, String description, BigDecimal price, Product.Category category) throws DomainException{
        if (name == null || name.isEmpty()){
            throw new DomainException("Product name cannot be null or empty");
        }

        if (name.length() > 100){
            throw new DomainException("Product name is too long. Max length is 100");
        }

        if (description == null || description.isEmpty()){
            throw new DomainException("Product description cannot be null or empty");
        }

        if (price == null){
            throw new DomainException("Product price must be not null");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0){
            throw new DomainException("Product price must be greater than 0");
        }

        if (category == null){
            throw new DomainException("Category cannot be null");
        }
    }

    public enum Category {
        LANCHE,
        BEBIDA,
        ACOMPANHAMENTO,
        SOBREMESA;

        private Category category;

        public Category getCategory() {
            return category;
        }
    }
}
