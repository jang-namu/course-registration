package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.Major;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class LectureRepositoryTest {

    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 강의추가() throws Exception {
        //given
        Lecture lecture = new Major();
        lecture.setKorName("데이터사이언스");
        lecture.setClassroom("SH501");
        //when
        lectureRepository.save(lecture);
        Long savedId = lecture.getId();
        Lecture findLecture = lectureRepository.findOne(savedId);
        System.out.println("findLecture.getClassroom() = " + findLecture.getClassroom());
        //then
        Assertions.assertEquals(lecture, lectureRepository.findOne(savedId));

    }
}
