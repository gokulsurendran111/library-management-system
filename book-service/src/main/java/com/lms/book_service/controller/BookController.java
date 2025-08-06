package com.lms.book_service.controller;

import com.lms.book_service.dto.BookDTO;
import com.lms.book_service.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO dto) {
        return ResponseEntity.ok(service.addBook(dto));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(service.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO dto) {
        return ResponseEntity.ok(service.updateBook(id, dto));
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<Void> updateBookAvailability(
            @PathVariable Long id,
            @RequestParam boolean available) {
        service.updateBookAvailability(id, available);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
