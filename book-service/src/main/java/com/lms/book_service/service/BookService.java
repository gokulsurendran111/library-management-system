package com.lms.book_service.service;

import com.lms.book_service.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO addBook(BookDTO book);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO updateBook(Long id, BookDTO book);
    void deleteBook(Long id);

    void updateBookAvailability(Long id, boolean available);
}
