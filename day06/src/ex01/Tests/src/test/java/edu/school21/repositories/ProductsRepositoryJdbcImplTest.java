package edu.school21.repositories;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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

    private ProductsRepositoryJdbcImpl productRepository_;
    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS =

        Stream
            .of(new Product(1L, "Product_1", 100.0),
                new Product(2L, "Product_2", 200.0),
                new Product(3L, "Product_4", 155.0),
                new Product(4L, "Product_5", 17.0),
                new Product(5L, "Product_6", 228.0),
                new Product(6L, "Product_9", 4.0))
            .collect(Collectors.toList());

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
            assertEquals(one.getId(), two.getId());
            assertEquals(one.getName(), two.getName());
            assertEquals(one.getPrice(), two.getPrice());
        }
    }
}
