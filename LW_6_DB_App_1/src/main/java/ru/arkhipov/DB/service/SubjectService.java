package ru.arkhipov.DB.service;
import ru.arkhipov.DB.entity.Subject;
import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjects();
    Subject saveSubject(Subject subject);
    Subject getSubject(int id);
    void deleteSubject(int id);
}
