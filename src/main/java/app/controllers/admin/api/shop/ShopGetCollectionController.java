package app.controllers.admin.api.shop;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edit-shop/get-all/")
public class ShopGetCollectionController {

    @Autowired
    private ShopService shopService;

    @GetMapping("products/")
    public List<Product> getAllProducts() {
        return shopService.findAllProducts();
    }

    @GetMapping("categories/")
    public List<Category> getAllCategories() {
        return shopService.findAllCategories();
    }

    @GetMapping("clothing-sizes/")
    public List<ClothingSize> getAllClothingSizes() {
        return shopService.findAllClothingSizes();
    }
}
