package app.service.controllers.admin.shop.shop_management.impl;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.category.CategoryManagementService;
import app.service.controllers.admin.shop.clothing.ClothingSizeManagementService;
import app.service.controllers.admin.shop.product.ProductManagementService;
import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private CategoryManagementService categoryService;

    @Autowired
    private ClothingSizeManagementService clothingSizeService;

    @Autowired
    private ProductManagementService productService;


    @Override
    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productService.findProductById(id);
    }

    @Override
    public ResponseEntity<String> addProduct(Product product) {
        return productService.addProduct(product);
    }

    @Override
    public ResponseEntity<String> updateProduct(Product product) {
        return productService.updateProduct(product);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        return productService.deleteProduct(id);
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return categoryService.findCategoryById(id);
    }

    @Override
    public ResponseEntity<String> addCategory(Category category) {
        return categoryService.addCategory(category);
    }

    @Override
    public ResponseEntity<String> updateCategory(Category category) {
        return categoryService.updateCategory(category);
    }

    @Override
    public ResponseEntity<String> deleteCategory(Long id) {
        return categoryService.deleteCategory(id);
    }

    @Override
    public List<ClothingSize> findAllClothingSizes() {
        return clothingSizeService.findAllClothingSizes();
    }

    @Override
    public Optional<ClothingSize> findClothingSizeById(Integer id) {
        return clothingSizeService.findClothingSizeById(id);
    }

    @Override
    public ResponseEntity<String> addClothingSize(ClothingSize size) {
        return clothingSizeService.addClothingSize(size);
    }

    @Override
    public ResponseEntity<String> deleteClothingSize(Integer id) {
        return clothingSizeService.deleteClothingSize(id);
    }

    @Override
    public ResponseEntity<String> updateClothingSize(ClothingSize size) {
        return clothingSizeService.updateClothingSize(size);
    }
}
