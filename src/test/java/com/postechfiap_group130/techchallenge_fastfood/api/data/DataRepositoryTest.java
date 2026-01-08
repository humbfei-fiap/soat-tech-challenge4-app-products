package com.postechfiap_group130.techchallenge_fastfood.api.data;

import com.postechfiap_group130.techchallenge_fastfood.api.data.jpa.ProductEntity;
import com.postechfiap_group130.techchallenge_fastfood.api.data.jpa.ProductJpaRepository;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataRepositoryTest {

    private ProductJpaRepository repository;
    private DataRepository dataRepository;

    @BeforeEach
    void setup() {
        repository = mock(ProductJpaRepository.class);
        dataRepository = new DataRepository(repository);
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

    private ProductEntity createEntity() {
        return new ProductEntity(
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
    @DisplayName("Deve salvar produto e retornar ProductDto convertido")
    void shouldSaveProduct() {
        ProductDto dto = createDto();
        ProductEntity entity = createEntity();

        when(repository.save(any(ProductEntity.class))).thenReturn(entity);

        ProductDto result = dataRepository.saveProduct(dto);

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getDescription(), result.getDescription());
        assertEquals(entity.getPrice(), result.getPrice());
        assertEquals(entity.getCategory(), result.getCategory());
        assertEquals(entity.getAvailable(), result.getAvailable());

        verify(repository).save(any(ProductEntity.class));
    }

    // ============================
    // updateProduct
    // ============================

    @Test
    @DisplayName("Deve atualizar produto chamando saveProduct e retornar o mesmo DTO")
    void shouldUpdateProduct() {
        ProductDto dto = createDto();
        ProductEntity entity = createEntity();

        when(repository.save(any(ProductEntity.class))).thenReturn(entity);

        ProductDto result = dataRepository.updateProduct(dto);

        assertEquals(dto, result);
        verify(repository).save(any(ProductEntity.class));
    }

    // ============================
    // findById
    // ============================

    @Test
    @DisplayName("Deve retornar ProductDto quando encontrar por ID")
    void shouldFindById() {
        ProductEntity entity = createEntity();
        UUID id = entity.getId();

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        ProductDto result = dataRepository.findById(id);

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
    }

    @Test
    @DisplayName("Deve retornar null quando não encontrar por ID")
    void shouldReturnNullWhenFindByIdNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        ProductDto result = dataRepository.findById(id);

        assertNull(result);
    }

    // ============================
    // findByName
    // ============================

    @Test
    @DisplayName("Deve retornar ProductDto quando encontrar por nome")
    void shouldFindByName() {
        ProductEntity entity = createEntity();

        when(repository.findByName("Produto")).thenReturn(entity);

        ProductDto result = dataRepository.findByName("Produto");

        assertNotNull(result);
        assertEquals(entity.getName(), result.getName());
    }

    @Test
    @DisplayName("Deve retornar null quando não encontrar por nome")
    void shouldReturnNullWhenFindByNameNotFound() {
        when(repository.findByName("Inexistente")).thenReturn(null);

        ProductDto result = dataRepository.findByName("Inexistente");

        assertNull(result);
    }

    // ============================
    // getByCategory
    // ============================

    @Test
    @DisplayName("Deve retornar lista convertida de ProductDto por categoria")
    void shouldGetByCategory() {
        ProductEntity entity = createEntity();

        when(repository.getByCategory(Product.Category.LANCHE))
                .thenReturn(List.of(entity));

        List<ProductDto> result = dataRepository.getByCategory(Product.Category.LANCHE);

        assertEquals(1, result.size());
        assertEquals(entity.getId(), result.get(0).getId());
    }

    // ============================
    // existsByName
    // ============================

    @Test
    @DisplayName("Deve retornar true quando produto existir pelo nome")
    void shouldReturnTrueWhenExistsByName() {
        when(repository.existsByName("Produto")).thenReturn(true);

        assertTrue(dataRepository.existsByName("Produto"));
    }

    @Test
    @DisplayName("Deve retornar false quando produto não existir pelo nome")
    void shouldReturnFalseWhenNotExistsByName() {
        when(repository.existsByName("Produto")).thenReturn(false);

        assertFalse(dataRepository.existsByName("Produto"));
    }
}