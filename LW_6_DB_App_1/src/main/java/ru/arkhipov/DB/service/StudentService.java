package ru.arkhipov.DB.service;

import org.springframework.stereotype.Service;
import ru.arkhipov.DB.entity.Student;
import java.util.List;

@Service
public interface StudentService {

    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudent(int id);

    void deleteStudent(int id);

}
