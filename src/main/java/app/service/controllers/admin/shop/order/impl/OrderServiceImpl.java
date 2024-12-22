package app.service.controllers.admin.shop.order.impl;

import app.domain.entites.shop.Order;
import app.repository.shop.OrderRepository;
import app.service.controllers.admin.shop.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}
