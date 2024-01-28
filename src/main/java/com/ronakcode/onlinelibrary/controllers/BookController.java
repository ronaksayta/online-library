package com.ronakcode.onlinelibrary.controllers;

import com.ronakcode.onlinelibrary.dto.MessageData;
import com.ronakcode.onlinelibrary.entities.Book;
import com.ronakcode.onlinelibrary.exceptions.BookNotFoundException;
import com.ronakcode.onlinelibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book")
@CrossOrigin("*")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/all")
    ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Book> searchByISBN(@RequestParam String isbn) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.searchBookByISBN(isbn), HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable String bookId) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<MessageData> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.OK);
    }

   @DeleteMapping("/{bookId}")
   ResponseEntity<MessageData> deleteBook(@PathVariable String bookId) {
        return new ResponseEntity<>(bookService.deleteBook(bookId), HttpStatus.OK);
   }
}
