package app.controllers.admin.api.shop;

import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-shop/delete/")
public class ShopDeleteController {

    @Autowired
    private ShopService shopService;

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return shopService.deleteProduct(id);
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return shopService.deleteCategory(id);
    }

    @DeleteMapping("clothing-size/{id}")
    public ResponseEntity<String> deleteClothingSize(@PathVariable Integer id) {
        return shopService.deleteClothingSize(id);
    }
}
