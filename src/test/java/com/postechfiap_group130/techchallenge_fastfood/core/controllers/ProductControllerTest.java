package com.postechfiap_group130.techchallenge_fastfood.core.controllers;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.ProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdateProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private DataSource dataSource;

    @Test
    @DisplayName("Deve criar produto com sucesso")
    void shouldCreateProductSuccessfully() throws Exception {
        UUID newId = UUID.randomUUID();
        ProductRequestDto request =
                new ProductRequestDto("Burger", "Desc", new BigDecimal("20.00"), Product.Category.LANCHE);

        when(dataSource.existsByName("Burger")).thenReturn(false);
        when(dataSource.saveProduct(any())).thenReturn(
                new ProductDto(UUID.randomUUID(),
                        "Burger", "Desc",
                        new BigDecimal("20.00"),
                        Product.Category.LANCHE,
                        true
                )
        );

        ProductController controller = new ProductController(dataSource);

        ProductResponseDto response = controller.createProduct(request);

        assertNotNull(response);
        assertEquals("Burger", response.getName());
        assertEquals(Product.Category.LANCHE, response.getCategory());

        verify(dataSource).existsByName("Burger");
        verify(dataSource).saveProduct(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar produto duplicado")
    void shouldThrowExceptionWhenProductAlreadyExists() {
        ProductRequestDto request =
                new ProductRequestDto("Burger", "Desc", new BigDecimal("20.00"), Product.Category.LANCHE);

        when(dataSource.existsByName("Burger")).thenReturn(true);

        ProductController controller = new ProductController(dataSource);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> controller.createProduct(request)
        );

        assertEquals(
                "Product name already registered in the database!",
                exception.getMessage()
        );

        verify(dataSource).existsByName("Burger");
        verify(dataSource, never()).saveProduct(any());
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void shouldUpdateProductSuccessfully() {
        UpdateProductRequestDto request =
                new UpdateProductRequestDto(
                        UUID.randomUUID(),
                        "Burger",
                        "Updated",
                        new BigDecimal("25.00"),
                        Product.Category.LANCHE,
                        true
                );

        when(dataSource.updateProduct(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ProductController controller = new ProductController(dataSource);

        assertDoesNotThrow(() -> controller.updateProduct(request));

        verify(dataSource).updateProduct(any());
    }

    @Test
    @DisplayName("Deve retornar produtos por categoria")
    void shouldReturnProductsByCategory() {
        when(dataSource.getByCategory(Product.Category.BEBIDA))
                .thenReturn(List.of(
                        new ProductDto(
                                UUID.randomUUID(), "Coca", "Refrigerante",
                                new BigDecimal("7.00"),
                                Product.Category.BEBIDA,
                                true
                        )
                ));

        ProductController controller = new ProductController(dataSource);

        List<ProductResponseDto> result =
                controller.getProductsByCategory(Product.Category.BEBIDA);

        assertEquals(1, result.size());
        assertEquals("Coca", result.get(0).getName());

        verify(dataSource).getByCategory(Product.Category.BEBIDA);
    }

    @Test
    @DisplayName("Deve retornar todos os produtos")
    void shouldReturnAllProducts() {
        when(dataSource.getAll()).thenReturn(List.of(
                new ProductDto(
                        UUID.randomUUID(), "Burger", "Desc",
                        new BigDecimal("20.00"),
                        Product.Category.LANCHE,
                        true
                ),
                new ProductDto(
                        UUID.randomUUID(), "Coca", "Refri",
                        new BigDecimal("7.00"),
                        Product.Category.BEBIDA,
                        true
                )
        ));

        ProductController controller = new ProductController(dataSource);

        List<ProductResponseDto> result = controller.getAll();

        assertEquals(2, result.size());

        verify(dataSource).getAll();
    }
}

