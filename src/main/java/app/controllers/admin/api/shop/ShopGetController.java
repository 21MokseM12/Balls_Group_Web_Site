package app.controllers.admin.api.shop;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/edit-shop/get/")
public class ShopGetController {

    @Autowired
    private ShopService shopService;

    @GetMapping("product/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return shopService.findProductById(id);
    }

    @GetMapping("category/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id) {
        return shopService.findCategoryById(id);
    }

    @GetMapping("clothing-size/{id}")
    public Optional<ClothingSize> getClothingSizeById(@PathVariable Integer id) {
        return shopService.findClothingSizeById(id);
    }
}
