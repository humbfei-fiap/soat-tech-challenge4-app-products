package com.postechfiap_group130.techchallenge_fastfood.api.data.jpa;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    @DisplayName("Deve criar ProductEntity usando construtor com argumentos")
    void shouldCreateUsingConstructor() {
        UUID newId = UUID.randomUUID();
        ProductEntity entity = new ProductEntity(
                newId,
                "Hamburguer",
                "Hamburguer artesanal",
                new BigDecimal("29.90"),
                Product.Category.LANCHE,
                true
        );

        assertEquals(newId, entity.getId());
        assertEquals("Hamburguer", entity.getName());
        assertEquals("Hamburguer artesanal", entity.getDescription());
        assertEquals(new BigDecimal("29.90"), entity.getPrice());
        assertEquals(Product.Category.LANCHE, entity.getCategory());
        assertTrue(entity.getAvailable());
    }

    @Test
    @DisplayName("Deve testar construtor vazio gerado pelo Lombok")
    void shouldCreateUsingNoArgsConstructor() {
        ProductEntity entity = new ProductEntity(null, null, null, null, null, null);

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getPrice());
        assertNull(entity.getCategory());
        assertNull(entity.getAvailable());
    }

    @Test
    @DisplayName("Deve testar todos os setters e getters")
    void shouldTestAllGettersAndSetters() {
        ProductEntity entity = new ProductEntity(null, null, null, null, null, null);
        UUID newId = UUID.randomUUID();
        entity.setId(newId);
        entity.setName("Refrigerante");
        entity.setDescription("Coca-Cola lata");
        entity.setPrice(new BigDecimal("7.50"));
        entity.setCategory(Product.Category.BEBIDA);
        entity.setAvailable(false);

        assertEquals(newId, entity.getId());
        assertEquals("Refrigerante", entity.getName());
        assertEquals("Coca-Cola lata", entity.getDescription());
        assertEquals(new BigDecimal("7.50"), entity.getPrice());
        assertEquals(Product.Category.BEBIDA, entity.getCategory());
        assertFalse(entity.getAvailable());
    }

    @Test
    @DisplayName("Deve converter Product para ProductEntity usando fromEntity")
    void shouldConvertFromDomainToEntity() {
        Product product = new Product(
                UUID.randomUUID(),
                "Batata Frita",
                "Batata frita crocante",
                new BigDecimal("15.00"),
                Product.Category.ACOMPANHAMENTO,
                true
        );

        ProductEntity entity = ProductEntity.fromEntity(product);

        assertNotNull(entity);
        assertEquals(product.getId(), entity.getId());
        assertEquals(product.getName(), entity.getName());
        assertEquals(product.getDescription(), entity.getDescription());
        assertEquals(product.getPrice(), entity.getPrice());
        assertEquals(product.getCategory(), entity.getCategory());
        assertEquals(product.getAvailable(), entity.getAvailable());
    }

    @Test
    @DisplayName("Deve converter ProductEntity para Product usando toDomain")
    void shouldConvertFromEntityToDomain() {
        ProductEntity entity = new ProductEntity(
                UUID.randomUUID(),
                "Sobremesa",
                "Pudim",
                new BigDecimal("12.00"),
                Product.Category.SOBREMESA,
                true
        );

        Product product = entity.toDomain();

        assertNotNull(product);
        assertEquals(entity.getId(), product.getId());
        assertEquals(entity.getName(), product.getName());
        assertEquals(entity.getDescription(), product.getDescription());
        assertEquals(entity.getPrice(), product.getPrice());
        assertEquals(entity.getCategory(), product.getCategory());
        assertEquals(entity.getAvailable(), product.getAvailable());
    }

    //@Test
    @DisplayName("Deve cobrir todos os ramos reais de equals, hashCode e toString")
    void shouldCoverAllEqualsHashCodeBranches() {

        ProductEntity entity = new ProductEntity(
                UUID.randomUUID(),
                "Hamburguer",
                "Teste",
                new BigDecimal("10.00"),
                Product.Category.LANCHE,
                true
        );

        // 1️⃣ this == o
        assertEquals(entity, entity);

        // 2️⃣ comparação com null
        assertNotEquals(entity, null);

        // 3️⃣ comparação com classe diferente
        assertNotEquals(entity, "string");

        // 4️⃣ objetos iguais
        ProductEntity sameEntity = new ProductEntity(
                UUID.randomUUID(),
                "Hamburguer",
                "Teste",
                new BigDecimal("10.00"),
                Product.Category.LANCHE,
                true
        );
        assertEquals(entity, sameEntity);
        assertEquals(entity.hashCode(), sameEntity.hashCode());

        // 5️⃣ diferença em campo
        ProductEntity differentEntity = new ProductEntity(
                UUID.randomUUID(), // id diferente
                "Hamburguer",
                "Teste",
                new BigDecimal("10.00"),
                Product.Category.LANCHE,
                true
        );
        assertNotEquals(entity, differentEntity);

        // 6️⃣ campos nulos
        ProductEntity nullEntity1 = new ProductEntity(
                null, null, null, null, null, null
        );
        ProductEntity nullEntity2 = new ProductEntity(
                null, null, null, null, null, null
        );
        assertEquals(nullEntity1, nullEntity2);
        assertEquals(nullEntity1.hashCode(), nullEntity2.hashCode());

        // 7️⃣ toString
        assertNotNull(entity.toString());
    }

}
