package com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductRequestDtoTest {

    @Test
    @DisplayName("Deve criar ProductRequestDto usando construtor")
    void shouldCreateUsingConstructor() {
        ProductRequestDto dto = new ProductRequestDto(
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE
        );

        assertEquals("Hamburguer", dto.getName());
        assertEquals("Hamburguer artesanal", dto.getDescription());
        assertEquals(new BigDecimal("29.90"), dto.getPrice());
        assertEquals(Product.Category.LANCHE, dto.getCategory());
    }

    @Test
    @DisplayName("Deve testar todos os getters e setters")
    void shouldTestAllGettersAndSetters() {
        ProductRequestDto dto = new ProductRequestDto(
                null, null, null, null
        );

        dto.setName("Refrigerante");
        dto.setDescription("Coca-Cola lata");
        dto.setPrice(new BigDecimal("7.50"));
        dto.setCategory(Product.Category.BEBIDA);

        assertEquals("Refrigerante", dto.getName());
        assertEquals("Coca-Cola lata", dto.getDescription());
        assertEquals(new BigDecimal("7.50"), dto.getPrice());
        assertEquals(Product.Category.BEBIDA, dto.getCategory());
    }
}
