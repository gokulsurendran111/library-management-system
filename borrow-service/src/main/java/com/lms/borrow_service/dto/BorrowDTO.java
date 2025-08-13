package com.lms.borrow_service.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowDTO {
    private Long userId;
    private String userEmail;
    private Long bookId;
    private String bookTitle;
}
