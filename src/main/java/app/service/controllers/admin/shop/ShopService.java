package app.service.controllers.admin.shop;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ShopService {
    Optional<Product> findProductById(Long id);

    Optional<Category> findCategoryById(Long id);

    Optional<ClothingSize> findClothingSizeById(Integer id);

    List<Product> findAllProducts();

    List<Category> findAllCategories();

    List<ClothingSize> findAllClothingSizes();

    ResponseEntity<String> deleteProduct(Long id);

    ResponseEntity<String> deleteCategory(Long id);

    ResponseEntity<String> deleteClothingSize(Integer id);

    ResponseEntity<String> addProduct(Product product);

    ResponseEntity<String> addCategory(Category category);

    ResponseEntity<String> addClothingSize(ClothingSize size);

    ResponseEntity<String> updateProduct(Product product);

    ResponseEntity<String> updateCategory(Category category);

    ResponseEntity<String> updateClothingSize(ClothingSize size);
}
