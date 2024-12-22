package app.service.controllers.admin.shop.order.product;

import app.domain.entites.shop.OrderedProduct;

import java.util.Optional;

public interface OrderedProductService {
    Optional<OrderedProduct> findOrderedProductById(Long orderedProductId);

    Long save(OrderedProduct orderedProduct);
}
