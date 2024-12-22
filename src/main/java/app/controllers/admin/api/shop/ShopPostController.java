package app.controllers.admin.api.shop;

import app.domain.entites.shop.*;
import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/edit-shop/add/")
public class ShopPostController {

    @Autowired
    private ShopService shopService;

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

    @PostMapping("order/product/{productId}")
    public ResponseEntity<String> addOrder(
            @RequestBody Customer customer,
            @PathVariable Long productId
    ) {
        return shopService.addOrder(customer, productId);
    }
}
