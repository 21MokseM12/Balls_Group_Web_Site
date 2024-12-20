package app.controllers.admin.api.shop;

import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/edit-shop/get-all-by/")
public class ShopFilterGetCollectionController {

    @Autowired
    private ShopService shopService;

    @GetMapping("product/category/{categoryId}")
    public List<Product> getAllProductsByCategory(@PathVariable Long categoryId) {
        return shopService.findAllProductsByCategory(categoryId);
    }
}
