package app.service.controllers.admin.shop.category;

import app.domain.entites.shop.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryManagementService {

    @Autowired
    private CategoryService categoryService;

    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }

    public Optional<Category> findCategoryById(Long id) {
        return Optional.ofNullable(categoryService.findById(id));
    }

    public ResponseEntity<String> addCategory(Category category) {
        if (categoryService.existsByCategory(category)) {
            return ResponseEntity.ok("Категория с таким названием уже существует");
        } else {
            categoryService.save(category);
            return ResponseEntity.ok("Категория успешно добавлена!");
        }
    }

    public ResponseEntity<String> updateCategory(Category category) {
        if (categoryService.existsById(category.getId())) {
            categoryService.save(category);
            return ResponseEntity.ok("Категория успешно обновлена");
        } else {
            return ResponseEntity.ok("Категория не был найдена");
        }
    }

    public ResponseEntity<String> deleteCategory(Long id) {
        if (categoryService.existsById(id)) {
            categoryService.deleteById(id);
            return ResponseEntity.ok("Категория была успешно удалена!");
        } else {
            return ResponseEntity.ok("Категория не была найдена");
        }
    }
}
