package app.service.controllers.admin.shop.order.order.impl;

import app.domain.entites.shop.Order;
import app.repository.shop.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем тестовые заказы
        order1 = new Order();
        order1.setId(1L);
        order2 = new Order();
        order2.setId(2L);
    }

    @Test
    void testSaveOrder() {
        // Arrange
        Order orderToSave = new Order();
        orderToSave.setId(1L);

        // Act
        orderService.save(orderToSave);

        // Assert
        verify(orderRepository, times(1)).save(orderToSave);
    }

    @Test
    void testFindAllOrders() {
        // Arrange
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Act
        List<Order> orders = orderService.findAll();

        // Assert
        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testDeleteOrderById() {
        // Arrange
        Long orderId = 1L;

        // Act
        orderService.deleteById(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void testDeleteOrderById_NonExistingOrder() {
        // Arrange
        Long nonExistingOrderId = 999L;

        // Act
        orderService.deleteById(nonExistingOrderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(nonExistingOrderId);
    }
}
