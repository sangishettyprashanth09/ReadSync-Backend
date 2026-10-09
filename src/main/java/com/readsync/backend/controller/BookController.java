package com.readsync.backend.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.web.multipart.MultipartFile;
import com.readsync.backend.entity.Book;
import com.readsync.backend.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/upload")
    public Book uploadBook(
            @RequestParam("file") MultipartFile file) throws IOException {

        return bookService.uploadBook(file);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<Resource> getPdfFile(
            @PathVariable Long id) throws MalformedURLException {

        Book book = bookService.getBookById(id);

        Path path = Paths.get(book.getFilePath());

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + book.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping("/cover/{id}")
    public ResponseEntity<Resource> getCover(
            @PathVariable Long id) throws IOException {

        Book book = bookService.getBookById(id);

        Path path = Paths.get(book.getCoverImagePath());

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
    @DeleteMapping("/{id}")
    public void deleteBook(
            @PathVariable Long id) {

        bookService.deleteBook(id);
    }
}