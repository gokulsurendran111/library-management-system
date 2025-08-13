package com.lms.borrow_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.borrow_service.events.BookBorrowedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BorrowEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public BorrowEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookBorrowedEvent(BookBorrowedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("book-borrowed-events", message);
            System.out.println("📤 Sent: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
