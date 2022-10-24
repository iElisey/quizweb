package com.elos.webquizengine.service;

import com.elos.webquizengine.dto.SignupDto;
import com.elos.webquizengine.model.Role;
import com.elos.webquizengine.model.User;
import com.elos.webquizengine.repo.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  implements UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User add(SignupDto signupDto) {
        if (userRepository.existsByUsername(signupDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(signupDto.getEmail());
        user.setRoles(List.of(Role.USER));
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(s);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", user.get().getUsername()));
        }
    }
}
