package com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductResponseDtoTest {

    @Test
    @DisplayName("Deve criar ProductResponseDto usando construtor completo")
    void shouldCreateUsingAllArgsConstructor() {
        UUID newId = UUID.randomUUID();
        ProductResponseDto dto = new ProductResponseDto(
                newId,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE,
                true
        );

        assertEquals(newId, dto.getId());
        assertEquals("Hamburguer", dto.getName());
        assertEquals("Hamburguer artesanal", dto.getDescription());
        assertEquals(new BigDecimal("29.90"), dto.getPrice());
        assertEquals(Product.Category.LANCHE, dto.getCategory());
        assertTrue(dto.getAvailable());
    }

    @Test
    @DisplayName("Deve testar todos os getters e setters")
    void shouldTestAllGettersAndSetters() {
        ProductResponseDto dto = new ProductResponseDto(
                null, null, null, null, null, null
        );

        UUID newId = UUID.randomUUID();
        dto.setId(newId);
        dto.setName("Refrigerante");
        dto.setDescription("Coca-Cola lata");
        dto.setPrice(new BigDecimal("7.50"));
        dto.setCategory(Product.Category.BEBIDA);
        dto.setAvailable(false);

        assertEquals(newId, dto.getId());
        assertEquals("Refrigerante", dto.getName());
        assertEquals("Coca-Cola lata", dto.getDescription());
        assertEquals(new BigDecimal("7.50"), dto.getPrice());
        assertEquals(Product.Category.BEBIDA, dto.getCategory());
        assertFalse(dto.getAvailable());
    }
}
