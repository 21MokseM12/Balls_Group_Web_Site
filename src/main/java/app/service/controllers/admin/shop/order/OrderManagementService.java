package app.service.controllers.admin.shop.order;

import app.domain.entites.shop.*;
import app.service.controllers.admin.shop.clothing.ClothingSizeService;
import app.service.controllers.admin.shop.customer.CustomerNotFoundException;
import app.service.controllers.admin.shop.customer.CustomerService;
import app.service.controllers.admin.shop.order.order.OrderService;
import app.service.controllers.admin.shop.order.product.OrderedProductException;
import app.service.controllers.admin.shop.order.product.OrderedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManagementService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClothingSizeService clothingSizeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderedProductService orderedProductService;

    public ResponseEntity<String> addOrder(OrderDTO orderDTO) {
        Customer customer = customerService.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        OrderedProduct orderedProduct = orderedProductService.findOrderedProductById(orderDTO.getOrderedProductId())
                .orElseThrow(() -> new OrderedProductException("ordered product not found"));
        orderService.save(
                new Order(
                        customer,
                        orderedProduct
                )
        );
        return ResponseEntity.ok("Ваш заказ успешно оформлен!");
    }

    public ResponseEntity<String> addOrderedProduct(OrderedProduct orderedProduct) {
        ClothingSize clothingSize = clothingSizeService.findBySize(orderedProduct.getSize().getSize());
        orderedProduct.setSize(clothingSize);
        Long orderedProductId = orderedProductService.save(orderedProduct);
        return ResponseEntity.ok(String.valueOf(orderedProductId));
    }

    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    public ResponseEntity<String> deleteOrder(Long orderId) {
        orderService.deleteById(orderId);
        return ResponseEntity.ok("Заказ успешно удален!");
    }
}
