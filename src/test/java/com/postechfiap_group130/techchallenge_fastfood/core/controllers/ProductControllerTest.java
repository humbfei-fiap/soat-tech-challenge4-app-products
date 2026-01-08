package com.postechfiap_group130.techchallenge_fastfood.core.controllers;

import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.ProductDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.ProductGateway;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import com.postechfiap_group130.techchallenge_fastfood.core.presenters.ProductPresenter;
import com.postechfiap_group130.techchallenge_fastfood.core.usecases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private DataSource dataSource;
    private ProductController controller;

    @BeforeEach
    void setup() {
        dataSource = mock(DataSource.class);
        controller = new ProductController(dataSource);
    }

    private Product product() {
        return new Product(
                UUID.randomUUID(),
                "Produto",
                "Descrição",
                BigDecimal.TEN,
                Product.Category.LANCHE,
                true
        );
    }

    private ProductDto response(Product p) {
        return new ProductDto(
                p.id(), p.name(), p.description(), p.price(), p.category(), p.available()
        );
    }

    // ============================================================
    // createProduct
    // ============================================================

    @Test
    @DisplayName("Deve criar produto com sucesso")
    void shouldCreateProduct() throws DomainException {
        ProductDto dto = new ProductDto(
                UUID.randomUUID(), "Produto", "Desc",
                BigDecimal.TEN, Product.Category.LANCHE, true
        );

        Product p = product();
        ProductDto r = response(p);

        try (
                MockedConstruction<ProductGateway> gw =
                        Mockito.mockConstruction(ProductGateway.class,
                                (mock, ctx) -> when(mock.existsByName(dto.getName())).thenReturn(false));

                MockedConstruction<RegisterProductUseCase> uc =
                        Mockito.mockConstruction(RegisterProductUseCase.class,
                                (mock, ctx) -> when(mock.execute(dto)).thenReturn(p));

                MockedStatic<ProductPresenter> presenter = Mockito.mockStatic(ProductPresenter.class)
        ) {
            presenter.when(() -> ProductPresenter.toDto(p)).thenReturn(r);

            assertEquals(r, controller.createProduct(dto));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar produto já existente")
    void shouldThrowWhenProductExists() {
        ProductDto dto = new ProductDto(
                UUID.randomUUID(), "Produto", "Desc",
                BigDecimal.TEN, Product.Category.LANCHE, true
        );

        try (
                MockedConstruction<ProductGateway> gw =
                        Mockito.mockConstruction(ProductGateway.class,
                                (mock, ctx) -> when(mock.existsByName(dto.getName())).thenReturn(true))
        ) {
            assertThrows(DomainException.class, () -> controller.createProduct(dto));
        }
    }

    // ============================================================
    // updateProduct
    // ============================================================

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void shouldUpdateProduct() throws DomainException {
        ProductDto req = new ProductDto(
                UUID.randomUUID(), "Nome", "Desc",
                BigDecimal.TEN, Product.Category.LANCHE, true
        );

        Product p = product();

        try (
                MockedConstruction<ProductGateway> gw =
                        Mockito.mockConstruction(ProductGateway.class);

                MockedConstruction<UpdateProductUseCase> uc =
                        Mockito.mockConstruction(UpdateProductUseCase.class,
                                (mock, ctx) -> when(mock.execute(req)).thenReturn(p));

                MockedStatic<ProductPresenter> presenter = Mockito.mockStatic(ProductPresenter.class)
        ) {
            presenter.when(() -> ProductPresenter.toDto(p)).thenReturn(response(p));

            assertDoesNotThrow(() -> controller.updateProduct(req));
        }
    }

    // ============================================================
    // getProductsByCategory
    // ============================================================

    @Test
    @DisplayName("Deve retornar lista de produtos por categoria")
    void shouldReturnProductsByCategory() {
        Product p = product();
        List<Product> list = List.of(p);
        List<ProductDto> resp = List.of(response(p));

        try (
                MockedConstruction<ProductGateway> gw =
                        Mockito.mockConstruction(ProductGateway.class);

                MockedConstruction<GetProductsByCategoryUseCase> uc =
                        Mockito.mockConstruction(GetProductsByCategoryUseCase.class,
                                (mock, ctx) -> when(mock.execute(Product.Category.LANCHE)).thenReturn(list));

                MockedStatic<ProductPresenter> presenter = Mockito.mockStatic(ProductPresenter.class)
        ) {
            presenter.when(() -> ProductPresenter.toDtoList(list)).thenReturn(resp);

            assertEquals(resp, controller.getProductsByCategory(Product.Category.LANCHE));
        }
    }

    // ============================================================
    // getProductById
    // ============================================================

    @Test
    @DisplayName("Deve retornar produto por ID")
    void shouldReturnProductById() {
        UUID id = UUID.randomUUID();
        Product p = product();
        ProductDto r = response(p);

        try (
                MockedConstruction<ProductGateway> gw =
                        Mockito.mockConstruction(ProductGateway.class);

                MockedConstruction<GetProductByIdUseCase> uc =
                        Mockito.mockConstruction(GetProductByIdUseCase.class,
                                (mock, ctx) -> when(mock.execute(id)).thenReturn(p));

                MockedStatic<ProductPresenter> presenter = Mockito.mockStatic(ProductPresenter.class)
        ) {
            presenter.when(() -> ProductPresenter.toDto(p)).thenReturn(r);

            assertEquals(r, controller.getProductById(id));
        }
    }

    // ============================================================
    // getProductByName
    // ============================================================

    @Test
    @DisplayName("Deve retornar produto por nome")
    void shouldReturnProductByName() {
        Product p = product();
        ProductDto r = response(p);

        try (
                MockedConstruction<ProductGateway> gw =
                        Mockito.mockConstruction(ProductGateway.class);

                MockedConstruction<GetProductByNameUseCase> uc =
                        Mockito.mockConstruction(GetProductByNameUseCase.class,
                                (mock, ctx) -> when(mock.execute("Produto")).thenReturn(p));

                MockedStatic<ProductPresenter> presenter = Mockito.mockStatic(ProductPresenter.class)
        ) {
            presenter.when(() -> ProductPresenter.toDto(p)).thenReturn(r);

            assertEquals(r, controller.getProductByName("Produto"));
        }
    }
}