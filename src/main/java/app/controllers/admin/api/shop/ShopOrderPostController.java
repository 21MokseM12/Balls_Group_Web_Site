package app.controllers.admin.api.shop;

import app.domain.entites.shop.Customer;
import app.domain.entites.shop.Order;
import app.domain.entites.shop.OrderDTO;
import app.domain.entites.shop.OrderedProduct;
import app.service.controllers.admin.shop.shop_management.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edit-shop/add/order/")
public class ShopOrderPostController {

    @Autowired
    private ShopService shopService;

    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody OrderDTO order) {
        return shopService.addOrder(order);
    }

    @PostMapping("customer/")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        return shopService.addCustomer(customer);
    }

    @PostMapping("product/")
    public ResponseEntity<String> addOrderedProduct(
            @RequestBody OrderedProduct orderedProduct
    ) {
        return shopService.addOrderedProduct(orderedProduct);
    }
}
