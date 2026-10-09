package com.readsync.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String fileName;

    private String filePath;

    private Integer totalPages;

    private String uploadDate;

    private String coverImagePath;
}