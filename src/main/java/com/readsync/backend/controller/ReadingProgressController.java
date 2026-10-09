package com.readsync.backend.controller;

import com.readsync.backend.entity.ReadingProgress;
import com.readsync.backend.repository.ReadingProgressRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "http://localhost:5173")
public class ReadingProgressController {

    private final ReadingProgressRepository repository;

    public ReadingProgressController(
            ReadingProgressRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{bookId}")
    public ReadingProgress getProgress(
            @PathVariable Long bookId) {
        return repository.findByBookId(bookId);
    }
    @PostMapping("/{bookId}")
    public ReadingProgress saveProgress(
        @PathVariable Long bookId,
        @RequestBody ReadingProgress progressRequest
    ) {

    ReadingProgress progress =
            repository.findByBookId(bookId);

    if (progress == null) {
        progress = new ReadingProgress();
        progress.setBookId(bookId);
    }

    progress.setCurrentPage(
            progressRequest.getCurrentPage()
    );

    return repository.save(progress);
}
}