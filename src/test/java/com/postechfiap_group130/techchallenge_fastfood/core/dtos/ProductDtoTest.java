package com.postechfiap_group130.techchallenge_fastfood.core.dtos;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductDtoTest {

    @Test
    @DisplayName("Deve criar ProductDto corretamente via construtor")
    void shouldCreateProductDtoUsingConstructor() {
        UUID newId = UUID.randomUUID();
        ProductDto dto = new ProductDto(
                newId,
                "Produto",
                "Descrição",
                new BigDecimal("10.00"),
                Product.Category.LANCHE,
                true
        );

        assertEquals(newId, dto.getId());
        assertEquals("Produto", dto.getName());
        assertEquals("Descrição", dto.getDescription());
        assertEquals(new BigDecimal("10.00"), dto.getPrice());
        assertEquals(Product.Category.LANCHE, dto.getCategory());
        assertTrue(dto.getAvailable());
    }

    @Test
    @DisplayName("Deve setar e obter id corretamente")
    void shouldSetAndGetId() {
        ProductDto dto = new ProductDto(null, null, null, null, null, null);
        UUID newId = UUID.randomUUID();
        dto.setId(newId);

        assertEquals(newId, dto.getId());
    }

    @Test
    @DisplayName("Deve setar e obter name corretamente")
    void shouldSetAndGetName() {
        ProductDto dto = new ProductDto(null, null, null, null, null, null);

        dto.setName("X-Burger");

        assertEquals("X-Burger", dto.getName());
    }

    @Test
    @DisplayName("Deve setar e obter description corretamente")
    void shouldSetAndGetDescription() {
        ProductDto dto = new ProductDto(null, null, null, null, null, null);

        dto.setDescription("Descrição teste");

        assertEquals("Descrição teste", dto.getDescription());
    }

    @Test
    @DisplayName("Deve setar e obter price corretamente")
    void shouldSetAndGetPrice() {
        ProductDto dto = new ProductDto(null, null, null, null, null, null);

        BigDecimal price = new BigDecimal("15.90");
        dto.setPrice(price);

        assertEquals(price, dto.getPrice());
    }

    @Test
    @DisplayName("Deve setar e obter category corretamente")
    void shouldSetAndGetCategory() {
        ProductDto dto = new ProductDto(null, null, null, null, null, null);

        dto.setCategory(Product.Category.SOBREMESA);

        assertEquals(Product.Category.SOBREMESA, dto.getCategory());
    }

    @Test
    @DisplayName("Deve setar e obter available corretamente")
    void shouldSetAndGetAvailable() {
        ProductDto dto = new ProductDto(null, null, null, null, null, null);

        dto.setAvailable(false);

        assertFalse(dto.getAvailable());
    }
}
