package com.learnmicroservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnmicroservice.domain.BookEvent;
import com.learnmicroservice.domain.BookEventType;
import com.learnmicroservice.entity.Book;
import com.learnmicroservice.exception.ResourceNotFoundException;
import com.learnmicroservice.kafka.BookEventProducer;
import com.learnmicroservice.repository.BookRepository;
import com.learnmicroservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String BOOK_CACHE = "BOOK";
    private final BookEventProducer bookEventProducer;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        // call redis to get data
        Book book = (Book) redisTemplate.opsForHash().get(BOOK_CACHE, id.toString());
        if (book == null) {
            book = bookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Book not found with id :" + id)
            );
            if (book != null) {
                redisTemplate.opsForHash().put(BOOK_CACHE, id.toString(), book.toString());
            }
        }

        return book;
    }

    public Book createBook(Book book) throws ExecutionException, JsonProcessingException, InterruptedException, TimeoutException {
        Book savedBook = bookRepository.save(book);
        // invoke the kafka producer
        BookEventType bookEventType = BookEventType.NEW;
        BookEvent bookEvent = new BookEvent(savedBook.getBookId(), bookEventType, savedBook);
        SendResult<Integer, String> result = bookEventProducer.sendBookEvent(bookEvent);
        redisTemplate.opsForHash().put(BOOK_CACHE, String.valueOf(savedBook.getBookId()), savedBook.toString());
        return savedBook;
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id :" + id));
        book.setTitle(bookDetails.getTitle());
        book.setDescription(bookDetails.getDescription());
        book.setImage(bookDetails.getImage());
        book.setPrice(bookDetails.getPrice());
        book.setCategoryId(bookDetails.getCategoryId());
        book.setAuthorId(bookDetails.getAuthorId());
        Book updatedBook =  bookRepository.save(book);
        redisTemplate.opsForHash().put(BOOK_CACHE, updatedBook.getBookId(), updatedBook.toString());
        return updatedBook;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooksByName(String name) {
        return bookRepository.findBookByTitle(name);
    }

    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findBookByAuthorId(authorId);
    }

    public List<Book> getBooksByCategory(Long categoryId) {
        return bookRepository.findBookByCategoryId(categoryId);
    }

    public List<Book> getBestSellingBooks() {
        return new ArrayList<>();
    }

    public List<Book> getLatestBooks() {
        return new ArrayList<>();
    }

    public List<Book> getOnSaleBooks() {
        return new ArrayList<>();
    }
}
