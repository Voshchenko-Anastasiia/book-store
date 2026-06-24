package com.epam.rd.autocode.spring.project.service;

import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.model.Order;

import java.util.*;

public interface OrderService {
    List<OrderDTO> getOrdersByClient(String clientEmail);
    List<OrderDTO> getOrdersByEmployee(String employeeEmail);
    OrderDTO addOrder(OrderDTO orderDto, String clientEmail);
    List<OrderDTO> findAllByClient(String clientEmail);
    List<Order> getAllOrders();
    void updateStatus(Long id, String status);
}
