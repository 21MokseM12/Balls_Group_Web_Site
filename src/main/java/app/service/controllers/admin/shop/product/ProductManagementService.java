package app.service.controllers.admin.shop.product;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.category.CategoryService;
import app.service.controllers.admin.shop.clothing.ClothingSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductManagementService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ClothingSizeService clothingSizeService;


    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return Optional.ofNullable(productService.findById(id));
    }

    public ResponseEntity<String> addProduct(Product product) {
        if (productService.existsByTitle(product.getTitle())) {
            return ResponseEntity.ok("Товар с таким названием уже существует");
        } else if (!categoryService.existsByCategory(product.getCategory())) {
            return ResponseEntity.ok("Выбранная категория не найдена");
        } else {
            setProductTransientDependencies(product);
            productService.save(product);
            return ResponseEntity.ok("Товар успешно добавлен!");
        }
    }

    private void setProductTransientDependencies(Product product) {
        product.setCategory(categoryService.findByCategory(product.getCategory()));
        product.setClothingSize(
                product.getClothingSize().stream()
                        .map(size -> clothingSizeService.findBySize(size))
                        .collect(Collectors.toSet())
        );

    }

    public ResponseEntity<String> updateProduct(Product product) {
        if (productService.existsById(product.getId())) {
            Product previous = productService.findById(product.getId());
            setProductTransientDependencies(product);
            product.setProductPhotoLinks(previous.getProductPhotoLinks());
            productService.save(product);
            return ResponseEntity.ok("Товар был успешно обновлен!");
        } else {
            return ResponseEntity.ok("Товар не был найден");
        }
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        if (productService.existsById(id)) {
            productService.deleteById(id);
            return ResponseEntity.ok("Товар был успешно удален!");
        } else {
            return ResponseEntity.ok("Товар не найден");
        }
    }

    public List<Product> findAllProductsByCategory(Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return productService.findAllByCategory(category);
    }
}
