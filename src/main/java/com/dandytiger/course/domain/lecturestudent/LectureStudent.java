package com.dandytiger.course.domain.lecturestudent;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.student.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureStudent {

    @Id
    @GeneratedValue
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
    private registrationStatus status;

    // 시간
    private LocalDateTime registrationTime;

    //==생성 메서드==//
    public static LectureStudent createLectureStudent(Student student, Lecture lecture) {
        LectureStudent lectureStudent = new LectureStudent();
        lectureStudent.setStudent(student);
        lectureStudent.setLecture(lecture);
        lectureStudent.setRegistrationTime(LocalDateTime.now());

        if (lecture.getCapacity() - lecture.getCurrentCount() == 0) {
            lectureStudent.setStatus(registrationStatus.WAIT);
        } else {
            lectureStudent.setStatus(registrationStatus.COMPLETE);
            lecture.addCurrentCount();
        }

        return lectureStudent;
    }

    //==비즈니스 로직==//

    /**
     * 수강 취소
     */
    public void cancel() {
        this.setStatus(registrationStatus.CANCEL);
        getLecture().reduceCurrentCount();
    }


}