package com.postechfiap_group130.techchallenge_fastfood.api.rest.controller;

import java.util.List;
import java.util.UUID;

import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.postechfiap_group130.techchallenge_fastfood.core.controllers.ProductController;
import com.postechfiap_group130.techchallenge_fastfood.api.data.DataRepository;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.ProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdateProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;

@RestController
@RequestMapping("/products")
public class ProductResource {
    private final DataRepository dataRepository;

    public ProductResource(DataRepository dataRepository) {
        this.dataRepository = dataRepository;

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        ProductController productController = new ProductController(dataRepository);
        ProductResponseDto productResponseDto = productController.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductResponseDto> getProductByName(@PathVariable String name) {
        ProductController productController = new ProductController(dataRepository);
        ProductResponseDto productResponseDto = productController.getProductByName(name);
        if (productResponseDto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> getProductByCategory(@PathVariable Product.Category category) {
        ProductController productController = new ProductController(dataRepository);
        List<ProductResponseDto> productResponseDto = productController.getProductsByCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto productRequestDto) throws DomainException {
        ProductDto produtoDto = new ProductDto(UUID.randomUUID(), productRequestDto.getName(),
                productRequestDto.getDescription(), productRequestDto.getPrice(), productRequestDto.getCategory(),
                true);
        ProductController productController = new ProductController(dataRepository);
        ProductResponseDto productResponseDto = productController.createProduct(produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody UpdateProductRequestDto updateProdutoDto) throws DomainException {
        ProductController productController = new ProductController(dataRepository);
        productController.updateProduct(updateProdutoDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
