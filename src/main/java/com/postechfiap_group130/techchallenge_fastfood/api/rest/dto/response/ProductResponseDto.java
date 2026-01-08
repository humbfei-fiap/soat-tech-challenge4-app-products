package com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductResponseDto(
    UUID id,
    String name,
    String description,
    BigDecimal price,
    Product.Category category,
    Boolean available
) {    // Métodos de compatibilidade para manter os testes funcionando
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Product.Category getCategory() { return category; }
    public Boolean getAvailable() { return available; }
}