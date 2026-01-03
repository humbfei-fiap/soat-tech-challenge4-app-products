package com.postechfiap_group130.techchallenge_fastfood.core.entities;

import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductTest {

    // =======================
    // CONSTRUTORES + GETTERS
    // =======================

    @Test
    @DisplayName("Deve criar produto válido usando construtor completo e validar getters")
    void shouldCreateProductWithAllArgumentsAndValidateGetters() {
        UUID newId = UUID.randomUUID();
        Product product = new Product(
                newId,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("25.90"),
                Product.Category.LANCHE,
                true
        );

        assertEquals(newId, product.getId());
        assertEquals("Hamburguer", product.getName());
        assertEquals("Hamburguer artesanal", product.getDescription());
        assertEquals(new BigDecimal("25.90"), product.getPrice());
        assertEquals(Product.Category.LANCHE, product.getCategory());
        assertTrue(product.getAvailable());
    }

    @Test
    @DisplayName("Deve criar produto válido usando construtor simplificado e available=true por padrão")
    void shouldCreateProductUsingSimplifiedConstructor() {
        Product product = new Product(
                "Refrigerante",
                "Coca-Cola 350ml",
                new BigDecimal("6.50"),
                Product.Category.BEBIDA
        );

        assertEquals("Refrigerante", product.getName());
        assertEquals("Coca-Cola 350ml", product.getDescription());
        assertEquals(new BigDecimal("6.50"), product.getPrice());
        assertEquals(Product.Category.BEBIDA, product.getCategory());
        assertTrue(product.getAvailable());
    }

    // =======================
    // VALIDAÇÕES – NAME
    // =======================

    @Test
    @DisplayName("Deve lançar exceção quando nome for null")
    void shouldThrowExceptionWhenNameIsNull() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(
                        null,
                        "Descrição",
                        new BigDecimal("10.00"),
                        Product.Category.LANCHE
                )
        );

        assertEquals("Product name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for vazio")
    void shouldThrowExceptionWhenNameIsEmpty() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(
                        "",
                        "Descrição",
                        new BigDecimal("10.00"),
                        Product.Category.LANCHE
                )
        );

        assertEquals("Product name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome exceder 100 caracteres")
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(101);

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(
                        longName,
                        "Descrição",
                        new BigDecimal("10.00"),
                        Product.Category.LANCHE
                )
        );

        assertEquals("Product name is too long. Max length is 100", exception.getMessage());
    }

    // =======================
    // VALIDAÇÕES – DESCRIPTION
    // =======================

    @Test
    @DisplayName("Deve lançar exceção quando descrição for null")
    void shouldThrowExceptionWhenDescriptionIsNull() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(
                        "Produto",
                        null,
                        new BigDecimal("10.00"),
                        Product.Category.LANCHE
                )
        );

        assertEquals("Product description cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando descrição for vazia")
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(
                        "Produto",
                        "",
                        new BigDecimal("10.00"),
                        Product.Category.LANCHE
                )
        );

        assertEquals("Product description cannot be null or empty", exception.getMessage());
    }

    // =======================
    // VALIDAÇÕES – PRICE
    // =======================

    @Test
    @DisplayName("Deve lançar exceção quando preço for null")
    void shouldThrowExceptionWhenPriceIsNull() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(
                        "Produto",
                        "Descrição",
                        null,
                        Product.Category.LANCHE
                )
        );

        assertEquals("Product price must be not null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando preço for zero")
    void shouldThrowExceptionWhenPriceIsZero() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(
                        "Produto",
                        "Descrição",
                        BigDecimal.ZERO,
                        Product.Category.LANCHE
                )
        );

        assertEquals("Product price must be greater than 0", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando preço for negativo")
    void shouldThrowExceptionWhenPriceIsNegative() {
        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(
                        "Produto",
                        "Descrição",
                        new BigDecimal("-1"),
                        Product.Category.LANCHE
                )
        );

        assertEquals("Product price must be greater than 0", exception.getMessage());
    }

    // =======================
    // ENUM CATEGORY (100% coverage)
    // =======================

    @Test
    @DisplayName("Deve cobrir todos os valores do enum Category")
    void shouldCoverAllCategoryEnumValues() {
        assertEquals(Product.Category.LANCHE, Product.Category.valueOf("LANCHE"));
        assertEquals(Product.Category.BEBIDA, Product.Category.valueOf("BEBIDA"));
        assertEquals(Product.Category.ACOMPANHAMENTO, Product.Category.valueOf("ACOMPANHAMENTO"));
        assertEquals(Product.Category.SOBREMESA, Product.Category.valueOf("SOBREMESA"));
    }

    @Test
    @DisplayName("Deve cobrir getter do enum Category (campo interno null)")
    void shouldCoverCategoryGetter() {
        Product.Category category = Product.Category.LANCHE;

        assertNull(category.getCategory());
    }
}
