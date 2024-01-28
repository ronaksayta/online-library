package com.ronakcode.onlinelibrary.repositories;

import com.ronakcode.onlinelibrary.entities.Order;
import com.ronakcode.onlinelibrary.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUser(User user);
}
