package ru.arkhipov.DB.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.arkhipov.DB.entity.Response;
import ru.arkhipov.DB.entity.Subject;
import ru.arkhipov.DB.service.SubjectService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyControllerSubject {

    @Autowired
    private SubjectService subjectService;

    // получение всех дисциплин
    @GetMapping("/subjects")
    public Response<List<Subject>> allSubjects() {
        try {
            List<Subject> subjects = subjectService.getAllSubjects();
            return Response.success(subjects);
        } catch (Exception e) {
            return Response.error("Failed to fetch subjects: " + e.getMessage());
        }
    }

    // получение дисциплины по ID
    @GetMapping("/subjects/{id}")
    public Response<Subject> getSubject(@PathVariable("id") int id) {
        try {
            Subject subject = subjectService.getSubject(id);
            if (subject != null) {
                return Response.success(subject);
            } else {
                return Response.error("Subject with id " + id + " not found");
            }
        } catch (Exception e) {
            return Response.error("Error retrieving subject: " + e.getMessage());
        }
    }

    // создание новой дисциплины
    @PostMapping("/subjects")
    public Response<Subject> saveSubject(@RequestBody Subject subject) {
        try {
            Subject savedSubject = subjectService.saveSubject(subject);
            return Response.success(savedSubject);
        } catch (Exception e) {
            return Response.error("Failed to save subject: " + e.getMessage());
        }
    }

    // обновление существующей дисциплины
    @PutMapping("/subjects")
    public Response<Subject> updateSubject(@RequestBody Subject subject) {
        try {
            Subject updatedSubject = subjectService.saveSubject(subject);
            return Response.success(updatedSubject);
        } catch (Exception e) {
            return Response.error("Failed to update subject: " + e.getMessage());
        }
    }

    // удаление дисциплины по ID
    @DeleteMapping("/subjects/{id}")
    public Response<Void> deleteSubject(@PathVariable("id") int id) {
        try {
            subjectService.deleteSubject(id);
            return Response.success(null);
        } catch (Exception e) {
            return Response.error("Failed to delete subject: " + e.getMessage());
        }
    }
}