import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.model.Order;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.repo.EmployeeRepository;
import com.epam.rd.autocode.spring.project.repo.OrderRepository;
import com.epam.rd.autocode.spring.project.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // <--- THIS IS THE MISSING KEY
@MockitoSettings(strictness = Strictness.LENIENT) // <--- THIS PREVENTS THE UNNECESSARY STUBBING ERROR
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getOrdersByEmployee_ShouldReturnOrders_WhenEmployeeExists() {

        String email = "employee@test.com";
        Employee employee = new Employee();
        employee.setId(1L);
        List<Order> orders = List.of(new Order());

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(employee));
        when(orderRepository.findByEmployeeId(1L)).thenReturn(orders);

        List<OrderDTO> result = orderService.getOrdersByEmployee(email);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository).findByEmail(email);
    }

    @Test
    void getOrdersByEmployee_ShouldThrowException_WhenEmployeeNotFound() {
        String email = "unknown@test.com";
        when(employeeRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderService.getOrdersByEmployee(email);
        });
    }

    @Test
    void getOrdersByClient_ShouldReturnOrders() {
        String clientEmail = "client@test.com";
        List<Order> orders = List.of(new Order());

        when(orderRepository.findByClientEmail(clientEmail)).thenReturn(orders);

        List<OrderDTO> result = orderService.getOrdersByClient(clientEmail);

        assertEquals(1, result.size());
        verify(orderRepository).findByClientEmail(clientEmail);
    }
}