package com.postechfiap_group130.techchallenge_fastfood.api.rest.controller;

import com.postechfiap_group130.techchallenge_fastfood.api.data.DataRepository;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.ProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdateProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import com.postechfiap_group130.techchallenge_fastfood.core.controllers.ProductController;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductResourceTest {

    private DataRepository dataRepository;
    private ProductResource resource;

    @BeforeEach
    void setup() {
        dataRepository = mock(DataRepository.class);
        resource = new ProductResource(dataRepository);
    }

    @Test
    @DisplayName("Deve retornar produto por ID com status 200")
    void shouldReturnProductById() {
        UUID id = UUID.randomUUID();
        ProductResponseDto mockResponse = new ProductResponseDto(
                id, "Nome", "Desc", BigDecimal.TEN, Product.Category.LANCHE, true
        );

        try (MockedConstruction<ProductController> ignored =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getProductById(id)).thenReturn(mockResponse))) {

            ResponseEntity<ProductResponseDto> response = resource.getProductById(id);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockResponse, response.getBody());
        }
    }

    @Test
    @DisplayName("Deve retornar produto por nome com status 200")
    void shouldReturnProductByName() {
        ProductResponseDto mockResponse = new ProductResponseDto(
                UUID.randomUUID(), "Nome", "Desc", BigDecimal.TEN, Product.Category.LANCHE, true
        );

        try (MockedConstruction<ProductController> ignored =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getProductByName("Nome")).thenReturn(mockResponse))) {

            ResponseEntity<ProductResponseDto> response = resource.getProductByName("Nome");

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(mockResponse, response.getBody());
        }
    }

    @Test
    @DisplayName("Deve retornar 404 quando produto não encontrado por nome")
    void shouldReturnNotFoundWhenNameNotFound() {
        try (MockedConstruction<ProductController> ignored =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getProductByName("Inexistente")).thenReturn(null))) {

            ResponseEntity<ProductResponseDto> response = resource.getProductByName("Inexistente");

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Test
    @DisplayName("Deve retornar lista de produtos por categoria com status 201")
    void shouldReturnProductsByCategory() {
        List<ProductResponseDto> mockList = List.of(
                new ProductResponseDto(UUID.randomUUID(), "Nome", "Desc", BigDecimal.TEN, Product.Category.LANCHE, true)
        );

        try (MockedConstruction<ProductController> ignored =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getProductsByCategory(Product.Category.LANCHE)).thenReturn(mockList))) {

            ResponseEntity<List<ProductResponseDto>> response =
                    resource.getProductByCategory(Product.Category.LANCHE);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(mockList, response.getBody());
        }
    }

    @Test
    @DisplayName("Deve criar produto com status 201")
    void shouldCreateProduct() throws DomainException {
        ProductRequestDto request = new ProductRequestDto(
                "Nome", "Desc", BigDecimal.TEN, Product.Category.LANCHE
        );

        ProductResponseDto mockResponse = new ProductResponseDto(
                UUID.randomUUID(), "Nome", "Desc", BigDecimal.TEN, Product.Category.LANCHE, true
        );

        try (MockedConstruction<ProductController> ignored =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.createProduct(any(ProductDto.class))).thenReturn(mockResponse))) {

            ResponseEntity<ProductResponseDto> response = resource.create(request);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(mockResponse, response.getBody());
        }
    }

    @Test
    @DisplayName("Deve atualizar produto com status 200")
    void shouldUpdateProduct() throws DomainException {
        UpdateProductRequestDto request = new UpdateProductRequestDto(
                UUID.randomUUID(), "Nome", "Desc", BigDecimal.TEN, Product.Category.LANCHE, true
        );

        try (MockedConstruction<ProductController> ignored =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> doNothing().when(mock).updateProduct(request))) {

            ResponseEntity<ProductResponseDto> response = resource.updateProduct(request);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar produto")
    void shouldThrowExceptionOnCreate() throws DomainException {
        ProductRequestDto request = new ProductRequestDto(
                "Nome", "Desc", BigDecimal.TEN, Product.Category.LANCHE
        );

        try (MockedConstruction<ProductController> ignored =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.createProduct(any(ProductDto.class)))
                                     .thenThrow(new DomainException("Erro")))) {

            assertThrows(DomainException.class, () -> resource.create(request));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto")
    void shouldThrowExceptionOnUpdate() throws DomainException {
        UpdateProductRequestDto request = new UpdateProductRequestDto(
                UUID.randomUUID(), "Nome", "Desc", BigDecimal.TEN, Product.Category.LANCHE, true
        );

        try (MockedConstruction<ProductController> ignored =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> doThrow(new DomainException("Erro")).when(mock).updateProduct(request))) {

            assertThrows(DomainException.class, () -> resource.updateProduct(request));
        }
    }
}