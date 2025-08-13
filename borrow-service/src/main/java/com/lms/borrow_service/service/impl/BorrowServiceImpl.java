package com.lms.borrow_service.service.impl;

import com.lms.borrow_service.dto.BorrowDTO;
import com.lms.borrow_service.events.BookBorrowedEvent;
import com.lms.borrow_service.kafka.BorrowEventProducer;
import com.lms.borrow_service.model.Borrow;
import com.lms.borrow_service.repository.BorrowRepository;
import com.lms.borrow_service.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private RestTemplate restTemplate;

    private BorrowEventProducer eventProducer;

    public BorrowServiceImpl(BorrowEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @Value("${book.service.url}")
    private String bookServiceUrl;

    @Override
    public Borrow createBorrow(BorrowDTO dto) {
        LocalDate currentDate = LocalDate.now();
        Borrow borrow = Borrow.builder()
                .userId(dto.getUserId())
                .bookId(dto.getBookId())
                .borrowDate(currentDate)
                .dueDate(currentDate.plusDays(30))
                .build();
        String url = bookServiceUrl + "/" + dto.getBookId() + "/availability?available=false";
        restTemplate.put(url, HttpEntity.EMPTY);
        return borrowRepository.save(borrow);
    }

    @Override
    public Borrow getBorrowById(Long id) {
        return borrowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
    }

    @Override
    public List<Borrow> getAllBorrows() {
        return new ArrayList<>(borrowRepository.findAll());
    }

    @Override
    public Borrow updateBorrow(Long id, BorrowDTO dto) {
        Borrow borrow = borrowRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));
        LocalDate currentDate = LocalDate.now();

        borrow.setUserId(dto.getUserId());
        borrow.setBookId(dto.getBookId());
        borrow.setBorrowDate(currentDate);
        borrow.setReturnDate(currentDate.plusDays(30));

        return borrowRepository.save(borrow);
    }

    @Override
    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }

    @Override
    public Borrow borrowBook(BorrowDTO dto) {
        Borrow borrow = createBorrow(dto);
        BookBorrowedEvent event = new BookBorrowedEvent(
                borrow.getUserId(),
                dto.getUserEmail(),
                borrow.getBookId(),
                dto.getBookTitle(),
                borrow.getBorrowDate(),
                borrow.getDueDate()
        );
        eventProducer.sendBookBorrowedEvent(event);
        return borrow;
    }

    @Override
    public List<Borrow> getBorrowsByUser(Long userId) {
        return getAllBorrows().stream().filter(b -> b.getUserId().equals(userId)).toList();
    }

    private BorrowDTO toDTO(Borrow borrow) {
        return BorrowDTO.builder()
                .userId(borrow.getUserId())
                .bookId(borrow.getBookId())
                .build();
    }
}
