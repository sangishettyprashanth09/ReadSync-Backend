package com.readsync.backend.repository;

import java.util.Optional;
import com.readsync.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(Long id);
}
