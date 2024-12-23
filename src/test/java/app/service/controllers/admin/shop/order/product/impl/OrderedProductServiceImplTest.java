package app.service.controllers.admin.shop.order.product.impl;

import app.domain.entites.shop.OrderedProduct;
import app.repository.shop.OrderedProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderedProductServiceImplTest {

    @Mock
    private OrderedProductRepository orderedProductRepository;

    @InjectMocks
    private OrderedProductServiceImpl orderedProductService;

    private OrderedProduct orderedProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание тестового объекта OrderedProduct
        orderedProduct = new OrderedProduct();
        orderedProduct.setId(1L);
    }

    @Test
    void testFindOrderedProductById_Found() {
        // Arrange
        when(orderedProductRepository.findById(1L)).thenReturn(Optional.of(orderedProduct));

        // Act
        Optional<OrderedProduct> result = orderedProductService.findOrderedProductById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(orderedProduct, result.get());
        verify(orderedProductRepository, times(1)).findById(1L);
    }

    @Test
    void testFindOrderedProductById_NotFound() {
        // Arrange
        when(orderedProductRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<OrderedProduct> result = orderedProductService.findOrderedProductById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(orderedProductRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveOrderedProduct() {
        // Arrange
        when(orderedProductRepository.save(orderedProduct)).thenReturn(orderedProduct);

        // Act
        Long savedId = orderedProductService.save(orderedProduct);

        // Assert
        assertEquals(1L, savedId);
        verify(orderedProductRepository, times(1)).save(orderedProduct);
    }
}
