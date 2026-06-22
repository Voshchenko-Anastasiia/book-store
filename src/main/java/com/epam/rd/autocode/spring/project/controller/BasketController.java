package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BasketController {

    BookRepository bookRepository;
    OrderService orderService;

    public BasketController(BookRepository bookRepository, OrderService orderService) {
        this.bookRepository = bookRepository;
        this.orderService = orderService;
    }

    @GetMapping("/basket")
    public String showBasket(HttpSession session, Model model) {
        List<Long> basketIds = (List<Long>) session.getAttribute("BASKET");

        List<Book> items = new ArrayList<>();
        if (basketIds != null) {
            for (Long id : basketIds) {
                bookRepository.findById(id).ifPresent(items::add);
            }
        }

        model.addAttribute("basketItems", items);

        return "basket";
    }

    @PostMapping("/basket/add")
    public String addToBasket(@RequestParam Long bookId, HttpSession session) {
        List<Long> basket = (List<Long>) session.getAttribute("BASKET");
        if (basket == null) {
            basket = new ArrayList<>();
        }

        basket.add(bookId);

        session.setAttribute("BASKET", basket);

        System.out.println("DEBUG: Added book ID " + bookId + " to basket. Total items: " + basket.size());

        return "redirect:/books";
    }

    @PostMapping("/basket/remove/{id}")
    public String removeItem(@PathVariable Long id, HttpSession session) {
        List<Long> basket = (List<Long>) session.getAttribute("BASKET");
        if (basket != null) {
            basket.remove(id);
            session.setAttribute("BASKET", basket);
        }
        return "redirect:/basket";
    }

    @PostMapping("/basket/order")
    public String placeOrder(HttpSession session, Principal principal) {
        List<Long> basketIds = (List<Long>) session.getAttribute("BASKET");

        if (basketIds == null || basketIds.isEmpty()) {
            return "redirect:/basket?error=empty";
        }

        OrderDTO orderDto = new OrderDTO();

        List<BookItemDTO> items = basketIds.stream()
                .map(id -> {
                    BookItemDTO item = new BookItemDTO();
                    item.setBookId(id);
                    item.setQuantity(1);
                    return item;
                })
                .collect(Collectors.toList());

        orderDto.setBookItems(items);

        orderService.addOrder(orderDto, principal.getName());

        session.removeAttribute("BASKET");

        return "redirect:/my-orders";
    }
}
