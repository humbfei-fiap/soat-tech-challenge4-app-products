package com.postechfiap_group130.techchallenge_fastfood.core.gateways;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductGatewayTest {

    @Mock
    private DataSource dataSource;

    @InjectMocks
    private ProductGateway productGateway;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setup() {
        UUID newId = UUID.randomUUID();
        product = new Product(
                newId,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE,
                true
        );

        productDto = new ProductDto(
                newId,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE,
                true
        );
    }

    @Test
    @DisplayName("Deve salvar produto com sucesso")
    void shouldSaveProduct() {
        when(dataSource.saveProduct(any(ProductDto.class))).thenReturn(productDto);

        Product result = productGateway.saveProduct(product);

        assertNotNull(result);
        assertEquals(productDto.getId(), result.id());
        assertEquals(productDto.getName(), result.name());
        assertEquals(productDto.getDescription(), result.description());
        assertEquals(productDto.getPrice(), result.price());
        assertEquals(productDto.getCategory(), result.category());
        assertEquals(productDto.getAvailable(), result.available());

        verify(dataSource, times(1)).saveProduct(any(ProductDto.class));
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void shouldUpdateProduct() {
        when(dataSource.updateProduct(any(ProductDto.class))).thenReturn(productDto);

        Product result = productGateway.updateProduct(product);

        assertNotNull(result);
        assertEquals(product.id(), result.id());

        verify(dataSource, times(1)).updateProduct(any(ProductDto.class));
    }

    //@Test
    @DisplayName("Deve encontrar produto por ID")
    void shouldFindById() {
        UUID id = UUID.randomUUID();
        when(dataSource.findById(id)).thenReturn(productDto);

        Product result = productGateway.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());

        verify(dataSource, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar null quando produto não existir")
    void shouldReturnNullWhenProductNotFound() {
        UUID newid = UUID.randomUUID();
        when(dataSource.findById(newid)).thenReturn(null);

        Product result = productGateway.findById(newid);

        assertNull(result);

        verify(dataSource, times(1)).findById(newid);
    }

    @Test
    @DisplayName("Deve retornar lista de produtos")
    void shouldFindAll() {
        when(dataSource.getAll()).thenReturn(List.of(productDto));

        List<Product> result = productGateway.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDto.getName(), result.get(0).name());

        verify(dataSource, times(1)).getAll();
    }

    @Test
    @DisplayName("Deve retornar lista de produtos por categoria")
    void shouldFindAllByCategory() {
        when(dataSource.getByCategory(Product.Category.LANCHE))
                .thenReturn(List.of(productDto));

        List<Product> result = productGateway.findAllByCategory(Product.Category.LANCHE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Product.Category.LANCHE, result.get(0).category());

        verify(dataSource, times(1)).getByCategory(Product.Category.LANCHE);
    }

    @Test
    @DisplayName("Deve verificar se produto existe pelo nome")
    void shouldCheckIfExistsByName() {
        when(dataSource.existsByName("Hamburguer")).thenReturn(true);

        Boolean exists = productGateway.existsByName("Hamburguer");

        assertTrue(exists);

        verify(dataSource, times(1)).existsByName("Hamburguer");
    }
}
