package ru.arhipov.db.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arhipov.db.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
}
