package edu.school21.repositories;

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
import javax.sql.DataSource;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private static final int ID_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int PRICE_INDEX = 3;

    private DataSource dataSource_;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        dataSource_ = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<Product>();
        String query = "SELECT * FROM Product ORDER BY id;";
        ResultSet productTable = getResultSet(query);
        try {
            while (!productTable.isLast()) {
                productTable.next();
                productList.add(extractProduct(productTable));
            }
        } catch (SQLException e) {
            throw new NotFoundEntityException();
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(long id) {
        Product product = null;
        String query =
            String.format("SELECT * FROM Product WHERE id = %d;", id);
        ResultSet resultLine = getResultSet(query);
        try {
            resultLine.next();
            product = extractProduct(resultLine);
        } catch (SQLException e) {
            throw new NotFoundEntityException();
        }
        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) {
        checkProduct(product);
        String query = String.format(
            "UPDATE Product SET name = %s, price = %f WHERE id = %d",
            product.getName(), product.getPrice(), product.getId());

        updateDataBase(query);
    }

    @Override
    public void save(Product product) {
        checkProduct(product);
        String query =
            String.format("INSERT INTO Product(Name, Price) VALUES('%s', %f);",
                          product.getName(), product.getPrice());

        updateDataBase(query);
    }

    @Override
    public void delete(long id) {
        String query = String.format("DELETE FROM Product"
                                         + " WHERE Id = %d ",
                                     id);
        updateDataBase(query);
    }

    private ResultSet getResultSet(String query) {
        try {
            return getStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new BadConnectionException(e.getMessage());
        }
        // return null;
    }

    private void updateDataBase(String query) {
        try {
            getStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new NotSavedSubEntityException();
        }
    }

    private Statement getStatement() throws SQLException {
        try {
            Connection connection = dataSource_.getConnection();
            return connection.createStatement();
        } catch (SQLException e) {
            throw new BadConnectionException(e.getMessage());
        }
        // return null;
    }

    private Product extractProduct(ResultSet line) throws SQLException {
        return new Product(line.getLong(ID_INDEX), line.getString(NAME_INDEX),
                           line.getDouble(PRICE_INDEX));
    }

    private void checkProduct(Product product) {
        if (null == product) {
            throw new NotSavedSubEntityException();
        }

        if (null == product.getName()) {
            throw new NotSavedSubEntityException();
        }

        final int MAX_INVALID_ID = 0;

        if ((product.getId() <= MAX_INVALID_ID) || (product.getPrice() < 0)) {
            throw new NotSavedSubEntityException();
        }
    }
}
