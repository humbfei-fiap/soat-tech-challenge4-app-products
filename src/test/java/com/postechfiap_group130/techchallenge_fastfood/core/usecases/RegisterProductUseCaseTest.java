package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.ProductRequestDto;
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
        ProductRequestDto requestDto = new ProductRequestDto(
                "Batata Frita",
                "Batata frita crocante",
                new BigDecimal("12.50"),
                Product.Category.ACOMPANHAMENTO
        );
        UUID newId = UUID.randomUUID();
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
        assertEquals(newId, result.getId());
        assertEquals("Batata Frita", result.getName());
        assertEquals("Batata frita crocante", result.getDescription());
        assertEquals(new BigDecimal("12.50"), result.getPrice());
        assertEquals(Product.Category.ACOMPANHAMENTO, result.getCategory());
        assertTrue(result.getAvailable());

        // Captura do Product criado internamente
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productGateway, times(1)).saveProduct(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(requestDto.getName(), capturedProduct.getName());
        assertEquals(requestDto.getDescription(), capturedProduct.getDescription());
        assertEquals(requestDto.getPrice(), capturedProduct.getPrice());
        assertEquals(requestDto.getCategory(), capturedProduct.getCategory());
        assertNotNull(capturedProduct.getAvailable()); // default definido na entidade
    }
}
