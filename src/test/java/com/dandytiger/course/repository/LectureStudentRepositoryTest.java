package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.Major;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.student.Student;
import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@Transactional
public class LectureStudentRepositoryTest {

    @Autowired
    LectureStudentRepository lectureStudentRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 강의신청() throws Exception {
        //given
        Student student = new Student(1L, 3, "김남기", "컴공", "201901542", "1234");

        Lecture lecture = new Major();
        lecture.setKorName("데이터사이언스");
        lecture.setClassroom("SH501");
        lecture.setCapacity(10);
        lecture.setCurrentCount(0);

        LectureStudent lectureStudent = LectureStudent.createLectureStudent(student, lecture);
        //when

        lectureStudentRepository.save(lectureStudent);
        Long savedId = lectureStudent.getId();


        //then
        em.flush();
        Assertions.assertEquals(lectureStudent, lectureStudentRepository.findOne(savedId));

    }
}