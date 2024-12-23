package app.service.controllers.admin.shop.shop_management.impl;

import app.domain.dto.shop.OrderDTO;
import app.domain.entites.shop.*;
import app.service.controllers.admin.shop.category.CategoryManagementService;
import app.service.controllers.admin.shop.clothing.ClothingSizeManagementService;
import app.service.controllers.admin.shop.customer.CustomerManagementService;
import app.service.controllers.admin.shop.order.OrderManagementService;
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

    @Autowired
    private OrderManagementService orderService;

    @Autowired
    private CustomerManagementService customerService;


    @Override
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
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
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @Override
    public Optional<Category> getCategory(Long id) {
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
    public List<ClothingSize> getAllClothingSizes() {
        return clothingSizeService.findAllClothingSizes();
    }

    @Override
    public Optional<ClothingSize> getClothingSize(Integer id) {
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

    @Override
    public List<Product> findAllProductsByCategory(Long categoryId) {
        return productService.findAllProductsByCategory(categoryId);
    }

    @Override
    public ResponseEntity<String> decrementStock(Long productId, Integer decrementBy) {
        return productService.decrementStock(productId, decrementBy);
    }

    @Override
    public ResponseEntity<String> addCustomer(Customer customer) {
        return customerService.addCustomer(customer);
    }

    @Override
    public ResponseEntity<String> addOrder(OrderDTO order) {
        return orderService.addOrder(order);
    }

    @Override
    public ResponseEntity<String> addOrderedProduct(OrderedProduct orderedProduct) {
        return orderService.addOrderedProduct(orderedProduct);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Override
    public ResponseEntity<String> deleteOrder(Long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
