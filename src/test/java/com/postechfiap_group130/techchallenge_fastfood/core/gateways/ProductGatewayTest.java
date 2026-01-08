package com.postechfiap_group130.techchallenge_fastfood.core.gateways;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductGatewayTest {

    private DataSource dataSource;
    private ProductGateway gateway;

    @BeforeEach
    void setup() {
        dataSource = mock(DataSource.class);
        gateway = new ProductGateway(dataSource);
    }

    private Product createEntity() {
        return new Product(
                UUID.randomUUID(),
                "Produto",
                "Descrição",
                new BigDecimal("10.00"),
                Product.Category.LANCHE,
                true
        );
    }

    private ProductDto createDto() {
        return new ProductDto(
                UUID.randomUUID(),
                "Produto",
                "Descrição",
                new BigDecimal("10.00"),
                Product.Category.LANCHE,
                true
        );
    }

    // ============================
    // saveProduct
    // ============================

    @Test
    @DisplayName("Deve salvar produto e retornar entidade convertida")
    void shouldSaveProduct() {
        Product entity = createEntity();
        ProductDto dto = createDto();

        when(dataSource.saveProduct(any(ProductDto.class))).thenReturn(dto);

        Product result = gateway.saveProduct(entity);

        assertNotNull(result);
        assertEquals(dto.getId(), result.id());
        assertEquals(dto.getName(), result.name());
        assertEquals(dto.getDescription(), result.description());
        assertEquals(dto.getPrice(), result.price());
        assertEquals(dto.getCategory(), result.category());
        assertEquals(dto.getAvailable(), result.available());

        verify(dataSource).saveProduct(any(ProductDto.class));
    }

    // ============================
    // updateProduct
    // ============================

    @Test
    @DisplayName("Deve atualizar produto e retornar entidade convertida")
    void shouldUpdateProduct() {
        Product entity = createEntity();
        ProductDto dto = createDto();

        when(dataSource.updateProduct(any(ProductDto.class))).thenReturn(dto);

        Product result = gateway.updateProduct(entity);

        assertNotNull(result);
        assertEquals(dto.getId(), result.id());
        assertEquals(dto.getName(), result.name());
        assertEquals(dto.getDescription(), result.description());
        assertEquals(dto.getPrice(), result.price());
        assertEquals(dto.getCategory(), result.category());
        assertEquals(dto.getAvailable(), result.available());

        verify(dataSource).updateProduct(any(ProductDto.class));
    }

    // ============================
    // findById
    // ============================

    @Test
    @DisplayName("Deve retornar produto quando encontrado por ID")
    void shouldFindById() {
        ProductDto dto = createDto();
        UUID id = dto.getId();

        when(dataSource.findById(id)).thenReturn(dto);

        Product result = gateway.findById(id);

        assertNotNull(result);
        assertEquals(dto.getId(), result.id());
    }

    @Test
    @DisplayName("Deve retornar null quando produto não encontrado por ID")
    void shouldReturnNullWhenFindByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(dataSource.findById(id)).thenReturn(null);

        Product result = gateway.findById(id);

        assertNull(result);
    }

    // ============================
    // findByName
    // ============================

    @Test
    @DisplayName("Deve retornar produto quando encontrado por nome")
    void shouldFindByName() {
        ProductDto dto = createDto();

        when(dataSource.findByName("Produto")).thenReturn(dto);

        Product result = gateway.findByName("Produto");

        assertNotNull(result);
        assertEquals(dto.getName(), result.name());
    }

    @Test
    @DisplayName("Deve retornar null quando produto não encontrado por nome")
    void shouldReturnNullWhenFindByNameNotFound() {
        when(dataSource.findByName("Inexistente")).thenReturn(null);

        Product result = gateway.findByName("Inexistente");

        assertNull(result);
    }

    // ============================
    // findAllByCategory
    // ============================

    @Test
    @DisplayName("Deve retornar lista convertida de produtos por categoria")
    void shouldFindAllByCategory() {
        ProductDto dto = createDto();

        when(dataSource.getByCategory(Product.Category.LANCHE))
                .thenReturn(List.of(dto));

        List<Product> result = gateway.findAllByCategory(Product.Category.LANCHE);

        assertEquals(1, result.size());
        assertEquals(dto.getId(), result.get(0).id());
    }

    // ============================
    // existsByName
    // ============================

    @Test
    @DisplayName("Deve retornar true quando produto existe pelo nome")
    void shouldReturnTrueWhenExistsByName() {
        when(dataSource.existsByName("Produto")).thenReturn(true);

        assertTrue(gateway.existsByName("Produto"));
    }

    @Test
    @DisplayName("Deve retornar false quando produto não existe pelo nome")
    void shouldReturnFalseWhenNotExistsByName() {
        when(dataSource.existsByName("Produto")).thenReturn(false);

        assertFalse(gateway.existsByName("Produto"));
    }
}