package com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;

public class ProductResponseDto {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Product.Category category;
    private Boolean available;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product.Category getCategory() {
        return category;
    }

    public void setCategory(Product.Category category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public ProductResponseDto(UUID id, String name, String description, BigDecimal price, Product.Category category, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = available;
    }
}
