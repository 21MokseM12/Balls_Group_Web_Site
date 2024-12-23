package app.service.controllers.admin.shop.customer.impl;

import app.domain.entites.shop.Customer;
import app.repository.shop.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testSave() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Long customerId = customerService.save(customer);

        // Assert
        assertNotNull(customerId);
        assertEquals(1L, customerId);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testExistsByName_True() {
        // Arrange
        String customerName = "John Doe";
        when(customerRepository.existsByName(customerName)).thenReturn(true);

        // Act
        boolean exists = customerService.existsByName(customerName);

        // Assert
        assertTrue(exists);
        verify(customerRepository, times(1)).existsByName(customerName);
    }

    @Test
    void testExistsByName_False() {
        // Arrange
        String customerName = "Jane Doe";
        when(customerRepository.existsByName(customerName)).thenReturn(false);

        // Act
        boolean exists = customerService.existsByName(customerName);

        // Assert
        assertFalse(exists);
        verify(customerRepository, times(1)).existsByName(customerName);
    }

    @Test
    void testFindByName_Found() {
        // Arrange
        String customerName = "John Doe";
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName(customerName);
        when(customerRepository.findByName(customerName)).thenReturn(customer);

        // Act
        Customer foundCustomer = customerService.findByName(customerName);

        // Assert
        assertNotNull(foundCustomer);
        assertEquals(customerName, foundCustomer.getName());
        verify(customerRepository, times(1)).findByName(customerName);
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        String customerName = "Non Existent";
        when(customerRepository.findByName(customerName)).thenReturn(null);

        // Act
        Customer foundCustomer = customerService.findByName(customerName);

        // Assert
        assertNull(foundCustomer);
        verify(customerRepository, times(1)).findByName(customerName);
    }

    @Test
    void testFindById_Found() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> foundCustomer = customerService.findById(customerId);

        // Assert
        assertTrue(foundCustomer.isPresent());
        assertEquals(customerId, foundCustomer.get().getId());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        Long customerId = 999L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> foundCustomer = customerService.findById(customerId);

        // Assert
        assertFalse(foundCustomer.isPresent());
        verify(customerRepository, times(1)).findById(customerId);
    }
}
