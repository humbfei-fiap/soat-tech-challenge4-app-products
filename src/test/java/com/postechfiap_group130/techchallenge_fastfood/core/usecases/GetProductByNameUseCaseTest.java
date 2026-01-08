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

class GetProductByNameUseCaseTest {

    private ProductGateway productGateway;
    private GetProductByNameUseCase useCase;

    @BeforeEach
    void setup() {
        productGateway = mock(ProductGateway.class);
        useCase = new GetProductByNameUseCase(productGateway);
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
    @DisplayName("Deve retornar produto quando encontrado pelo nome")
    void shouldReturnProductWhenFound() {
        Product product = createProduct();

        when(productGateway.findByName("Produto")).thenReturn(product);

        Product result = useCase.execute("Produto");

        assertNotNull(result);
        assertEquals(product.name(), result.name());
        verify(productGateway).findByName("Produto");
    }

    @Test
    @DisplayName("Deve retornar null quando produto não for encontrado pelo nome")
    void shouldReturnNullWhenNotFound() {
        when(productGateway.findByName("Inexistente")).thenReturn(null);

        Product result = useCase.execute("Inexistente");

        assertNull(result);
        verify(productGateway).findByName("Inexistente");
    }
}
