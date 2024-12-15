package app.controllers.admin;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import app.repository.CategoryRepository;
import app.repository.ClothingSizeRepository;
import app.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/edit-shop/")
public class EditProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClothingSizeRepository clothingSizeRepository;

    @GetMapping("get-all/products/")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("get-all/categories/")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("get-all/clothing-sizes/")
    public List<ClothingSize> getAllClothingSizes() {
        return clothingSizeRepository.findAll();
    }

    @GetMapping("get/clothing-size/{id}")
    public Optional<ClothingSize> getClothingSizeById(@PathVariable Integer id) {
        return clothingSizeRepository.findById(id);
    }

    @GetMapping("get/category/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id);
    }

    @GetMapping("get/product/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id);
    }

    @PostMapping("add/product/")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        log.info("DTO: {}", product.getClothingSize());

        if (productRepository.existsByTitle(product.getTitle())) {
            return ResponseEntity.ok("Товар с таким названием уже существует");
        } else if (!categoryRepository.existsByCategory(product.getCategory().getCategory())) {
            return ResponseEntity.ok("Выбранная категория не найдена");
        } else {
            product.setCategory(
                    categoryRepository.findByCategory(
                            product.getCategory().getCategory()
                    )
            );
            product.setClothingSize(
                    product.getClothingSize().stream()
                            .map(size -> clothingSizeRepository.findBySize(size.getSize()))
                            .collect(Collectors.toSet())
            );

            productRepository.save(product);
            return ResponseEntity.ok("Товар успешно добавлен!");
        }
    }

    @PostMapping("add/category/")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        if (categoryRepository.existsByCategory(category.getCategory())) {
            return ResponseEntity.ok("Категория с таким названием уже существует");
        } else {
            categoryRepository.save(category);
            return ResponseEntity.ok("Категория успешно добавлена!");
        }
    }

    @PostMapping("add/clothing-size/")
    public ResponseEntity<String> addSize(@RequestBody ClothingSize size) {
        if (clothingSizeRepository.existsBySize(size.getSize())) {
            return ResponseEntity.ok("Такой размер уже существует");
        } else {
            clothingSizeRepository.save(size);
            return ResponseEntity.ok("Размер одежды успешно добавлен!");
        }
    }

    @PutMapping("update/product/")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        if (productRepository.existsById(product.getId())) {
            Product previous = productRepository.findById(product.getId()).orElseThrow();
            product.setCategory(
                    categoryRepository.findByCategory(
                            product.getCategory().getCategory()
                    )
            );
            product.setClothingSize(
                    product.getClothingSize().stream()
                            .map(size -> clothingSizeRepository.findBySize(size.getSize()))
                            .collect(Collectors.toSet())
            );
            product.setProductPhotoLinks(previous.getProductPhotoLinks());
            productRepository.save(product);
            return ResponseEntity.ok("Товар был успешно обновлен!");
        } else {
            return ResponseEntity.ok("Товар не был найден");
        }
    }

    @PutMapping("update/clothing-size/")
    public ResponseEntity<String> updateClothingSize(@RequestBody ClothingSize size) {
        if (clothingSizeRepository.existsById(size.getId())) {
            clothingSizeRepository.save(size);
            return ResponseEntity.ok("Размер одежды успешно обновлен");
        } else {
            return ResponseEntity.ok("Размер не был найден");
        }
    }

    @PutMapping("update/category/")
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {
        if (categoryRepository.existsById(category.getId())) {
            categoryRepository.save(category);
            return ResponseEntity.ok("Категория успешно обновлена");
        } else {
            return ResponseEntity.ok("Категория не был найдена");
        }
    }

    @DeleteMapping("delete/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Товар был успешно удален!");
        } else {
            return ResponseEntity.ok("Товар не найден");
        }
    }

    @DeleteMapping("delete/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok("Категория была успешно удалена!");
        } else {
            return ResponseEntity.ok("Категория не была найдена");
        }
    }

    @DeleteMapping("delete/clothing-size/{id}")
    public ResponseEntity<String> deleteClothingSize(@PathVariable Integer id) {
        if (clothingSizeRepository.existsById(id)) {
            clothingSizeRepository.deleteById(id);
            return ResponseEntity.ok("Размер был успешно удален!");
        } else {
            return ResponseEntity.ok("Размер не был найден");
        }
    }
}
