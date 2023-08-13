package com.dandytiger.course.service;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.exception.ExceedCreditException;
import com.dandytiger.course.repository.LectureRepository;
import com.dandytiger.course.repository.LectureStudentRepository;
import com.dandytiger.course.repository.LectureStudentSearch;
import com.dandytiger.course.repository.StudentRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LectureStudentService {

    private final LectureStudentRepository lectureStudentRepository;
    private final StudentRepository studentRepository;
    private final LectureRepository lectureRepository;

    /**
     * 신청
     */
    @Transactional
    public Long apply(Long studentId, Long lectureId) {

        //엔티티 조회
        Student student = (Student) studentRepository.findById(studentId)
                .orElseThrow(
                        NullPointerException::new
                );
        Lecture lecture = lectureRepository.findOne(lectureId);

        student.addCurrentCredit(lecture.getCredit());


        //LectureStudent 생성
        LectureStudent lectureStudent = LectureStudent.createLectureStudent(student, lecture);

        //저장
        lectureStudentRepository.save(lectureStudent);

        log.info("repository save after current credit = {}",student.getCurrentCredit());

        return lectureStudent.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelApply(Long lectureStudentId) {
        //엔티티 조회
        LectureStudent lectureStudent = lectureStudentRepository.findOne(lectureStudentId);
        //취소
        lectureStudent.cancel();
    }


    /**
     * 아직 QueryDsl로 전부 검색하는거 해결 X.
     */
    public List<LectureStudent> findLectureStudents() {
        return lectureStudentRepository.findAll();
    }
}
