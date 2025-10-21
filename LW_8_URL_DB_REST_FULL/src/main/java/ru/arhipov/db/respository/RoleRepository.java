package ru.arhipov.db.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.arhipov.db.entity.Role;
public interface RoleRepository extends JpaRepository<Role,Long>{
    Role findByName(String name);}
