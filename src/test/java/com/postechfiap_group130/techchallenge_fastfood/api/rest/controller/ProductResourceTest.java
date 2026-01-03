package com.postechfiap_group130.techchallenge_fastfood.api.rest.controller;

import com.postechfiap_group130.techchallenge_fastfood.api.data.DataRepository;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.ProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdateProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private ProductResource productResource;

    @BeforeEach
    void setup() {
        productResource = new ProductResource(dataRepository);
    }

    @Test
    void getAll_shouldReturnOk() {
        when(dataRepository.getAll()).thenReturn(List.of());

        ResponseEntity<?> response = productResource.GetAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAll_shouldReturnInternalServerError() {
        when(dataRepository.getAll()).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = productResource.GetAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getByCategory_shouldReturnOk() {
        when(dataRepository.getByCategory(Product.Category.LANCHE))
                .thenReturn(List.of());

        ResponseEntity<?> response =
                productResource.GetProductByCategory(Product.Category.LANCHE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //@Test
    void getByCategory_shouldReturnBadRequestForInvalidCategory() {
        ResponseEntity<?> response =
                productResource.GetProductByCategory(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    //@Test
    void create_shouldReturnCreated() throws Exception {
        ProductRequestDto request =
                new ProductRequestDto(
                        "Burger",
                        "Desc",
                        new BigDecimal("20.00"),
                        Product.Category.LANCHE
                );

        when(dataRepository.existsByName("Burger")).thenReturn(false);
        when(dataRepository.saveProduct(any())).thenReturn(null);

        ResponseEntity<ProductResponseDto> response =
                productResource.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    //@Test
    void update_shouldReturnOk() {
        UpdateProductRequestDto request =
                new UpdateProductRequestDto(
                        UUID.randomUUID(),
                        "Burger",
                        "Updated",
                        new BigDecimal("25.00"),
                        Product.Category.LANCHE,
                        true
                );

        ResponseEntity<?> response =
                productResource.updateProduct(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //@Test
    void update_shouldReturnBadRequestOnDomainException() throws DomainException{
        UpdateProductRequestDto request =
                new UpdateProductRequestDto(
                        UUID.randomUUID(),
                        null,
                        "",
                        new BigDecimal("25.00"),
                        Product.Category.LANCHE,
                        true
                );

        doThrow(new DomainException("Invalid data"))
                .when(dataRepository).updateProduct(any());

        ResponseEntity<?> response =
                productResource.updateProduct(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_shouldReturnInternalServerError() {
        UpdateProductRequestDto request =
                new UpdateProductRequestDto(
                        UUID.randomUUID(),
                        "Burger",
                        "Error",
                        new BigDecimal("25.00"),
                        Product.Category.LANCHE,
                        true
                );

        doThrow(RuntimeException.class)
                .when(dataRepository).updateProduct(any());

        ResponseEntity<?> response =
                productResource.updateProduct(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
