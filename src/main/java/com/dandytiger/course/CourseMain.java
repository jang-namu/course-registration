package com.dandytiger.course;

import com.dandytiger.course.domain.lecture.GeneralElective;
import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.Major;
import com.dandytiger.course.repository.LectureRepository;
import com.dandytiger.course.service.LectureService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Transactional
public class CourseMain {

    private final LectureRepository lectureRepository;
    private final LectureService lectureService;

    @PostConstruct
    public void init() {
        Lecture lecture = new Major();
        lecture.setClassroom("SH501");

        lectureService.saveLecture(lecture);
    }

}
