package com.arkhipov.URL_DB_REST.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arkhipov.URL_DB_REST.entity.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{}
