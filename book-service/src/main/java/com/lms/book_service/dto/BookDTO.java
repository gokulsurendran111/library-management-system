package com.lms.book_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private boolean available;
}

