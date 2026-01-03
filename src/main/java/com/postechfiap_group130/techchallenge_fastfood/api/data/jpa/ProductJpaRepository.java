package com.postechfiap_group130.techchallenge_fastfood.api.data.jpa;

import java.util.List;
import java.util.UUID;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE category = :#{#category?.name()}")
    List<ProductEntity> getByCategory(Product.Category category);
    @Query(nativeQuery = true, value = "SELECT * FROM products")
    List<ProductEntity> getAll();
    Boolean existsByName(String name);
}
