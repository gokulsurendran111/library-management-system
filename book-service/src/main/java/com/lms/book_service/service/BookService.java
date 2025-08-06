package com.lms.book_service.service;

import com.lms.book_service.dto.BookRequestDTO;
import com.lms.book_service.dto.BookResponseDTO;

import java.util.List;

public interface BookService {
    BookResponseDTO addBook(BookRequestDTO book);
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO getBookById(Long id);
    BookResponseDTO updateBook(Long id, BookRequestDTO book);
    void deleteBook(Long id);
}
