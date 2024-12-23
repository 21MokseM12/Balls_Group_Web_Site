package app.service.controllers.admin.shop.shop_management;

import app.domain.dto.shop.OrderDTO;
import app.domain.entites.shop.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ShopService {

    Optional<Product> getProduct(Long id);

    Optional<Category> getCategory(Long id);

    Optional<ClothingSize> getClothingSize(Integer id);

    List<Product> getAllProducts();

    List<Category> getAllCategories();

    List<ClothingSize> getAllClothingSizes();

    ResponseEntity<String> deleteProduct(Long id);

    ResponseEntity<String> deleteCategory(Long id);

    ResponseEntity<String> deleteClothingSize(Integer id);

    ResponseEntity<String> addProduct(Product product);

    ResponseEntity<String> addCategory(Category category);

    ResponseEntity<String> addClothingSize(ClothingSize size);

    ResponseEntity<String> updateProduct(Product product);

    ResponseEntity<String> updateCategory(Category category);

    ResponseEntity<String> updateClothingSize(ClothingSize size);

    List<Product> findAllProductsByCategory(Long categoryId);

    ResponseEntity<String> decrementStock(Long productId, Integer decrementBy);

    ResponseEntity<String> addCustomer(Customer customer);

    ResponseEntity<String> addOrder(OrderDTO order);

    ResponseEntity<String> addOrderedProduct(OrderedProduct orderedProduct);

    List<Order> getAllOrders();

    ResponseEntity<String> deleteOrder(Long orderId);
}
