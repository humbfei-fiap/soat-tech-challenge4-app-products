package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;
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
class GetAllProductsUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @Test
    @DisplayName("Deve retornar todos os produtos chamando o gateway")
    void shouldReturnAllProducts() {
        List<Product> products = List.of(
                new Product(
                        UUID.randomUUID(),
                        "Hamburguer",
                        "Hamburguer artesanal",
                        new BigDecimal("29.90"),
                        Product.Category.LANCHE,
                        true
                )
        );

        when(productGateway.findAll()).thenReturn(products);

        GetAllProductsUseCase useCase = new GetAllProductsUseCase(productGateway);

        List<Product> result = useCase.execute();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hamburguer", result.get(0).getName());

        verify(productGateway, times(1)).findAll();
    }
}
