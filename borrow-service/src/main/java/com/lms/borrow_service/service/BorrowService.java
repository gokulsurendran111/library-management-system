package com.lms.borrow_service.service;

import com.lms.borrow_service.dto.BorrowDTO;
import com.lms.borrow_service.model.Borrow;

import java.util.List;

public interface BorrowService {
    Borrow createBorrow(BorrowDTO borrowDTO);
    Borrow getBorrowById(Long id);
    List<Borrow> getAllBorrows();
    Borrow updateBorrow(Long id, BorrowDTO borrowDTO);
    void deleteBorrow(Long id);

    Borrow borrowBook(BorrowDTO dto);
    List<Borrow> getBorrowsByUser(Long userId);
}
