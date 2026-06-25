import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.model.Client;
import com.epam.rd.autocode.spring.project.model.Employee;
import com.epam.rd.autocode.spring.project.model.Order;
import com.epam.rd.autocode.spring.project.model.User;
import com.epam.rd.autocode.spring.project.repo.OrderRepository;
import com.epam.rd.autocode.spring.project.repo.UserRepository;
import com.epam.rd.autocode.spring.project.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private OrderRepository orderRepository;
    @Mock private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks private UserServiceImpl userService;

    private UserRegistrationDTO clientDto;
    private UserRegistrationDTO employeeDto;

    @BeforeEach
    void setUp() {
        clientDto = new UserRegistrationDTO();
        clientDto.setName("John Client");
        clientDto.setEmail("client@epam.com");
        clientDto.setPassword("pass123");
        clientDto.setRole("ROLE_CLIENT");

        employeeDto = new UserRegistrationDTO();
        employeeDto.setName("Alex Admin");
        employeeDto.setEmail("admin@epam.com");
        employeeDto.setPassword("secure987");
        employeeDto.setRole("ADMIN");
    }


    @Test
    void registerNewUser_WhenEmailExists_ShouldThrowException() {
        when(userRepository.findByEmail("client@epam.com")).thenReturn(Optional.of(new Client()));

        assertThrows(IllegalArgumentException.class, () -> userService.registerNewUser(clientDto));
    }

    @Test
    void registerNewUser_AsClient_ShouldSaveClientWithZeroBalance() {
        when(passwordEncoder.encode("pass123")).thenReturn("hashedPass");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registered = userService.registerNewUser(clientDto);

        assertTrue(registered instanceof Client);
        assertEquals(BigDecimal.ZERO, ((Client) registered).getBalance());
        assertEquals("CLIENT", registered.getRole());
    }

    @Test
    void registerNewUser_AsEmployeeOrAdmin_ShouldSaveEmployeeSubclass() {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registered = userService.registerNewUser(employeeDto);

        assertTrue(registered instanceof Employee);
        assertEquals("ADMIN", registered.getRole());
    }

    @Test
    void deleteUser_ShouldCascadeDeleteOrdersFirst() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findByUserId(1L)).thenReturn(orders);

        userService.deleteUser(1L);

        verify(orderRepository, times(1)).deleteAll(orders);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAllUsers_ShouldReturnMappedDtos() {
        User user1 = new Client(); user1.setRole("CLIENT");
        User user2 = new Employee(); user2.setRole("ADMIN");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserRegistrationDTO> dtos = userService.findAllUsers();

        assertEquals(2, dtos.size());
        assertEquals("********", dtos.get(0).getPassword());
    }

    @Test
    void findAllNonAdminUsers_ShouldFilterAdminsOut() {
        User user1 = new Client(); user1.setRole("CLIENT");
        User user2 = new Employee(); user2.setRole("ADMIN");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserRegistrationDTO> dtos = userService.findAllNonAdminUsers();

        assertEquals(1, dtos.size());
        assertEquals("CLIENT", dtos.get(0).getRole());
    }
}