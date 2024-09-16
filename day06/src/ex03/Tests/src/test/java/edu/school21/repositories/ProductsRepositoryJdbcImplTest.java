package edu.school21.repositories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import edu.school21.exception.BadConnectionException;
import edu.school21.exception.NotFoundEntityException;
import edu.school21.exception.NotSavedSubEntityException;
import edu.school21.models.Product;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class ProductsRepositoryJdbcImplTest {

    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS =

        Stream
            .of(new Product(1L, "Product_1", 100.0),
                new Product(2L, "Product_2", 200.0),
                new Product(3L, "Product_4", 155.0),
                new Product(4L, "Product_5", 17.0),
                new Product(5L, "Product_6", 228.0),
                new Product(6L, "Product_9", 4.0))
            .collect(Collectors.toList());

    private static final long PRODUCT_ID_FOR_FIND_BY_ID = 1L;
    private static final long INCORRECT_ID = -1L;
    private static final Product EXPECTED_FIND_BY_ID_PRODUCT =
        new Product(1L, "Product_1", 100.0);
    private static final Product EXPECTED_UPDATE_PRODUCT =
        new Product(1L, "Up_Product", 10000.0);

    private static final Product EXPECTED_SAVE_PRODUCT =
        new Product(7L, "Product_Saved", 1.0);

    private ProductsRepositoryJdbcImpl productRepository_;

    @BeforeEach
    public void init() {
        try {
            DataSource db = new EmbeddedDatabaseBuilder()
                                .generateUniqueName(true)
                                .setType(EmbeddedDatabaseType.HSQL)
                                .setScriptEncoding("UTF-8")
                                .ignoreFailedDrops(true)
                                .addScript("schema.sql")
                                .addScript("data.sql")
                                .build();
            productRepository_ = new ProductsRepositoryJdbcImpl(db);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void findAllTest() {
        List<Product> result = productRepository_.findAll();
        assertEquals(result.size(), EXPECTED_FIND_ALL_PRODUCTS.size());
        for (int i = 0; i < result.size(); ++i) {
            Product one = result.get(i);
            Product two = EXPECTED_FIND_ALL_PRODUCTS.get(i);
            assertEqualsProducts(one, two);
        }
    }

    @Test
    public void findAllExceptionTest() {
        DataSource db = new EmbeddedDatabaseBuilder()
                            .generateUniqueName(true)
                            .setType(EmbeddedDatabaseType.HSQL)
                            .setScriptEncoding("UTF-8")
                            .ignoreFailedDrops(true)
                            .addScript("schema.sql")
                            .build();
        ProductsRepositoryJdbcImpl pr = new ProductsRepositoryJdbcImpl(db);
        assertThrows(NotFoundEntityException.class, () -> pr.findAll());
    }

    @Test
    public void badConnectionExceptionTest() {
        DataSource db = new EmbeddedDatabaseBuilder()
                            .generateUniqueName(true)
                            .setType(EmbeddedDatabaseType.HSQL)
                            .setScriptEncoding("UTF-8")
                            .ignoreFailedDrops(true)
                            .build();
        ProductsRepositoryJdbcImpl pr = new ProductsRepositoryJdbcImpl(db);
        assertThrows(BadConnectionException.class, () -> pr.findAll());
    }

    @Test
    public void badConnectionByNullExceptionTest() {
        ProductsRepositoryJdbcImpl pr = new ProductsRepositoryJdbcImpl(null);
        assertThrows(BadConnectionException.class, () -> pr.findAll());
    }

    @Test
    public void findByIdTest() {
        Optional<Product> result =
            productRepository_.findById(PRODUCT_ID_FOR_FIND_BY_ID);
        result.ifPresentOrElse(
            product
            -> assertEqualsProducts(product, EXPECTED_FIND_BY_ID_PRODUCT),
            () -> fail("Product not found\n"));
    }

    @Test
    public void findByIdExceptionTest() {
        assertThrows(NotFoundEntityException.class,
                     () -> productRepository_.findById(INCORRECT_ID));
    }

    @Test
    public void updateTest() {
        productRepository_.update(EXPECTED_UPDATE_PRODUCT);
        Optional<Product> result =
            productRepository_.findById(EXPECTED_UPDATE_PRODUCT.getId());
        result.ifPresentOrElse(
            product
            -> assertEqualsProducts(product, EXPECTED_UPDATE_PRODUCT),
            () -> fail("Product not found\n"));
    }

    @Test
    public void updateExceptionTest() {
        assertThrows(
            NotSavedSubEntityException.class,
            () -> productRepository_.update(new Product(INCORRECT_ID)));
    }

    @Test
    public void saveTest() {
        productRepository_.save(EXPECTED_SAVE_PRODUCT);
        Optional<Product> result =
            productRepository_.findById(EXPECTED_SAVE_PRODUCT.getId());
        result.ifPresentOrElse(
            product
            -> assertEqualsProducts(product, EXPECTED_SAVE_PRODUCT),
            () -> fail("Product not found\n"));
    }

    @Test
    public void saveExceptionIncorrectIdTest() {
        Product incorrectProduct = new Product(INCORRECT_ID);
        incorrectProduct.setName("Name");
        incorrectProduct.setPrice(1.0);
        assertThrows(NotSavedSubEntityException.class,
                     () -> productRepository_.update(incorrectProduct));
    }

    @Test
    public void saveExceptionInvalidQueryTest() {
        Product incorrectProduct = new Product(EXPECTED_SAVE_PRODUCT.getId());
        incorrectProduct.setName("\b\b';\n");
        incorrectProduct.setPrice(1.0);
        assertThrows(NotSavedSubEntityException.class,
                     () -> productRepository_.update(incorrectProduct));
    }

    @Test
    public void saveExceptionInvalidPriceTest() {
        Product incorrectProduct = new Product(EXPECTED_SAVE_PRODUCT.getId());
        incorrectProduct.setName("Name");
        incorrectProduct.setPrice(-1.0);
        assertThrows(NotSavedSubEntityException.class,
                     () -> productRepository_.update(incorrectProduct));
    }

    @Test
    public void saveExceptionInvalidProductTest() {
        assertThrows(NotSavedSubEntityException.class,
                     () -> productRepository_.update(null));
    }

    @Test
    public void saveExceptionTest() {
        Product incorrectProduct = new Product(INCORRECT_ID);
        incorrectProduct.setName(null);
        incorrectProduct.setPrice(-1);
        assertThrows(NotSavedSubEntityException.class,
                     () -> productRepository_.update(incorrectProduct));
    }

    @Test
    public void deleteTest() {
        productRepository_.delete(EXPECTED_SAVE_PRODUCT.getId());
        List<Product> resultList = productRepository_.findAll();
        assertEquals(resultList.size(), EXPECTED_FIND_ALL_PRODUCTS.size());
        for (int i = 0; i < resultList.size(); ++i) {
            assertEqualsProducts(resultList.get(i),
                                 EXPECTED_FIND_ALL_PRODUCTS.get(i));
        }
    }

    private void assertEqualsProducts(Product first, Product second) {
        assertEquals(first.getId(), second.getId());
        assertEquals(first.getName(), second.getName());
        assertEquals(first.getPrice(), second.getPrice());
    }
}
