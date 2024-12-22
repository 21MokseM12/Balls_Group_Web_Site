package app.service.controllers.admin.shop.order.product.impl;

import app.domain.entites.shop.OrderedProduct;
import app.repository.shop.OrderedProductRepository;
import app.service.controllers.admin.shop.order.product.OrderedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderedProductServiceImpl implements OrderedProductService {

    @Autowired
    private OrderedProductRepository orderedProductRepository;

    @Override
    public Optional<OrderedProduct> findOrderedProductById(Long orderedProductId) {
        return orderedProductRepository.findById(orderedProductId);
    }

    @Override
    public Long save(OrderedProduct order) {
        return orderedProductRepository.save(order).getId();
    }
}
