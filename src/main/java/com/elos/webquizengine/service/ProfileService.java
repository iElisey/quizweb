package com.elos.webquizengine.service;

import com.elos.webquizengine.model.User;
import com.elos.webquizengine.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    @Autowired
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getProfile(User user) {
        return userRepository.findById(user.getId());
    }
}
