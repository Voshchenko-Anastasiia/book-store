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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    public OrderServiceImpl(OrderRepository orderRepository, BookRepository bookRepository, EmployeeRepository employeeRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<OrderDTO> getOrdersByClient(String clientEmail) {
        return orderRepository.findByClientEmail(clientEmail).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByEmployee(String employeeEmail) {

        //bridge
        Employee employee = employeeRepository.findByEmail(employeeEmail)
                .orElseThrow(() -> new RuntimeException("Employee not found with email: " + employeeEmail));

        List<Order> orders = orderRepository.findByEmployeeId(employee.getId());

        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalPrice(order.getPrice());
        dto.setStatus(order.getStatus());
        
        if (order.getBookItems() != null) {
            List<BookItemDTO> itemDtos = order.getBookItems().stream()
                    .map(this::convertItemToDto)
                    .collect(Collectors.toList());
            dto.setBookItems(itemDtos);
        }
        return dto;
    }

    private BookItemDTO convertItemToDto(BookItem item) {
        BookItemDTO dto = new BookItemDTO();
        dto.setBookId(item.getBook().getId());
        dto.setQuantity(item.getQuantity());
        return dto;
    }

    public List<OrderDTO> findAllByClient(String clientEmail) {
        return orderRepository.findByClientEmailCustom(clientEmail)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO addOrder(OrderDTO orderDto, String clientEmail) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        BigDecimal runningTotal = BigDecimal.ZERO;
        List<BookItem> bookItems = new ArrayList<>();

        for (BookItemDTO dto : orderDto.getBookItems()) {
            Book book = bookRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found: " + dto.getBookId()));

            BookItem item = new BookItem();
            item.setBook(book);
            item.setOrder(order);
            item.setQuantity(dto.getQuantity());

            BigDecimal itemPrice = book.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
            item.setPriceAtPurchase(book.getPrice());

            bookItems.add(item);

            runningTotal = runningTotal.add(itemPrice);
        }

        order.setBookItems(bookItems);
        order.setPrice(runningTotal);

        Client client = clientRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        order.setClient(client);

        Order savedOrder = orderRepository.save(order);

        return convertToDto(savedOrder);
    }
}