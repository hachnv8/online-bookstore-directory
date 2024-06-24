package com.learnmicroservice.service;

import com.learnmicroservice.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(Long id);

    Book createBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    List<Book> searchBooksByName(String name);

    List<Book> getBooksByAuthor(Long authorId);

    List<Book> getBooksByCategory(Long categoryId);

    List<Book> getBestSellingBooks();

    List<Book> getLatestBooks();

    List<Book> getOnSaleBooks();
}
