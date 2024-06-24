package com.learnmicroservice.repository;

import com.learnmicroservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitle(String title);
    List<Book> findBookByCategoryId(Long categoryId);
    List<Book> findBookByAuthorId(Long authorId);
    List<Book> findBookByTitle(String title);
}
