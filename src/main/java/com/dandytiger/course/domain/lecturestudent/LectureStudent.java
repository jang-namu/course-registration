package com.dandytiger.course.domain.lecturestudent;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.Major;
import com.dandytiger.course.domain.student.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class LectureStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    // 대기 번호
    private int waitingNumber;

    // 상태
    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    // 시간
    private LocalDateTime registrationTime;

    //==생성 메서드==//
    public static LectureStudent createLectureStudent(Student student, Lecture lecture) {

        log.info("createLectureStudent start ");

        LectureStudent lectureStudent = new LectureStudent();

        lectureStudent.setStudent(student);
        lectureStudent.setLecture(lecture);
        lectureStudent.setRegistrationTime(LocalDateTime.now());

        if (lecture.getCapacity() - lecture.getCurrentCount() == 0) {
            log.info("createLectureStudent start after WAIT ");
            lectureStudent.setStatus(RegistrationStatus.WAIT);
        } else {
            log.info("createLectureStudent start after COMPLETE ");
            lectureStudent.setStatus(RegistrationStatus.COMPLETE);
            // lecture.addCurrentCount(); 말고 아래에 해야할듯?
        }

        lecture.addCurrentCount();

        log.info("createLectureStudent end ");

        return lectureStudent;
    }

    //==비즈니스 로직==//

    /**
     * 수강 취소
     */
    public void cancel() {
        this.setStatus(RegistrationStatus.CANCEL);
        getLecture().reduceCurrentCount();
    }


}