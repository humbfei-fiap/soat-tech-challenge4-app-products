package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.ProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterProductUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @Test
    @DisplayName("Deve registrar um produto chamando o gateway com dados do DTO")
    void shouldRegisterProduct() {
        // Arrange
        UUID newId = UUID.randomUUID();
        ProductDto requestDto = new ProductDto(
                newId,
                "Batata Frita",
                "Batata frita crocante",
                new BigDecimal("12.50"),
                Product.Category.ACOMPANHAMENTO,
                true
        );

        Product savedProduct = new Product(
                newId,
                "Batata Frita",
                "Batata frita crocante",
                new BigDecimal("12.50"),
                Product.Category.ACOMPANHAMENTO,
                true
        );

        when(productGateway.saveProduct(any(Product.class)))
                .thenReturn(savedProduct);

        RegisterProductUseCase useCase = new RegisterProductUseCase(productGateway);

        // Act
        Product result = useCase.execute(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(newId, result.id());
        assertEquals("Batata Frita", result.name());
        assertEquals("Batata frita crocante", result.description());
        assertEquals(new BigDecimal("12.50"), result.price());
        assertEquals(Product.Category.ACOMPANHAMENTO, result.category());
        assertTrue(result.available());

        // Captura do Product criado internamente
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productGateway, times(1)).saveProduct(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(requestDto.getName(), capturedProduct.name());
        assertEquals(requestDto.getDescription(), capturedProduct.description());
        assertEquals(requestDto.getPrice(), capturedProduct.price());
        assertEquals(requestDto.getCategory(), capturedProduct.category());
        assertEquals(requestDto.getAvailable(), capturedProduct.available());
    }
}
