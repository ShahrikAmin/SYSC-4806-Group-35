package com.group35.project.User;

import org.springframework.stereotype.Service;

@Service

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
    public boolean usernameExists(String s) {
        return false;
    }

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.userName());
        user.setPassword(userDto.password());
        user.setRole(userDto.role());
        repo.save(user);
    }
}
