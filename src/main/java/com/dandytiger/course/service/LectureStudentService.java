package com.dandytiger.course.service;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.domain.timetable.TimeTable;
import com.dandytiger.course.exception.DuplicateTimeCheck;
import com.dandytiger.course.exception.DuplicateTimeException;
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

import java.util.ArrayList;
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
    private final DuplicateTimeCheck duplicateTimeCheck;

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

        /**
         * 신청하려는 강의가 시간이 겹치는 확인하는 로직
         */
        List<List<TimeTable>> olds = new ArrayList<>();
        List<LectureStudent> registrationLectures = student.getRegistrationLectures();

        for (LectureStudent rl : registrationLectures) {
            Lecture l = rl.getLecture();
            log.info("rl : registrationLectures");
            log.info("lectureName = {} , 교수 = {}  ",lecture.getKorName(),lecture.getProfessorName());
            List<TimeTable> tt = l.getTimeTable();
            log.info("l.getTimeTable = {} ",l.getTimeTable().size());
            for (TimeTable t : tt) {
                log.info("t : tt");
                log.info("t = {} ",t.getCode());
                log.info("t = {} ",t.getLecture());

            }

            olds.add(tt);
        }


        if(!duplicateTimeCheck.is_available_registration(olds,lecture.getTimeTable())){
            log.info("duplicateTimeCheck = 겹친다.");
            throw new DuplicateTimeException("강의 시간 겹치는 신청");
        }
        log.info("duplicateTimeCheck = 안 겹친다.");

        //LectureStudent 생성
        LectureStudent lectureStudent = LectureStudent.createLectureStudent(student, lecture);

        if (lecture.getCapacity() >= lecture.getCurrentCount()) {
            student.addCurrentCredit(lecture.getCredit());

        }
        //저장
        lectureStudentRepository.save(lectureStudent);

        return lectureStudent.getId();
    }

    /**
     * 취소
     */
    @Transactional
    public void cancelApply(Long lectureStudentId) {
        LectureStudent lectureStudent = lectureStudentRepository.findOne(lectureStudentId);
        lectureStudent.cancel();
        lectureStudentRepository.reduceAllWaitLectureStudents(lectureStudent.getLecture().getId());
        //취소
        lectureStudentRepository.delete(lectureStudent);
    }


    /**
     * 아직 QueryDsl로 전부 검색하는거 해결 X.
     */
    public List<LectureStudent> findLectureStudents() {
        return lectureStudentRepository.findAll();
    }
}
