package ru.arhipov.db.service;

import ru.arhipov.db.dto.UserDto;
import ru.arhipov.db.entity.User;
import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
