package com.lms.user_service.service.impl;

import com.lms.user_service.dto.UserDTO;
import com.lms.user_service.model.User;
import com.lms.user_service.repository.UserRepository;
import com.lms.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user -> new UserDTO(user.getId(), user.getUserName(), user.getEmail())).collect(Collectors.toList());
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

    public UserDTO findByUsername(String username) {
        return userRepository.findByUserName(username)
                .map(user -> new UserDTO(user.getId(), user.getUserName(), user.getEmail()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    private User getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }


    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserEntityById(id);
        existing.setUserName(user.getUserName());
        existing.setEmail(user.getEmail());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
