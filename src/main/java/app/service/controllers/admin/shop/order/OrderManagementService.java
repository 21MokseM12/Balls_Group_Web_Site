package app.service.controllers.admin.shop.order;

import app.domain.entites.shop.Customer;
import app.domain.entites.shop.Order;
import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.customer.CustomerService;
import app.service.controllers.admin.shop.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderManagementService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    public ResponseEntity<String> addOrder(Customer customer, Long productId) {
        Product product = productService.findById(productId);
        if (!customerService.existsByName(customer.getName())) {
            customerService.save(customer);
        } else {
            customer = customerService.findByName(customer.getName());
        }
        orderService.save(
                new Order(customer, product)
        );
        return ResponseEntity.ok("Ваш заказ успешно оформлен!");
    }
}
