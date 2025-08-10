package com.lms.user_service.service.impl;

import com.lms.user_service.dto.UserDTO;
import com.lms.user_service.model.User;
import com.lms.user_service.repository.UserRepository;
import com.lms.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User register(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return repository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream().map(user -> new UserDTO(user.getId(), user.getUserName(), user.getEmail())).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = getUserEntityById(id); // reuse internal method

        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();
    }

    private User getUserEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }


    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserEntityById(id);
        existing.setUserName(user.getUserName());
        existing.setEmail(user.getEmail());
        return repository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
