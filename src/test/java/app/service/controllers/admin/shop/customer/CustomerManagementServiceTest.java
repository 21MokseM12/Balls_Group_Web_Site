package app.service.controllers.admin.shop.customer;

import app.domain.entites.shop.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerManagementServiceTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerManagementService customerManagementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomer_NewCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerService.existsByName(customer.getName())).thenReturn(false);
        when(customerService.save(customer)).thenReturn(1L);

        // Act
        ResponseEntity<String> response = customerManagementService.addCustomer(customer);

        // Assert
        assertNotNull(response);
        assertEquals("1", response.getBody());
        verify(customerService, times(1)).existsByName(customer.getName());
        verify(customerService, times(1)).save(customer);
    }

    @Test
    void testAddCustomer_ExistingCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setName("Jane Doe");
        Customer existingCustomer = new Customer();
        existingCustomer.setId(2L);
        existingCustomer.setName("Jane Doe");

        when(customerService.existsByName(customer.getName())).thenReturn(true);
        when(customerService.findByName(customer.getName())).thenReturn(existingCustomer);

        // Act
        ResponseEntity<String> response = customerManagementService.addCustomer(customer);

        // Assert
        assertNotNull(response);
        assertEquals("2", response.getBody());
        verify(customerService, times(1)).existsByName(customer.getName());
        verify(customerService, times(0)).save(customer);
        verify(customerService, times(1)).findByName(customer.getName());
    }
}
