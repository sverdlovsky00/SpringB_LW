package ru.arhipov.url_db_rest_max.service;

import ru.arhipov.url_db_rest_max.entity.User;
import ru.arhipov.url_db_rest_max.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;}

    @Transactional
    public boolean registerUser(String username, String password) {
        log.info("Registering user: {}", username);
        if (userRepository.existsByUsername(username)) {
            log.warn("User already exists: {}", username);
            return false;}

        User user = new User(username, passwordEncoder.encode(password));
        //По умолчанию РИД ОНЛИ
        user.addRole("READ_ONLY");
        userRepository.save(user);
        log.info("User registered successfully: {}", username);
        return true;}

    public boolean validateUser(String username, String password) {
        log.info("Validating user: {}", username);
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            log.warn("User not found: {}", username);
            return false;}

        User user = userOpt.get();
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        log.info("🔧 Password validation result for {}: {}", username, matches);
        return matches;}

    public Optional<User> getCurrentUser(String username) {
        return userRepository.findByUsername(username);}
}