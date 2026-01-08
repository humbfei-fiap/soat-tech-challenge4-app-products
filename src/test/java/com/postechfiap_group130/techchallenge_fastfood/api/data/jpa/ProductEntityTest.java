package com.postechfiap_group130.techchallenge_fastfood.api.data.jpa;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    void constructorAndGettersSetters_shouldWorkCorrectly() {
        UUID id = UUID.randomUUID();
        String name = "Burger";
        String description = "Delicious burger";
        BigDecimal price = BigDecimal.valueOf(25.90);
        Product.Category category = Product.Category.LANCHE;
        Boolean available = true;

        ProductEntity entity = new ProductEntity(id, name, description, price, category, available);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals(price, entity.getPrice());
        assertEquals(category, entity.getCategory());
        assertEquals(available, entity.getAvailable());

        UUID newId = UUID.randomUUID();
        entity.setId(newId);
        assertEquals(newId, entity.getId());

        entity.setName("New Name");
        assertEquals("New Name", entity.getName());

        entity.setDescription("New Desc");
        assertEquals("New Desc", entity.getDescription());

        entity.setPrice(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, entity.getPrice());

        entity.setCategory(Product.Category.BEBIDA);
        assertEquals(Product.Category.BEBIDA, entity.getCategory());

        entity.setAvailable(false);
        assertFalse(entity.getAvailable());
    }

    @Test
    void equalsAndHashCode_shouldWorkCorrectly() {
        UUID id = UUID.randomUUID();

        ProductEntity e1 = new ProductEntity(id, "A", "B", BigDecimal.ONE, Product.Category.LANCHE, true);
        ProductEntity e2 = new ProductEntity(id, "X", "Y", BigDecimal.TEN, Product.Category.BEBIDA, false);

        // Same ID → must be equal
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());

        // Different ID → must NOT be equal
        ProductEntity e3 = new ProductEntity(UUID.randomUUID(), "A", "B", BigDecimal.ONE, Product.Category.LANCHE, true);
        assertNotEquals(e1, e3);
        
    }

    @Test
    void fromEntity_shouldConvertDomainToEntityCorrectly() {
        UUID id = UUID.randomUUID();
        Product product = new Product(
                id,
                "Pizza",
                "Cheese pizza",
                BigDecimal.valueOf(40.0),
                Product.Category.LANCHE,
                true
        );

        ProductEntity entity = ProductEntity.fromEntity(product);

        assertEquals(product.id(), entity.getId());
        assertEquals(product.name(), entity.getName());
        assertEquals(product.description(), entity.getDescription());
        assertEquals(product.price(), entity.getPrice());
        assertEquals(product.category(), entity.getCategory());
        assertEquals(product.available(), entity.getAvailable());
    }

    @Test
    void toDomain_shouldConvertEntityToDomainCorrectly() {
        UUID id = UUID.randomUUID();
        ProductEntity entity = new ProductEntity(
                id,
                "Soda",
                "Cold soda",
                BigDecimal.valueOf(8.50),
                Product.Category.BEBIDA,
                true
        );

        Product product = entity.toDomain();

        assertEquals(entity.getId(), product.id());
        assertEquals(entity.getName(), product.name());
        assertEquals(entity.getDescription(), product.description());
        assertEquals(entity.getPrice(), product.price());
        assertEquals(entity.getCategory(), product.category());
        assertEquals(entity.getAvailable(), product.available());
    }
}