package com.ronakcode.onlinelibrary.repositories;

import com.ronakcode.onlinelibrary.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
