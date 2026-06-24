import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.model.*;
import com.epam.rd.autocode.spring.project.model.enums.OrderStatus;
import com.epam.rd.autocode.spring.project.repo.*;
import com.epam.rd.autocode.spring.project.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private BookRepository bookRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private OrderServiceImpl orderService;

    private User testUser;
    private Order testOrder;
    private Book testBook;

    @BeforeEach
    void setUp() {
        testUser = new Client();
        testUser.setId(10L);
        testUser.setEmail("client@test.com");

        testBook = new Book();
        testBook.setId(1L);
        testBook.setName("Java Core");
        testBook.setPrice(BigDecimal.valueOf(20.00));

        testOrder = new Order();
        testOrder.setId(100L);
        testOrder.setUser(testUser);
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.setTotalPrice(BigDecimal.valueOf(40.00));

        OrderItem orderItem = new OrderItem();
        orderItem.setBook(testBook);
        orderItem.setQuantity(2);
        orderItem.setPrice(testBook.getPrice());
        orderItem.setOrder(testOrder);
        testOrder.setItems(Collections.singletonList(orderItem));
    }

    @Test
    void getOrdersByClient_WhenUserExists_ShouldReturnOrders() {
        when(userRepository.findByEmail("client@test.com")).thenReturn(Optional.of(testUser));
        when(orderRepository.findByUserId(10L)).thenReturn(Collections.singletonList(testOrder));

        List<OrderDTO> result = orderService.getOrdersByClient("client@test.com");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(OrderStatus.PENDING, result.get(0).getStatus());
    }

    @Test
    void getOrdersByClient_WhenUserDoesNotExist_ShouldThrowException() {
        when(userRepository.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getOrdersByClient("unknown@test.com"));
    }

    @Test
    void getOrdersByEmployee_WhenUserExists_ShouldReturnOrders() {
        when(userRepository.findByEmail("emp@test.com")).thenReturn(Optional.of(testUser));
        when(orderRepository.findByUserId(10L)).thenReturn(Collections.singletonList(testOrder));

        List<OrderDTO> result = orderService.getOrdersByEmployee("emp@test.com");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findAllByClient_ShouldFallbackToGetOrdersByClient() {
        when(userRepository.findByEmail("client@test.com")).thenReturn(Optional.of(testUser));
        when(orderRepository.findByUserId(10L)).thenReturn(Collections.singletonList(testOrder));

        List<OrderDTO> result = orderService.findAllByClient("client@test.com");

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void addOrder_WithValidData_ShouldConsolidateAndSave() {
        OrderDTO cartDto = new OrderDTO();
        List<BookItemDTO> items = new ArrayList<>();

        BookItemDTO item1 = new BookItemDTO();
        item1.setBookId(1L);
        item1.setQuantity(2);

        BookItemDTO item2 = new BookItemDTO();
        // Duplicate ID to test consolidation logic branch
        item2.setBookId(1L);
        item2.setQuantity(1);

        items.add(item1);
        items.add(item2);
        cartDto.setBookItems(items);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(userRepository.findByEmail("client@test.com")).thenReturn(Optional.of(testUser));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        OrderDTO result = orderService.addOrder(cartDto, "client@test.com");

        assertNotNull(result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void addOrder_WhenBookNotFound_ShouldThrowException() {
        OrderDTO cartDto = new OrderDTO();
        BookItemDTO item = new BookItemDTO();
        item.setBookId(99L);
        item.setQuantity(1);
        cartDto.setBookItems(Collections.singletonList(item));

        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.addOrder(cartDto, "client@test.com"));
    }

    @Test
    void getAllOrders_ShouldReturnList() {
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(testOrder));
        List<Order> result = orderService.getAllOrders();
        assertEquals(1, result.size());
    }

    @Test
    void updateStatus_WhenOrderExists_ShouldUpdateStatus() {
        when(orderRepository.findById(100L)).thenReturn(Optional.of(testOrder));

        orderService.updateStatus(100L, "DELIVERED");

        assertEquals(OrderStatus.DELIVERED, testOrder.getStatus());
        verify(orderRepository, times(1)).save(testOrder);
    }

    @Test
    void updateStatus_WhenOrderNotFound_ShouldThrowException() {
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> orderService.updateStatus(999L, "DELIVERED"));
    }
}