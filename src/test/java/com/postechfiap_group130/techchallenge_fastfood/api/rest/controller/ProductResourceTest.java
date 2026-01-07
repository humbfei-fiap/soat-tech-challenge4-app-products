package com.postechfiap_group130.techchallenge_fastfood.api.rest.controller;

import com.postechfiap_group130.techchallenge_fastfood.api.data.DataRepository;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.ProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdateProductRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ProductResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import com.postechfiap_group130.techchallenge_fastfood.core.controllers.ProductController;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductResourceTest {

    @Mock
    private DataRepository dataRepository;

    @Test
    void getAll_shouldReturnOk_whenControllerReturnsList() {
        ProductResource resource = new ProductResource(dataRepository);

        ProductResponseDto responseDto = Mockito.mock(ProductResponseDto.class);
        List<ProductResponseDto> list = Collections.singletonList(responseDto);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getAll()).thenReturn(list))) {

            ResponseEntity<?> response = resource.GetAll();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(list, response.getBody());
            assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    void getAll_shouldReturnBadRequest_whenIllegalArgumentExceptionThrown() {
        ProductResource resource = new ProductResource(dataRepository);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getAll()).thenThrow(new IllegalArgumentException()))) {

            ResponseEntity<?> response = resource.GetAll();

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Invalid Category", response.getBody());
        }
    }

    @Test
    void getAll_shouldReturnInternalServerError_whenGenericExceptionThrown() {
        ProductResource resource = new ProductResource(dataRepository);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getAll()).thenThrow(new RuntimeException("error")))) {

            ResponseEntity<?> response = resource.GetAll();

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Test
    void getProductByCategory_shouldReturnBadRequest_whenCategoryIsNull() {
        ProductResource resource = new ProductResource(dataRepository);

        ResponseEntity<?> response = resource.GetProductByCategory(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid Category", response.getBody());
    }

    @Test
    void getProductByCategory_shouldReturnOk_whenControllerReturnsList() {
        ProductResource resource = new ProductResource(dataRepository);

        Product.Category category = Mockito.mock(Product.Category.class);
        ProductResponseDto responseDto = Mockito.mock(ProductResponseDto.class);
        List<ProductResponseDto> list = Collections.singletonList(responseDto);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getProductsByCategory(category)).thenReturn(list))) {

            ResponseEntity<?> response = resource.GetProductByCategory(category);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(list, response.getBody());
        }
    }

    @Test
    void getProductByCategory_shouldReturnBadRequest_whenIllegalArgumentExceptionThrown() {
        ProductResource resource = new ProductResource(dataRepository);

        Product.Category category = Mockito.mock(Product.Category.class);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getProductsByCategory(category))
                                     .thenThrow(new IllegalArgumentException()))) {

            ResponseEntity<?> response = resource.GetProductByCategory(category);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Invalid Category", response.getBody());
        }
    }

    @Test
    void getProductByCategory_shouldReturnInternalServerError_whenGenericExceptionThrown() {
        ProductResource resource = new ProductResource(dataRepository);

        Product.Category category = Mockito.mock(Product.Category.class);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.getProductsByCategory(category))
                                     .thenThrow(new RuntimeException("error")))) {

            ResponseEntity<?> response = resource.GetProductByCategory(category);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Test
    void create_shouldReturnCreatedWithResponseBody() throws Exception {
        ProductResource resource = new ProductResource(dataRepository);

        ProductRequestDto requestDto = Mockito.mock(ProductRequestDto.class);
        when(requestDto.getName()).thenReturn("Product 1");
        when(requestDto.getDescription()).thenReturn("Desc");
        when(requestDto.getPrice()).thenReturn(new BigDecimal("10.0"));
        when(requestDto.getCategory()).thenReturn(Mockito.mock(Product.Category.class));

        ProductResponseDto responseDto = Mockito.mock(ProductResponseDto.class);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> when(mock.createProduct(any(ProductDto.class)))
                                     .thenReturn(responseDto))) {

            ResponseEntity<ProductResponseDto> response = resource.create(requestDto);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertSame(responseDto, response.getBody());
            ProductController constructed = mocked.constructed().get(0);
            verify(constructed, times(1)).createProduct(any(ProductDto.class));
        }
    }

    @Test
    void updateProduct_shouldReturnOk_whenUpdateSucceeds() throws DomainException {
        ProductResource resource = new ProductResource(dataRepository);

        UpdateProductRequestDto updateDto = Mockito.mock(UpdateProductRequestDto.class);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> {
                                 // no-op, just don't throw
                             })) {

            ResponseEntity<?> response = resource.updateProduct(updateDto);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNull(response.getBody());
            ProductController constructed = mocked.constructed().get(0);
            verify(constructed, times(1)).updateProduct(updateDto);
        }
    }

    @Test
    void updateProduct_shouldReturnBadRequest_whenDomainExceptionThrown() throws DomainException {
        ProductResource resource = new ProductResource(dataRepository);

        UpdateProductRequestDto updateDto = Mockito.mock(UpdateProductRequestDto.class);
        DomainException domainException = new DomainException("Domain error");

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> doThrow(domainException).when(mock).updateProduct(updateDto))) {

            ResponseEntity<?> response = resource.updateProduct(updateDto);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals("Domain error", response.getBody());
        }
    }

    @Test
    void updateProduct_shouldReturnInternalServerError_whenGenericExceptionThrown() throws DomainException {
        ProductResource resource = new ProductResource(dataRepository);

        UpdateProductRequestDto updateDto = Mockito.mock(UpdateProductRequestDto.class);

        try (MockedConstruction<ProductController> mocked =
                     Mockito.mockConstruction(ProductController.class,
                             (mock, context) -> doThrow(new RuntimeException("error"))
                                     .when(mock).updateProduct(updateDto))) {

            ResponseEntity<?> response = resource.updateProduct(updateDto);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertNull(response.getBody());
        }
    }
}