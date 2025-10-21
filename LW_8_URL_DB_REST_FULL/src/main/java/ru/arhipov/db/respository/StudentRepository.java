package ru.arhipov.db.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.arhipov.db.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{}
