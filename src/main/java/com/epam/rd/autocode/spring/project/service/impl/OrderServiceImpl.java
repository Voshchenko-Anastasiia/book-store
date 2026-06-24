package com.epam.rd.autocode.spring.project.service.impl;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.model.*;
import com.epam.rd.autocode.spring.project.model.enums.OrderStatus;
import com.epam.rd.autocode.spring.project.repo.*;
import com.epam.rd.autocode.spring.project.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderDTO> getOrdersByClient(String clientEmail) {
        User user = userRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + clientEmail));

        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByEmployee(String employeeEmail) {
        User user = userRepository.findByEmail(employeeEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + employeeEmail));

        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> findAllByClient(String clientEmail) {
        return getOrdersByClient(clientEmail); // Reuse the existing method to avoid duplication
    }

    private OrderDTO convertToDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());

        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());

        if (order.getItems() != null) {
            List<BookItemDTO> itemDtos = order.getItems().stream()
                    .map(this::convertItemToDto)
                    .collect(Collectors.toList());
            dto.setBookItems(itemDtos); // Keeping the DTO field name the same so we don't break the frontend
        }
        return dto;
    }

    private BookItemDTO convertItemToDto(OrderItem item) {
        BookItemDTO dto = new BookItemDTO();
        dto.setBookId(item.getBook().getId());
        dto.setBookName(item.getBook().getName());
        dto.setQuantity(item.getQuantity());
        return dto;
    }

    @Override
    @Transactional
    public OrderDTO addOrder(OrderDTO orderDto, String email) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        Map<Long, Integer> consolidatedItems = new HashMap<>();
        for (BookItemDTO dto : orderDto.getBookItems()) {
            consolidatedItems.put(
                    dto.getBookId(),
                    consolidatedItems.getOrDefault(dto.getBookId(), 0) + dto.getQuantity()
            );
        }

        BigDecimal runningTotal = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : consolidatedItems.entrySet()) {
            Long bookId = entry.getKey();
            Integer totalQuantity = entry.getValue();

            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found: " + bookId));

            OrderItem item = new OrderItem();
            item.setBook(book);
            item.setOrder(order);

            item.setQuantity(totalQuantity);
            item.setPrice(book.getPrice());

            BigDecimal itemTotalPrice = book.getPrice().multiply(BigDecimal.valueOf(totalQuantity));
            runningTotal = runningTotal.add(itemTotalPrice);

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(runningTotal);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);

        return convertToDto(savedOrder);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void updateStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));

        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
    }
}