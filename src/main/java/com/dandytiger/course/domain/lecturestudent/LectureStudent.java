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


        // 이미 신청한 과목시 예외 발생
        List<LectureStudent> registrationLectures = student.getRegistrationLectures();
        for (LectureStudent rl : registrationLectures) {
            String lectureName = rl.getLecture().getKorName();
            if (lecture.getKorName()==lectureName){
                throw new ApplySameLectureException("같은 과목 신청");
            }
        }


        LectureStudent lectureStudent = new LectureStudent();

        lectureStudent.setStudent(student);
        lectureStudent.setLecture(lecture);
        lectureStudent.setRegistrationTime(LocalDateTime.now());

        if (lecture.getCapacity() - lecture.getCurrentCount() <= 0) {
            lectureStudent.setStatus(RegistrationStatus.WAIT);
        } else {
            lectureStudent.setStatus(RegistrationStatus.COMPLETE);
            // lecture.addCurrentCount(); 말고 아래에 해야할듯?
        }

        lecture.addCurrentCount();

        return lectureStudent;
    }

    //==비즈니스 로직==//

    /**
     * 수강 취소
     */

    public void cancel() {
        getLecture().reduceCurrentCount();
        getStudent().reduceCurrentCredit(getLecture().getCredit());
    }


}