package com.readsync.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reading_progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = "bookId")
})
public class ReadingProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long bookId;

    private Integer currentPage;

    public ReadingProgress() {
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}