package ru.arkhipov.DB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arkhipov.DB.entity.Response;
import ru.arkhipov.DB.entity.Student;
import ru.arkhipov.DB.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public Response<List<Student>> allStudents() {
        try {
            return Response.success(studentService.getAllStudents());
        } catch (Exception e) {
            return Response.error("Failed to fetch students: " + e.getMessage());
        }
    }

    @GetMapping("/students/{id}")
    public Response<Student> getStudent(@PathVariable("id") int id) {
        try {
            Student student = studentService.getStudent(id);
            return student != null ?
                    Response.success(student) :
                    Response.error("Student not found");
        } catch (Exception e) {
            return Response.error("Error retrieving student: " + e.getMessage());
        }
    }

    @PostMapping("/students") public Response<Student> saveStudent(@RequestBody Student student) {
        try {
            return Response.success(studentService.saveStudent(student));
        } catch (Exception e) {
            return Response.error("Failed to save student: " + e.getMessage());
        }
    }

    @PutMapping("/students")
    public Response<Student> updateStudent(@RequestBody Student student) {
        try {
            return Response.success(studentService.saveStudent(student));
        } catch (Exception e) {
            return Response.error("Failed to update student: " + e.getMessage());
        }
    }

    @DeleteMapping("/students/{id}")
    public Response<Void> deleteStudent(@PathVariable("id") int id) {
        try {
            studentService.deleteStudent(id);
            return Response.success(null);
        } catch (Exception e) {
            return Response.error("Failed to delete student: " + e.getMessage());
        }
    }
}