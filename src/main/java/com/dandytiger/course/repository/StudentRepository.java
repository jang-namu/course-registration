package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.domain.timetable.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Optional<Student> findByLoginId(String loginId);


    // fetch join 하는 쿼리가 잘 못 된 거 같다. 제대로 쿼리 짜면 될듯 여기든 다른 곳이든

    // registrationLecture -> lecture -> timeTable
//    @Query("select s.registrationLectures from Student s join fetch s.registrationLectures where s.id=:id")
//    public List<LectureStudent> findFetchJoinStudent(@Param("id")Long id);


//    @Query("select s.registrationLectures from Student s join LectureStudent ls on s.id=ls.student.id join fetch ls.lecture.timeTable")
//    public List<LectureStudent> findFetchJoinStudent(@Param("id")Long id);
}
