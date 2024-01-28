package com.ronakcode.onlinelibrary.controllers;

import com.ronakcode.onlinelibrary.dto.OrderRequestDTO;
import com.ronakcode.onlinelibrary.entities.Order;
import com.ronakcode.onlinelibrary.exceptions.UserNotFoundException;
import com.ronakcode.onlinelibrary.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("api/order")
@RestController
@CrossOrigin("*")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/all")
    ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<List<Order>>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<Order>> getOrderByUser(@RequestHeader Map<String, String> headerData) throws UserNotFoundException {
        return new ResponseEntity<List<Order>>(orderService.getOrdersByUser(headerData.get("authorization").substring(7)), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Order> addOrder(@RequestHeader Map<String, String> headerData, @RequestBody OrderRequestDTO orderRequestDTO) throws UserNotFoundException {
        return new ResponseEntity<Order>(orderService.saveOrder(orderRequestDTO, headerData.get("authorization").substring(7)), HttpStatus.CREATED);
    }

}
