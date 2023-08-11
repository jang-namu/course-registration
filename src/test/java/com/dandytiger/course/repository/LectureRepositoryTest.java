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

import java.util.List;

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

        //when
        List<Lecture> lectureList = lectureRepository.findAll();
        //then
        for (Lecture lecture : lectureList) {
//            System.out.println("lecture = " + lecture.getMajor());
        }
    }
}
