package com.lms.borrow_service.service.impl;

import com.lms.borrow_service.dto.BorrowDTO;
import com.lms.borrow_service.model.Borrow;
import com.lms.borrow_service.repository.BorrowRepository;
import com.lms.borrow_service.service.BorrowService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Override
    public Borrow createBorrow(BorrowDTO dto) {
        Borrow borrow = Borrow.builder()
                .userId(dto.getUserId())
                .bookId(dto.getBookId())
                .borrowDate(dto.getBorrowDate())
                .returnDate(dto.getReturnDate())
                .build();

        Borrow saved = borrowRepository.save(borrow);
        return saved;
    }

    @Override
    public Borrow getBorrowById(Long id) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
        return borrow;
    }

    @Override
    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Borrow updateBorrow(Long id, BorrowDTO dto) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        borrow.setUserId(dto.getUserId());
        borrow.setBookId(dto.getBookId());
        borrow.setBorrowDate(dto.getBorrowDate());
        borrow.setReturnDate(dto.getReturnDate());

        Borrow updated = borrowRepository.save(borrow);
        return updated;
    }

    @Override
    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }

    @Override
    public Borrow borrowBook(BorrowDTO dto) {
        Borrow borrow = createBorrow(dto);
        return borrow;
    }

    @Override
    public List<Borrow> getBorrowsByUser(Long userId) {
        return getAllBorrows().stream().filter(b -> b.getUserId().equals(userId)).toList();
    }

    private BorrowDTO toDTO(Borrow borrow) {
        return BorrowDTO.builder()
                .id(borrow.getId())
                .userId(borrow.getUserId())
                .bookId(borrow.getBookId())
                .borrowDate(borrow.getBorrowDate())
                .returnDate(borrow.getReturnDate())
                .build();
    }
}
