package com.postechfiap_group130.techchallenge_fastfood.api.data;

import com.postechfiap_group130.techchallenge_fastfood.api.data.jpa.ProductEntity;
import com.postechfiap_group130.techchallenge_fastfood.api.data.jpa.ProductJpaRepository;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataRepositoryTest {

    @Mock
    private ProductJpaRepository productJpaRepository;

    @InjectMocks
    private DataRepository dataRepository;

    private ProductEntity productEntity;
    private ProductDto productDto;

    private UUID id1;
    private UUID id2;

    @BeforeEach
    void setUp() {
        id1 = UUID.randomUUID();
        id2 = UUID.randomUUID();

        productEntity = new ProductEntity(
                id1,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE,
                true
        );

        productDto = new ProductDto(
                id1,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE,
                true
        );
    }

    // =======================
    // saveProduct
    // =======================

    @Test
    @DisplayName("Deve salvar produto e retornar ProductDto persistido")
    void shouldSaveProduct() {
        when(productJpaRepository.save(any(ProductEntity.class)))
                .thenReturn(productEntity);

        ProductDto result = dataRepository.saveProduct(productDto);

        assertNotNull(result);
        assertEquals(productEntity.getId(), result.getId());
        assertEquals(productEntity.getName(), result.getName());

        verify(productJpaRepository).save(any(ProductEntity.class));
    }

    // =======================
    // updateProduct
    // =======================

    @Test
    @DisplayName("Deve atualizar produto reutilizando saveProduct")
    void shouldUpdateProduct() {
        when(productJpaRepository.save(any(ProductEntity.class)))
                .thenReturn(productEntity);

        ProductDto result = dataRepository.updateProduct(productDto);

        assertNotNull(result);
        assertEquals(productDto.getId(), result.getId());

        verify(productJpaRepository).save(any(ProductEntity.class));
    }

    // =======================
    // findById
    // =======================

    @Test
    @DisplayName("Deve retornar ProductDto quando encontrar por ID")
    void shouldFindProductById() {
        when(productJpaRepository.findById(id1))
                .thenReturn(Optional.of(productEntity));
        ProductDto result = dataRepository.findById(id1);

        assertNotNull(result);
        assertEquals(productEntity.getId(), result.getId());

        verify(productJpaRepository).findById(id1);
    }

    @Test
    @DisplayName("Deve retornar null quando não encontrar produto por ID")
    void shouldReturnNullWhenProductNotFoundById() {
        UUID id = UUID.randomUUID();
        when(productJpaRepository.findById(id))
                .thenReturn(Optional.empty());

        ProductDto result = dataRepository.findById(id);

        assertNull(result);

        verify(productJpaRepository).findById(id);
    }

    // =======================
    // getAll
    // =======================

    @Test
    @DisplayName("Deve retornar lista de ProductDto")
    void shouldGetAllProducts() {
        when(productJpaRepository.getAll())
                .thenReturn(List.of(productEntity));

        List<ProductDto> result = dataRepository.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productEntity.getId(), result.get(0).getId());

        verify(productJpaRepository).getAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver produtos")
    void shouldReturnEmptyListWhenNoProductsExist() {
        when(productJpaRepository.getAll())
                .thenReturn(List.of());

        List<ProductDto> result = dataRepository.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productJpaRepository).getAll();
    }

    // =======================
    // getByCategory
    // =======================

    @Test
    @DisplayName("Deve retornar produtos filtrados por categoria")
    void shouldGetProductsByCategory() {
        when(productJpaRepository.getByCategory(Product.Category.LANCHE))
                .thenReturn(List.of(productEntity));

        List<ProductDto> result = dataRepository.getByCategory(Product.Category.LANCHE);

        assertEquals(1, result.size());
        assertEquals(Product.Category.LANCHE, result.get(0).getCategory());

        verify(productJpaRepository).getByCategory(Product.Category.LANCHE);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver produtos na categoria")
    void shouldReturnEmptyListWhenNoProductsInCategory() {
        when(productJpaRepository.getByCategory(Product.Category.BEBIDA))
                .thenReturn(List.of());

        List<ProductDto> result = dataRepository.getByCategory(Product.Category.BEBIDA);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(productJpaRepository).getByCategory(Product.Category.BEBIDA);
    }

    // =======================
    // existsByName
    // =======================

    @Test
    @DisplayName("Deve retornar true quando produto existir pelo nome")
    void shouldReturnTrueWhenProductExistsByName() {
        when(productJpaRepository.existsByName("Hamburguer"))
                .thenReturn(true);

        Boolean exists = dataRepository.existsByName("Hamburguer");

        assertTrue(exists);

        verify(productJpaRepository).existsByName("Hamburguer");
    }

    @Test
    @DisplayName("Deve retornar false quando produto não existir pelo nome")
    void shouldReturnFalseWhenProductDoesNotExistByName() {
        when(productJpaRepository.existsByName("Pizza"))
                .thenReturn(false);

        Boolean exists = dataRepository.existsByName("Pizza");

        assertFalse(exists);

        verify(productJpaRepository).existsByName("Pizza");
    }
}
