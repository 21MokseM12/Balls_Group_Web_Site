package app.service.controllers.admin.shop.order.order;

import app.domain.entites.shop.Order;

import java.util.List;

public interface OrderService {
    void save(Order order);

    List<Order> findAll();

    void deleteById(Long orderId);
}
