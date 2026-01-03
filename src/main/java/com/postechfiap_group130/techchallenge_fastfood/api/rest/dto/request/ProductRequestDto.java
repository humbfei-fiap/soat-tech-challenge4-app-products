package com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;

import java.math.BigDecimal;

public class ProductRequestDto {
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

    public ProductRequestDto(String name, String description, BigDecimal price, Product.Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    private String name;
    private String description;
    private BigDecimal price;
    private Product.Category category;

}
