package app.repository.shop;

import app.domain.entites.shop.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategory(String categoryName);

    Category findByCategory(String category);
}
