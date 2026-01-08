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
class GetProductsByCategoryUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @Test
    @DisplayName("Deve retornar produtos filtrados por categoria")
    void shouldReturnProductsByCategory() {
        // Arrange
        Product.Category category = Product.Category.LANCHE;

        List<Product> products = List.of(
                new Product(
                        UUID.randomUUID(),
                        "Hambúrguer",
                        "Hambúrguer artesanal",
                        new BigDecimal("25.00"),
                        Product.Category.LANCHE,
                        true
                ),
                new Product(
                        UUID.randomUUID(),
                        "Cheeseburger",
                        "Com queijo",
                        new BigDecimal("27.00"),
                        Product.Category.LANCHE,
                        true
                )
        );

        when(productGateway.findAllByCategory(category))
                .thenReturn(products);

        GetProductsByCategoryUseCase useCase =
                new GetProductsByCategoryUseCase(productGateway);

        // Act
        List<Product> result = useCase.execute(category);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(products, result);

        verify(productGateway, times(1))
                .findAllByCategory(category);
        verifyNoMoreInteractions(productGateway);
    }
}
