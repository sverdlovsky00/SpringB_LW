package ru.arhipov.url_db_rest_max.service;

import ru.arhipov.url_db_rest_max.entity.User;
import ru.arhipov.url_db_rest_max.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        //Проверяем есть ли юзеры
        if (userRepository.count() == 0) {
            log.info("🔄 Initializing database with default users...");
            //Создаем админа
            User admin = new User("admin", passwordEncoder.encode("admin"));
            admin.addRole("ADMIN");
            admin.addRole("USER");
            userRepository.save(admin);
            //Создаем дефолт юзера
            User user = new User("user", passwordEncoder.encode("user"));
            user.addRole("USER");
            userRepository.save(user);
            //Создаем только для чтения :(
            User readonly = new User("reader", passwordEncoder.encode("reader"));
            readonly.addRole("READ_ONLY");
            userRepository.save(readonly);
            //Проверим что создалось
            List<User> users = userRepository.findAll();
            log.info("==========================================");
            log.info("BD INITIALIZATION");
            log.info("Created users: {}", users.size());
            for (User u : users) {
                log.info("👤 {} | Роли: {}", u.getUsername(), u.getRoles());}
            log.info("==========================================");}
        else {
            log.info("==========================================");
            log.info("BD ALLREADY INITIALIZATE");
            log.info("Current users: {}", userRepository.count());
            log.info("==========================================");}
    }
}