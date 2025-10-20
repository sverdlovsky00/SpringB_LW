package ru.arkhipov.DB.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.arkhipov.DB.dao.StudentDAO;
import ru.arkhipov.DB.entity.Student;

import java.util.List;
@Service

public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;

    @Override
    @Transactional
    public List<Student> getAllStudents(){return studentDAO.getAllStudents();}

    @Override
    @Transactional
    public Student saveStudent(Student student){
        return studentDAO.saveStudent(student);}

    @Override
    @Transactional
    public Student getStudent(int id){return  studentDAO.getStudent(id);}

    @Override
    @Transactional
    public void deleteStudent(int id){studentDAO.deleteStudent(id);}
}
