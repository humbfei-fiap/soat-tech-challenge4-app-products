package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdateProductRequestDto;
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
class UpdateProductUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @Test
    @DisplayName("Deve atualizar produto chamando o gateway com dados do DTO")
    void shouldUpdateProduct() {
        // Arrange
        UUID newId = UUID.randomUUID();
        UpdateProductRequestDto requestDto = new UpdateProductRequestDto(
                newId,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE,
                true
        );

        Product updatedProduct = new Product(
                newId,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE,
                true
        );

        when(productGateway.updateProduct(any(Product.class)))
                .thenReturn(updatedProduct);

        UpdateProductUseCase useCase = new UpdateProductUseCase(productGateway);

        // Act
        Product result = useCase.execute(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(newId, result.id());
        assertEquals("Hamburguer", result.name());
        assertEquals("Hamburguer artesanal", result.description());
        assertEquals(new BigDecimal("29.90"), result.price());
        assertEquals(Product.Category.LANCHE, result.category());
        assertTrue(result.available());

        // Captura para validar o Product criado internamente
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productGateway, times(1)).updateProduct(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(requestDto.getId(), capturedProduct.id());
        assertEquals(requestDto.getName(), capturedProduct.name());
        assertEquals(requestDto.getDescription(), capturedProduct.description());
        assertEquals(requestDto.getPrice(), capturedProduct.price());
        assertEquals(requestDto.getCategory(), capturedProduct.category());
        assertEquals(requestDto.getAvailable(), capturedProduct.available());
    }
}
