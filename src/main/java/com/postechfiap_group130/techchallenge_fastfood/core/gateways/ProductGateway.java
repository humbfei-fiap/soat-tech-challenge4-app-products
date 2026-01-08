package com.postechfiap_group130.techchallenge_fastfood.core.gateways;

import java.util.List;
import java.util.UUID;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.IProductGateway;

public class ProductGateway implements IProductGateway {

    private final DataSource dataSource;

    public ProductGateway(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Product saveProduct(Product product) {
        ProductDto productDto = new ProductDto(product.id(), product.name(), product.description(), product.price(), product.category(), product.available());
        productDto = dataSource.saveProduct(productDto);
        product = new Product(productDto.getId(), productDto.getName(), productDto.getDescription(),
                productDto.getPrice(), productDto.getCategory(), productDto.getAvailable());
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        ProductDto productDto = new ProductDto(product.id(), product.name(), product.description(), product.price(), product.category(), product.available());
        productDto = dataSource.updateProduct(productDto);
        product = new Product(productDto.getId(), productDto.getName(), productDto.getDescription(),
                productDto.getPrice(), productDto.getCategory(), productDto.getAvailable());
        return product;
    }

    @Override
    public Product findById(UUID id) {
        ProductDto productDto = dataSource.findById(id);
        if (productDto == null) return null;

        return new Product(productDto.getId(), productDto.getName(), productDto.getDescription(),
                productDto.getPrice(), productDto.getCategory(), productDto.getAvailable());
    }

    @Override
    public Product findByName(String name) {
        ProductDto productDto = dataSource.findByName(name);
        if (productDto == null) return null;

        return new Product(productDto.getId(), productDto.getName(), productDto.getDescription(),
                productDto.getPrice(), productDto.getCategory(), productDto.getAvailable());
    }

    @Override
    public List<Product> findAllByCategory(Product.Category category) {
        List<ProductDto> productDTOs = dataSource.getByCategory(category);
        return productDTOs.stream()
                .map(productDto ->
                        new Product(productDto.getId(), productDto.getName(), productDto.getDescription(),
                                productDto.getPrice(), productDto.getCategory(), productDto.getAvailable()))
                .toList();
    }

    @Override
    public Boolean existsByName(String name) {
        return dataSource.existsByName(name);
    }
}
