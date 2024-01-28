package com.ronakcode.onlinelibrary.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ronakcode.onlinelibrary.constants.BookConstant;
import com.ronakcode.onlinelibrary.dto.MessageData;
import com.ronakcode.onlinelibrary.entities.Book;
import com.ronakcode.onlinelibrary.exceptions.BookNotFoundException;
import com.ronakcode.onlinelibrary.repositories.BookRepository;
import com.ronakcode.onlinelibrary.services.BookService;
import com.ronakcode.onlinelibrary.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.UriEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(String id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("Unable to find book with the given ISBN: " + id);
        }
    }

    @Override
    public Book searchBookByISBN(String isbn) throws BookNotFoundException{
        HttpClient httpClient = new HttpClient();
        String bookAPIURL = BookConstant.bookAPIURL;
        String response = httpClient.getData(bookAPIURL + isbn);
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (response, JsonElement.class);
        JsonObject jsonObject = element.getAsJsonObject();
        int resultData = jsonObject.get("totalItems").getAsInt();
        if (resultData >= 1) {
            Book newBook = new Book();
            newBook.setIsbn(isbn);
            newBook.setTitle(jsonObject.get("items").getAsJsonArray().get(0).getAsJsonObject().get("volumeInfo").getAsJsonObject().get("title").getAsString());
            newBook.setAuthors(jsonObject.get("items").getAsJsonArray().get(0).getAsJsonObject().get("volumeInfo").getAsJsonObject().get("authors").getAsJsonArray().get(0).getAsString());
//            JsonObject subTitle = (JsonObject) jsonObject.get("items").getAsJsonArray().get(0).getAsJsonObject().get("volumeInfo").getAsJsonObject().get("subtitle");
//            if (subTitle != null) {
//                newBook.setSubTitle(subTitle.getAsString());
//            }
            newBook.setDescription(jsonObject.get("items").getAsJsonArray().get(0).getAsJsonObject().get("volumeInfo").getAsJsonObject().get("description").getAsString());
            newBook.setImageLink(jsonObject.get("items").getAsJsonArray().get(0).getAsJsonObject().get("volumeInfo").getAsJsonObject().get("imageLinks").getAsJsonObject().get("thumbnail").getAsString());
            return newBook;
        } else {
            throw new BookNotFoundException("Unable to find book by isbn: " + isbn);
        }
    }

    @Override
    public MessageData addBook(Book book) {
        bookRepository.save(book);
        return new MessageData("Book saved in database");
    }

    @Override
    public MessageData deleteBook(String id) {
        bookRepository.deleteById(id);
        return new MessageData("Book deleted successfully!");
    }
}
