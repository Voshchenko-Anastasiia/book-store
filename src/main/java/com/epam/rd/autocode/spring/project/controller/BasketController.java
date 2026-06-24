package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.OrderService;
import jakarta.servlet.http.HttpSession;
// Added SLF4J imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BasketController {

    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

    private final BookRepository bookRepository;
    private final OrderService orderService;

    public BasketController(BookRepository bookRepository, OrderService orderService) {
        this.bookRepository = bookRepository;
        this.orderService = orderService;
    }

    public static class BasketViewItem {
        private final Book book;
        private final int quantity;

        public BasketViewItem(Book book, int quantity) {
            this.book = book;
            this.quantity = quantity;
        }
        public Book getBook() { return book; }
        public int getQuantity() { return quantity; }
    }

    @GetMapping("/basket")
    public String showBasket(HttpSession session, Model model) {
        OrderDTO basket = (OrderDTO) session.getAttribute("basket");
        List<BasketViewItem> itemsForView = new ArrayList<>();

        BigDecimal grandTotal = BigDecimal.ZERO;

        if (basket != null && basket.getBookItems() != null) {
            for (BookItemDTO item : basket.getBookItems()) {
                Optional<Book> bookOpt = bookRepository.findById(item.getBookId());

                if (bookOpt.isPresent()) {
                    Book book = bookOpt.get();
                    itemsForView.add(new BasketViewItem(book, item.getQuantity()));

                    BigDecimal itemSubtotal = book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    grandTotal = grandTotal.add(itemSubtotal);
                } else {
                    logger.warn("Book with ID {} was in the basket but could not be found in the database.", item.getBookId());
                }
            }
        }

        model.addAttribute("basketItems", itemsForView);
        model.addAttribute("grandTotal", grandTotal);

        return "basket";
    }

    @PostMapping("/basket/add")
    public String addToBasket(
            @RequestParam Long bookId,
            @RequestParam(name = "quantity", defaultValue = "1") Integer quantity,
            HttpSession session) {

        OrderDTO basket = (OrderDTO) session.getAttribute("basket");
        if (basket == null) {
            basket = new OrderDTO();
        }

        if (basket.getBookItems() == null) {
            basket.setBookItems(new ArrayList<>());
        }

        boolean bookAlreadyInBasket = false;
        for (BookItemDTO item : basket.getBookItems()) {
            if (item.getBookId().equals(bookId)) {
                item.setQuantity(item.getQuantity() + quantity);
                bookAlreadyInBasket = true;
                break;
            }
        }

        if (!bookAlreadyInBasket) {
            BookItemDTO newItem = new BookItemDTO();
            newItem.setBookId(bookId);
            newItem.setQuantity(quantity);
            basket.getBookItems().add(newItem);
        }

        session.setAttribute("basket", basket);
        logger.debug("Added book ID {} (quantity: {}) to basket.", bookId, quantity); // Optional debug log
        return "redirect:/books";
    }

    @PostMapping("/basket/remove/{id}")
    public String removeItem(@PathVariable Long id, HttpSession session) {
        OrderDTO basket = (OrderDTO) session.getAttribute("basket");

        if (basket != null && basket.getBookItems() != null) {
            basket.getBookItems().removeIf(item -> item.getBookId().equals(id));
            session.setAttribute("basket", basket);
            logger.debug("Removed book ID {} from basket.", id); // Optional debug log
        }
        return "redirect:/basket";
    }

    @PostMapping("/basket/order")
    @PreAuthorize("hasRole('CLIENT')")
    public String placeOrder(HttpSession session, Principal principal) {
        if (principal == null) {
            logger.warn("Unauthenticated user attempted to place an order.");
            return "redirect:/login";
        }

        OrderDTO basket = (OrderDTO) session.getAttribute("basket");

        if (basket == null || basket.getBookItems() == null || basket.getBookItems().isEmpty()) {
            logger.warn("User {} attempted to place an empty order.", principal.getName());
            return "redirect:/basket?error=empty";
        }

        try {
            logger.info("User {} is attempting to place an order with {} items.", principal.getName(), basket.getBookItems().size());
            orderService.addOrder(basket, principal.getName());
            session.removeAttribute("basket");
            logger.info("Order placed successfully for user {}.", principal.getName());
        } catch (Exception e) {
            logger.error("Failed to place order for user: {}. Error message: {}", principal.getName(), e.getMessage(), e);
            return "redirect:/basket?error=order_failed";
        }

        return "redirect:/cabinet/orders";
    }
}