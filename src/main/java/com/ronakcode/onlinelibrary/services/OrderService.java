package com.ronakcode.onlinelibrary.services;

import com.ronakcode.onlinelibrary.dto.OrderRequestDTO;
import com.ronakcode.onlinelibrary.entities.Order;
import com.ronakcode.onlinelibrary.exceptions.UserNotFoundException;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    List<Order> getOrdersByUser(String token) throws UserNotFoundException;
    Order saveOrder(OrderRequestDTO order, String token) throws UserNotFoundException;
}
