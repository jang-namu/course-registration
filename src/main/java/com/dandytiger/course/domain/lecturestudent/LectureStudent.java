package com.dandytiger.course.domain.lecturestudent;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.exception.ApplySameLectureException;
import com.dandytiger.course.exception.ExceedCreditException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Entity
@Getter
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

    public LectureStudent(Lecture lecture, Student student, RegistrationStatus status, LocalDateTime registrationTime) {
        this.lecture = lecture;
        this.student = student;
        this.status = status;
        this.registrationTime = registrationTime;
    }

    //==생성 메서드==//
    public static LectureStudent createLectureStudent(Student student, Lecture lecture) {


        // 이미 신청한 과목시 예외 발생
        List<LectureStudent> registrationLectures = student.getRegistrationLectures();
        for (LectureStudent rl : registrationLectures) {
            String lectureName = rl.getLecture().getKorName();
            if (lecture.getKorName()==lectureName){
                throw new ApplySameLectureException("같은 과목 신청");
            }
        }

        LectureStudent lectureStudent = new LectureStudent();

        lecture.addCurrentCount();

        if (lecture.getCapacity() - lecture.getCurrentCount() < 0) {
            lectureStudent.updateLectureStudent(lecture, student, RegistrationStatus.WAIT, LocalDateTime.now());
            lectureStudent.addWaitingNumber(lecture);
        } else {
            lectureStudent.updateLectureStudent(lecture, student, RegistrationStatus.COMPLETE, LocalDateTime.now());
        }



        return lectureStudent;
    }

    public LectureStudent updateLectureStudent(Lecture lecture, Student student, RegistrationStatus registrationStatus, LocalDateTime now) {
        this.lecture = lecture;
        this.student = student;
        this.status = registrationStatus;
        this.registrationTime = now;

        return this;
    }

    //==비즈니스 로직==//
    public void addWaitingNumber(Lecture lecture) {
        int myNumber = lecture.getCurrentCount() - lecture.getCapacity();
        this.waitingNumber = myNumber + 1;
    }

    /**
     * 수강 취소
     */

    public void cancel() {
        getLecture().reduceCurrentCount();
        getStudent().reduceCurrentCredit(getLecture().getCredit());
    }


}