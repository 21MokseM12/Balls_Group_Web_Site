package app.service.controllers.admin.product;

import app.domain.entites.shop.Product;

import java.util.List;

public interface ProductService {
    Product findById(Long id);

    List<Product> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);

    void save(Product product);

    boolean existsByTitle(String title);
}
