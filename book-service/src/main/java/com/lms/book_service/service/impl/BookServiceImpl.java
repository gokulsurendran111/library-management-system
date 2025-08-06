package com.lms.book_service.service.impl;

import com.lms.book_service.dto.BookDTO;
import com.lms.book_service.model.Book;
import com.lms.book_service.repository.BookRepository;
import com.lms.book_service.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    @Override
    public BookDTO addBook(BookDTO dto) {
        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .available(true)
                .build();
        Book saved = repository.save(book);
        return toDTO(saved);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
        return toDTO(book);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setAvailable(dto.isAvailable());

        return toDTO(repository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
        repository.delete(book);
    }

    @Override
    public void updateBookAvailability(Long id, boolean available) {
        BookDTO dto = getBookById(id);
        dto.setAvailable(available);
        updateBook(id, dto);
    }

    private BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .available(book.isAvailable())
                .build();
    }
}
