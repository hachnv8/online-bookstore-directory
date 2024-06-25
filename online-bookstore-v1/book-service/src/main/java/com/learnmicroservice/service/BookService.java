package com.learnmicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnmicroservice.entity.Book;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(Long id);

    Book createBook(Book book) throws ExecutionException, JsonProcessingException, InterruptedException, TimeoutException;

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    List<Book> searchBooksByName(String name);

    List<Book> getBooksByAuthor(Long authorId);

    List<Book> getBooksByCategory(Long categoryId);

    List<Book> getBestSellingBooks();

    List<Book> getLatestBooks();

    List<Book> getOnSaleBooks();
}
