package app.service.controllers.admin.shop.order;

import app.domain.dto.shop.OrderDTO;
import app.domain.entites.shop.*;
import app.service.controllers.admin.shop.clothing.ClothingSizeService;
import app.service.controllers.admin.shop.customer.CustomerNotFoundException;
import app.service.controllers.admin.shop.customer.CustomerService;
import app.service.controllers.admin.shop.order.order.OrderService;
import app.service.controllers.admin.shop.order.product.OrderedProductException;
import app.service.controllers.admin.shop.order.product.OrderedProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderManagementServiceTest {

    @Mock
    private OrderService orderService;

    @Mock
    private ClothingSizeService clothingSizeService;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderedProductService orderedProductService;

    @InjectMocks
    private OrderManagementService orderManagementService;

    private Customer customer;
    private OrderedProduct orderedProduct;
    private ClothingSize clothingSize;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание тестовых объектов
        customer = new Customer();
        customer.setId(1L);

        orderedProduct = new OrderedProduct();
        orderedProduct.setId(1L);
        orderedProduct.setSize(new ClothingSize("M"));

        clothingSize = new ClothingSize("M");
        orderDTO = new OrderDTO(1L, 1L);  // customerId и orderedProductId
    }

    @Test
    void testAddOrder_CustomerNotFound() {
        // Arrange
        when(customerService.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () ->
                orderManagementService.addOrder(orderDTO));
    }

    @Test
    void testAddOrder_OrderedProductNotFound() {
        // Arrange
        when(customerService.findById(1L)).thenReturn(Optional.of(customer));
        when(orderedProductService.findOrderedProductById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OrderedProductException.class, () ->
                orderManagementService.addOrder(orderDTO));
    }

    @Test
    void testAddOrder_Success() {
        // Arrange
        when(customerService.findById(1L)).thenReturn(Optional.of(customer));
        when(orderedProductService.findOrderedProductById(1L)).thenReturn(Optional.of(orderedProduct));

        // Act
        ResponseEntity<String> response = orderManagementService.addOrder(orderDTO);

        // Assert
        assertEquals("Ваш заказ успешно оформлен!", response.getBody());
        verify(orderService, times(1)).save(any(Order.class));
    }

    @Test
    void testAddOrderedProduct_Success() {
        // Arrange
        when(clothingSizeService.findBySize("M")).thenReturn(clothingSize);
        when(orderedProductService.save(any(OrderedProduct.class))).thenReturn(1L);

        // Act
        ResponseEntity<String> response = orderManagementService.addOrderedProduct(orderedProduct);

        // Assert
        assertEquals("1", response.getBody());
        verify(orderedProductService, times(1)).save(orderedProduct);
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        when(orderService.findAll()).thenReturn(List.of(new Order(customer, orderedProduct)));

        // Act
        List<Order> orders = orderManagementService.getAllOrders();

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    void testDeleteOrder_Success() {
        // Arrange
        doNothing().when(orderService).deleteById(1L);

        // Act
        ResponseEntity<String> response = orderManagementService.deleteOrder(1L);

        // Assert
        assertEquals("Заказ успешно удален!", response.getBody());
        verify(orderService, times(1)).deleteById(1L);
    }
}
