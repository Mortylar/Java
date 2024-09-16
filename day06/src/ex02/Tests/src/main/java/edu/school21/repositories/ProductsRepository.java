package edu.school21.repositories;

import edu.school21.models.Product;
import java.util.List;
import java.util.Optional;

public interface ProductsRepository {

    public List<Product> findAll();

    public Optional<Product> findById(long id);

    public void update(Product product);

    public void save(Product product);

    public void delete(long id);
}
