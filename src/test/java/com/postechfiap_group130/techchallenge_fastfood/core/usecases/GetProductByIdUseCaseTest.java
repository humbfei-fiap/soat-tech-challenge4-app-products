package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductByIdUseCaseTest {

    private ProductGateway productGateway;
    private GetProductByIdUseCase useCase;

    @BeforeEach
    void setup() {
        productGateway = mock(ProductGateway.class);
        useCase = new GetProductByIdUseCase(productGateway);
    }

    private Product createProduct() {
        return new Product(
                UUID.randomUUID(),
                "Produto",
                "Descrição",
                new BigDecimal("10.00"),
                Product.Category.LANCHE,
                true
        );
    }

    @Test
    @DisplayName("Deve retornar produto quando encontrado pelo ID")
    void shouldReturnProductWhenFound() {
        Product product = createProduct();
        UUID id = product.id();

        when(productGateway.findById(id)).thenReturn(product);

        Product result = useCase.execute(id);

        assertNotNull(result);
        assertEquals(product.id(), result.id());
        assertEquals(product.name(), result.name());
        verify(productGateway).findById(id);
    }

    @Test
    @DisplayName("Deve retornar null quando produto não for encontrado pelo ID")
    void shouldReturnNullWhenNotFound() {
        UUID id = UUID.randomUUID();

        when(productGateway.findById(id)).thenReturn(null);

        Product result = useCase.execute(id);

        assertNull(result);
        verify(productGateway).findById(id);
    }
}