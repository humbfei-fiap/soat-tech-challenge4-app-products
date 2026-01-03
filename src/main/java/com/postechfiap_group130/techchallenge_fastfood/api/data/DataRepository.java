package com.postechfiap_group130.techchallenge_fastfood.api.data;

import com.postechfiap_group130.techchallenge_fastfood.api.data.jpa.ProductEntity;
import com.postechfiap_group130.techchallenge_fastfood.api.data.jpa.ProductJpaRepository;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DataRepository implements DataSource {
    private final ProductJpaRepository productJpaRepository;


    public DataRepository(
            ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity(productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getCategory(),
                productDto.getAvailable());

        productEntity = productJpaRepository.save(productEntity);

        return new ProductDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getCategory(),
                productEntity.getAvailable()
        );
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        this.saveProduct(productDto);
        return productDto;
    }

    @Override
    public ProductDto findById(UUID id) {
        java.util.Optional<ProductEntity> entity = productJpaRepository.findById(id);

        return entity.map(productEntity -> new ProductDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getCategory(),
                productEntity.getAvailable())).orElse(null);

    }

    @Override
    public List<ProductDto> getAll() {

        List<ProductEntity> products = productJpaRepository.getAll();
        return products.stream()
                .map(item -> new ProductDto(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getCategory(),
                        item.getAvailable()
                ))
                .toList();
    }

    @Override
    public List<ProductDto> getByCategory(Product.Category category) {
        List<ProductEntity> products = productJpaRepository.getByCategory(category);
        return products.stream()
                .map(item -> new ProductDto(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getCategory(),
                        item.getAvailable()
                ))
                .toList();
    }

    @Override
    public Boolean existsByName(String name) {
        return productJpaRepository.existsByName(name);
    }
}
