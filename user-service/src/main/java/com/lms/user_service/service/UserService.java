package com.lms.user_service.service;

import com.lms.user_service.dto.UserDTO;
import com.lms.user_service.model.User;
import java.util.List;

public interface UserService {
    User register(User user);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);

    UserDTO findByUsername(String username);
}
