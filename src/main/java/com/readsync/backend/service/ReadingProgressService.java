package com.readsync.backend.service;

import com.readsync.backend.entity.ReadingProgress;
import com.readsync.backend.repository.ReadingProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class ReadingProgressService {

    private final ReadingProgressRepository repository;

    public ReadingProgressService(
            ReadingProgressRepository repository) {
        this.repository = repository;
    }

    public ReadingProgress saveProgress(
            Long bookId,
            Integer currentPage) {

        ReadingProgress progress = repository.findByBookId(bookId);

        if (progress == null) {
            progress = new ReadingProgress();
            progress.setBookId(bookId);
        }

        progress.setCurrentPage(currentPage);

        return repository.save(progress);
    }

    public ReadingProgress getProgress(Long bookId) {
        return repository.findByBookId(bookId);
    }
}