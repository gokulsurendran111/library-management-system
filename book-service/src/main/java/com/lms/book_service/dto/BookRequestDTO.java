package com.lms.book_service.dto;

import lombok.Data;

@Data
public class BookRequestDTO {
    private String title;
    private String author;
    private String isbn;
}
