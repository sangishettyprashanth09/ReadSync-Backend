package com.readsync.backend.service;

import com.readsync.backend.entity.Book;
import com.readsync.backend.repository.BookRepository;
import com.readsync.backend.repository.ReadingProgressRepository;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ReadingProgressRepository progressRepository;

    public BookService(
            BookRepository bookRepository,
            ReadingProgressRepository progressRepository) {

        this.bookRepository = bookRepository;
        this.progressRepository = progressRepository;
    }

    public Book uploadBook(MultipartFile file) throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        String coverDir = System.getProperty("user.dir") + "/covers/";

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File coverDirectory = new File(coverDir);
        if (!coverDirectory.exists()) {
            coverDirectory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + ".pdf";
        String filePath = uploadDir + fileName;

        File destination = new File(filePath);
        file.transferTo(destination);

        Book book = new Book();

        book.setTitle(
                file.getOriginalFilename()
                        .replace(".pdf", ""));

        book.setFileName(fileName);
        book.setFilePath(filePath);

        PDDocument document = Loader.loadPDF(destination);

        int totalPages = document.getNumberOfPages();

        PDFRenderer renderer = new PDFRenderer(document);

        BufferedImage image = renderer.renderImageWithDPI(0, 150);

        String coverFileName = System.currentTimeMillis() + ".jpg";

        String coverPath = coverDir + coverFileName;

        ImageIO.write(
                image,
                "jpg",
                new File(coverPath));

        document.close();

        book.setTotalPages(totalPages);
        book.setUploadDate(LocalDate.now().toString());
        book.setCoverImagePath(coverPath);

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElse(null);

        if (book == null) {
            return;
        }

        try {

            File pdfFile = new File(book.getFilePath());

            if (pdfFile.exists()) {
                pdfFile.delete();
            }

            if (book.getCoverImagePath() != null) {

                File coverFile = new File(book.getCoverImagePath());

                if (coverFile.exists()) {
                    coverFile.delete();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        progressRepository.deleteByBookId(id);

        bookRepository.deleteById(id);
    }
}