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

    @GetMapping("/getall")
    public ResponseEntity<?> GetAll() {
        try {
            ProductController productController = new ProductController(dataRepository);
            List<ProductResponseDto> productResponseDto = productController.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Category");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<?> GetProductByCategory(@PathVariable Product.Category category) {
        if (category == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Category");
        try {
            ProductController productController = new ProductController(dataRepository);
            List<ProductResponseDto> productResponseDto = productController.getProductsByCategory(category);
            return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);    
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Category");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto productRequestDto) throws Exception {
        ProductDto produtoDto = new ProductDto(UUID.randomUUID(), productRequestDto.getName(),
                productRequestDto.getDescription(), productRequestDto.getPrice(), productRequestDto.getCategory(),
                true);
        ProductController productController = new ProductController(dataRepository);
        ProductResponseDto productResponseDto = productController.createProduct(produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductRequestDto updateProdutoDto) throws DomainException {
        try {
            ProductController productController = new ProductController(dataRepository);
            productController.updateProduct(updateProdutoDto);
            return ResponseEntity.status(HttpStatus.OK).body(null);    
        }  catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
