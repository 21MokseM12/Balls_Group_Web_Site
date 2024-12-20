package app.repository.shop;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByTitle(String title);

    List<Product> findAllByCategory(Category category);
}
