package ru.arhipov.url_db_rest_max.service;
import ru.arhipov.url_db_rest_max.entity.User;
import ru.arhipov.url_db_rest_max.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;}

    public List<User> findAll() {
        return userRepository.findAll();}

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);}

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);}

    public User save(User user) {
        return userRepository.save(user);}

    public void deleteById(Long id) {
        userRepository.deleteById(id);}

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);}

    public User createUser(String username, String password) {
        User user = new User(username, password);
        return userRepository.save(user);}

    @Transactional
    public void addRoleToUser(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User не найден!"));
        user.addRole(role);
        userRepository.save(user);}

    @Transactional
    public void removeRoleFromUser(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User не найден!"));
        user.getRoles().remove(role);
        userRepository.save(user);}

    public boolean hasRole(Long userId, String role) {
        return userRepository.findById(userId)
                .map(user -> user.hasRole(role))
                .orElse(false);}

    public List<User> findUsersWithoutRole(String role) {
        return userRepository.findAll().stream()
                .filter(user -> !user.hasRole(role))
                .collect(Collectors.toList());}
}