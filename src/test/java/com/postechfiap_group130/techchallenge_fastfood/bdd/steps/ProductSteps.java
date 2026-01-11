package com.postechfiap_group130.techchallenge_fastfood.bdd.steps;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Product.Category;
import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.DomainException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;

public class ProductSteps {

    private String name;
    private String description;
    private BigDecimal price;
    private Category category;

    private Product createdProduct;
    private Exception capturedException;

    @Given("a product name {string}")
    public void a_product_name(String name) {
        this.name = name;
    }

    @Given("a product name longer than 100 characters")
    public void a_product_name_longer_than_100_characters() {
        this.name = "X".repeat(101);
    }

    @Given("a product description {string}")
    public void a_product_description(String description) {
        this.description = description;
    }

    @Given("a product price of {double}")
    public void a_product_price_of(Double price) {
        this.price = BigDecimal.valueOf(price);
    }

    @Given("a null product price")
    public void a_null_product_price() {
        this.price = null;
    }

    @Given("a product category {string}")
    public void a_product_category(String category) {
        this.category = Category.valueOf(category);
    }

    @Given("a null product category")
    public void a_null_product_category() {
        this.category = null;
    }

    @When("the product is created")
    public void the_product_is_created() {
        try {
            createdProduct = new Product(
                    UUID.randomUUID(),
                    name,
                    description,
                    price,
                    category,
                    true
            );
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the product should be created successfully")
    public void the_product_should_be_created_successfully() {
        assertNotNull(createdProduct);
        assertNull(capturedException);
    }

    @Then("a validation error should occur with message {string}")
    public void a_validation_error_should_occur_with_message(String expectedMessage) {
        assertNotNull(capturedException);
        assertTrue(capturedException instanceof DomainException);
        assertEquals(expectedMessage, capturedException.getMessage());
    }
}
