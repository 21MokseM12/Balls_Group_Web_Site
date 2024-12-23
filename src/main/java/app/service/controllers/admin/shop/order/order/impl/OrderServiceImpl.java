package app.service.controllers.admin.shop.order.order.impl;

import app.domain.entites.shop.Order;
import app.repository.shop.OrderRepository;
import app.service.controllers.admin.shop.order.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
