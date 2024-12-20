package app.service.controllers.admin.shop.category.impl;

import app.domain.entites.shop.Category;
import app.repository.shop.CategoryRepository;
import app.service.controllers.admin.shop.category.CategoryException;
import app.service.controllers.admin.shop.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Category was not found");
                    return new CategoryException("Категория не была найдена");
                });
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public boolean existsByCategory(Category category) {
        return categoryRepository.existsByCategory(category.getCategory());
    }

    @Override
    public Category findByCategory(Category category) {
        return categoryRepository.findByCategory(category.getCategory());
    }
}
