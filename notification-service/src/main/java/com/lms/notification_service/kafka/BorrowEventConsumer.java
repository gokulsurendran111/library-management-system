package com.lms.notification_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.notification_service.events.BookBorrowedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BorrowEventConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "book-borrowed-events", groupId = "notification-service")
    public void consume(String message) {
        try {
            BookBorrowedEvent event = objectMapper.readValue(message, BookBorrowedEvent.class);
            System.out.println("📧 Email -> " + event.getUserEmail()
                    + " : You borrowed '" + event.getBookTitle()
                    + "', due on " + event.getDueDate());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
