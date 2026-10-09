package com.readsync.backend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import com.readsync.backend.entity.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingProgressRepository
        extends JpaRepository<ReadingProgress, Long> {

    ReadingProgress findByBookId(Long bookId);

    @Modifying
    @Transactional
    void deleteByBookId(Long bookId);
}