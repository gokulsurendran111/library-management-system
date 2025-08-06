package com.lms.book_service.service.impl;

import com.lms.book_service.dto.BookRequestDTO;
import com.lms.book_service.dto.BookResponseDTO;
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
    public BookResponseDTO addBook(BookRequestDTO dto) {
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
    public List<BookResponseDTO> getAllBooks() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
        return toDTO(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());

        return toDTO(repository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
        repository.delete(book);
    }

    private BookResponseDTO toDTO(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .available(book.isAvailable())
                .build();
    }
}
