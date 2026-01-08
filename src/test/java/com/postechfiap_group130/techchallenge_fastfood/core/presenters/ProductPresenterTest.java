package com.postechfiap_group130.techchallenge_fastfood.core.presenters;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductPresenterTest {

    private Product createProduct() {
        return new Product(
                UUID.randomUUID(),
                "Produto",
                "Descrição",
                BigDecimal.TEN,
                Product.Category.LANCHE,
                true
        );
    }

    @Test
    @DisplayName("Deve converter Product para ProductResponseDto")
    void shouldConvertToDto() {
        Product product = createProduct();

        ProductDto dto = ProductPresenter.toDto(product);

        assertNotNull(dto);
        // Ajuste aqui: usando getters, que é o mais comum
        assertEquals(product.id(), dto.getId());
        assertEquals(product.name(), dto.getName());
        assertEquals(product.description(), dto.getDescription());
        assertEquals(product.price(), dto.getPrice());
        assertEquals(product.category(), dto.getCategory());
        assertEquals(product.available(), dto.getAvailable());
    }

    @Test
    @DisplayName("Deve converter lista de Product para lista de ProductResponseDto")
    void shouldConvertToDtoList() {
        Product p1 = createProduct();
        Product p2 = createProduct();

        List<ProductDto> list = ProductPresenter.toDtoList(List.of(p1, p2));

        assertEquals(2, list.size());
        assertEquals(p1.id(), list.get(0).getId());
        assertEquals(p2.id(), list.get(1).getId());
    }

    @Test
    @DisplayName("Deve converter lista vazia sem erros")
    void shouldConvertEmptyList() {
        List<ProductDto> list = ProductPresenter.toDtoList(List.of());
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Construtor deve ser privado")
    void shouldHavePrivateConstructor() throws Exception {
        Constructor<ProductPresenter> constructor = ProductPresenter.class.getDeclaredConstructor();

        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);
        ProductPresenter instance = constructor.newInstance();
        assertNotNull(instance);
    }
}