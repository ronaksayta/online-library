package com.ronakcode.onlinelibrary.serviceImpl;

import com.ronakcode.onlinelibrary.config.JWTService;
import com.ronakcode.onlinelibrary.dto.OrderRequestDTO;
import com.ronakcode.onlinelibrary.entities.Book;
import com.ronakcode.onlinelibrary.entities.Order;
import com.ronakcode.onlinelibrary.entities.User;
import com.ronakcode.onlinelibrary.exceptions.UserNotFoundException;
import com.ronakcode.onlinelibrary.repositories.BookRepository;
import com.ronakcode.onlinelibrary.repositories.OrderRepository;
import com.ronakcode.onlinelibrary.services.AuthService;
import com.ronakcode.onlinelibrary.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthService authService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(String token) throws UserNotFoundException {
        String email = jwtService.extractEmail(token);
        User user = authService.findByEmail(email);
        return orderRepository.findByUser(user);
    }

    @Override
    public Order saveOrder(OrderRequestDTO order, String token) throws UserNotFoundException {
        String email = jwtService.extractEmail(token);
        User user = authService.findByEmail(email);
        Order saveOrder = new Order();
        saveOrder.setStartDate(order.getStartDate());
        saveOrder.setEndDate(order.getEndDate());
        List<Book> booksToSave = bookRepository.findAllById(order.getBookIds());
        saveOrder.setBooks(booksToSave);
        saveOrder.setUser(user);
        saveOrder.setNumberOfBooks(order.getBookIds().size());
        return orderRepository.save(saveOrder);
    }
}
