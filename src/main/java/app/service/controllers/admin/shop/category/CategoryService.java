package app.service.controllers.admin.shop.category;

import app.domain.entites.shop.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Long id);

    List<Category> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);

    void save(Category category);

    boolean existsByCategory(Category category);

    Category findByCategory(Category category);
}
