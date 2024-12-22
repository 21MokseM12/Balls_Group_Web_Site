package app.controllers.admin.api.shop;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/edit-shop/update/")
public class ShopUpdateController {

    @Autowired
    private ShopService shopService;

    @PutMapping("product/")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        return shopService.updateProduct(product);
    }

    @PutMapping("category/")
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {
        return shopService.updateCategory(category);
    }

    @PutMapping("clothing-size/")
    public ResponseEntity<String> updateClothingSize(@RequestBody ClothingSize size) {
        return shopService.updateClothingSize(size);
    }

    @PostMapping("product/{productId}/decrement/stock/")
    public ResponseEntity<String> decrementStock(
            @PathVariable Long productId,
            @RequestBody Map<String, Integer> request
    ) {
        return shopService.decrementStock(
                productId,
                request.getOrDefault("decrementBy", 1)
        );
    }
}
