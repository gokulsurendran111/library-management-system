package com.lms.borrow_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowedEvent {
    private Long userId;
    private String userEmail;
    private Long bookId;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate dueDate;
}
