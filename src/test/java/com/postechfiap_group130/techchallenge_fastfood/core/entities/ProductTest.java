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
        UUID id = UUID.randomUUID();
        String name = "Hamburguer";
        String description = "Hamburguer artesanal";
        BigDecimal price = new BigDecimal("25.90");
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        Product product = new Product(id, name, description, price, category, available);

        assertEquals(id, product.id());
        assertEquals(name, product.name());
        assertEquals(description, product.description());
        assertEquals(price, product.price());
        assertEquals(category, product.category());
        assertTrue(product.available());
    }

    @Test
    @DisplayName("Deve criar produto válido usando construtor simplificado")
    void shouldCreateProductUsingSimplifiedConstructor() {
        UUID id = UUID.randomUUID();
        String name = "Refrigerante";
        String description = "Coca-Cola 350ml";
        BigDecimal price = new BigDecimal("6.50");
        Product.Category category = Product.Category.BEBIDA;
        boolean available = true;

        Product product = new Product(id, name, description, price, category, available);

        assertEquals(name, product.name());
        assertEquals(description, product.description());
        assertEquals(price, product.price());
        assertEquals(category, product.category());
        assertTrue(product.available());
    }

    // =======================
    // VALIDAÇÕES – NAME
    // =======================

    @Test
    @DisplayName("Deve lançar exceção quando nome for null")
    void shouldThrowExceptionWhenNameIsNull() {
        UUID id = UUID.randomUUID();
        String name = null;
        String description = "Descrição";
        BigDecimal price = new BigDecimal("10.00");
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(id, name, description, price, category, available)
        );

        assertEquals("Product name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for vazio")
    void shouldThrowExceptionWhenNameIsEmpty() {
        UUID id = UUID.randomUUID();
        String name = "";
        String description = "Descrição";
        BigDecimal price = new BigDecimal("10.00");
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(id, name, description, price, category, available)
        );

        assertEquals("Product name cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome exceder 100 caracteres")
    void shouldThrowExceptionWhenNameIsTooLong() {
        UUID id = UUID.randomUUID();
        String name = "A".repeat(101);
        String description = "Descrição";
        BigDecimal price = new BigDecimal("10.00");
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(id, name, description, price, category, available)
        );

        assertEquals("Product name is too long. Max length is 100", exception.getMessage());
    }

    // =======================
    // VALIDAÇÕES – DESCRIPTION
    // =======================

    @Test
    @DisplayName("Deve lançar exceção quando descrição for null")
    void shouldThrowExceptionWhenDescriptionIsNull() {
        UUID id = UUID.randomUUID();
        String name = "Produto";
        String description = null;
        BigDecimal price = new BigDecimal("10.00");
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(id, name, description, price, category, available)
        );

        assertEquals("Product description cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando descrição for vazia")
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        UUID id = UUID.randomUUID();
        String name = "Produto";
        String description = "";
        BigDecimal price = new BigDecimal("10.00");
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(id, name, description, price, category, available)
        );

        assertEquals("Product description cannot be null or empty", exception.getMessage());
    }

    // =======================
    // VALIDAÇÕES – PRICE
    // =======================

    @Test
    @DisplayName("Deve lançar exceção quando preço for null")
    void shouldThrowExceptionWhenPriceIsNull() {
        UUID id = UUID.randomUUID();
        String name = "Produto";
        String description = "Descrição";
        BigDecimal price = null;
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(id, name, description, price, category, available)
        );

        assertEquals("Product price must be not null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando preço for zero")
    void shouldThrowExceptionWhenPriceIsZero() {
        UUID id = UUID.randomUUID();
        String name = "Produto";
        String description = "Descrição";
        BigDecimal price = BigDecimal.ZERO;
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(id, name, description, price, category, available)
        );

        assertEquals("Product price must be greater than 0", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando preço for negativo")
    void shouldThrowExceptionWhenPriceIsNegative() {
        UUID id = UUID.randomUUID();
        String name = "Produto";
        String description = "Descrição";
        BigDecimal price = new BigDecimal("-1");
        Product.Category category = Product.Category.LANCHE;
        boolean available = true;

        DomainException exception = assertThrows(
                DomainException.class,
                () -> new Product(id, name, description, price, category, available)
        );

        assertEquals("Product price must be greater than 0", exception.getMessage());
    }

    // =======================
    // ENUM CATEGORY
    // =======================

    @Test
    @DisplayName("Deve cobrir todos os valores do enum Category")
    void shouldCoverAllCategoryEnumValues() {
        assertEquals(Product.Category.LANCHE, Product.Category.valueOf("LANCHE"));
        assertEquals(Product.Category.BEBIDA, Product.Category.valueOf("BEBIDA"));
        assertEquals(Product.Category.ACOMPANHAMENTO, Product.Category.valueOf("ACOMPANHAMENTO"));
        assertEquals(Product.Category.SOBREMESA, Product.Category.valueOf("SOBREMESA"));
    }
}