package com.ronakcode.onlinelibrary.controller;

import com.google.gson.Gson;
import com.ronakcode.onlinelibrary.controllers.BookController;
import com.ronakcode.onlinelibrary.dto.MessageData;
import com.ronakcode.onlinelibrary.entities.Book;
import com.ronakcode.onlinelibrary.exceptions.BookNotFoundException;
import com.ronakcode.onlinelibrary.exceptions.ControllerAdvisor;
import com.ronakcode.onlinelibrary.services.BookService;
import lombok.SneakyThrows;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    BookService mockkBookService;

    @InjectMocks
    BookController mockBookController;

    @InjectMocks
    ControllerAdvisor controllerAdvisor;


    @SneakyThrows
    @Test
    void getBookByISBNSuccess() {
        Book expectedBook = new Book("123456789012", "abc", "abc subtitle", "abc book data", "abc author", "abc link");
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   when(mockkBookService.getBookById("123456789012")).thenReturn(expectedBook);

        String bookISBN = "123456789012";
        ResponseEntity<Book> response = mockBookController.getBookByISBN(bookISBN);
        Book actualBook = response.getBody();

        assertAll(
                () -> assertNotNull(actualBook),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(expectedBook, actualBook),
                () -> assertEquals(expectedBook.getIsbn(), actualBook.getIsbn())
        );
    }

    @SneakyThrows
    @Test
    void getBookByISBNBookNotFound() {

        MessageData messageData = new MessageData("Book not found");

        when(mockkBookService.getBookById("123456789012")).thenThrow(new BookNotFoundException("Book not found"));

        String bookISBN = "123456789012";
        ResponseEntity<MessageData> messageDataResponse;

            mockMvc = MockMvcBuilders.standaloneSetup(mockBookController).setControllerAdvice(controllerAdvisor).build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/book/" + bookISBN);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Gson gson = new Gson();
        MessageData actualMessageData = gson.fromJson(response.getContentAsString(), MessageData.class);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(404, response.getStatus()),
                () -> assertEquals(messageData.getMessage(), actualMessageData.getMessage())
        );
    }
}
