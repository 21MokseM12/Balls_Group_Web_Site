package app.controllers.admin.api.shop;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import app.repository.CategoryRepository;
import app.repository.ClothingSizeRepository;
import app.repository.ProductRepository;
import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-shop/add/")
public class ShopPostController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ClothingSizeRepository clothingSizeRepository;

    @PostMapping("product/")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        return shopService.addProduct(product);
    }

    @PostMapping("category/")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        return shopService.addCategory(category);
    }

    @PostMapping("clothing-size/")
    public ResponseEntity<String> addSize(@RequestBody ClothingSize size) {
        return shopService.addClothingSize(size);
    }
}
