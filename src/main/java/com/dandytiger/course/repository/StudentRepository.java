package com.dandytiger.course.repository;

import com.dandytiger.course.domain.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Optional<Student> findByLoginId(String loginId);
}
