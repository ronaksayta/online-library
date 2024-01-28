package com.ronakcode.onlinelibrary.services;

import com.ronakcode.onlinelibrary.dto.MessageData;
import com.ronakcode.onlinelibrary.entities.Book;
import com.ronakcode.onlinelibrary.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(String id) throws BookNotFoundException;
    Book searchBookByISBN(String isbn) throws BookNotFoundException;
    MessageData addBook(Book book);
    MessageData deleteBook(String id);
}
