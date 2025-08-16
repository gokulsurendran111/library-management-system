package com.lms.book_service.service;

import com.lms.book_service.dto.BookDTO;
import com.lms.book_service.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookDTO addBook(BookDTO book);
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO updateBook(Long id, BookDTO book);
    void deleteBook(Long id);

    void updateBookAvailability(Long id, boolean available);
    Page<Book> getAllBooks(PageRequest request);

    Page<Book> searchBooks(String pattern, Pageable pageable);
}
