package com.lms.borrow_service.controller;

import com.lms.borrow_service.dto.BorrowDTO;
import com.lms.borrow_service.model.Borrow;
import com.lms.borrow_service.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping
    public ResponseEntity<Borrow> borrowBook(@RequestBody BorrowDTO dto) {
        return ResponseEntity.ok(borrowService.borrowBook(dto));
    }

    @GetMapping("/user/{userId}")
    public List<Borrow> getBorrowsByUser(@PathVariable Long userId) {
        return borrowService.getBorrowsByUser(userId);
    }
}

