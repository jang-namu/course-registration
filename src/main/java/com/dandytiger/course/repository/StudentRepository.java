package com.dandytiger.course.repository;

import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.domain.timetable.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Optional<Student> findByLoginId(String loginId);

//    @Query("select t ")
//    public List<TimeTable> findTimeTableByStudentId()
}
