package com.dandytiger.course.domain.dto;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.lecturestudent.RegistrationStatus;
import com.dandytiger.course.domain.student.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class LectureStudentDto {
    private Long id;
    private String lectureType;
    private String code;
    private String korName;
    private int credit;
    private String professorName;
    private RegistrationStatus status;
    private Student student;
    private Lecture lecture;

    public LectureStudentDto(LectureStudent ls) {
        this.id = ls.getId();
        this.lectureType = ls.getLecture().getLectureType();
        this.code = ls.getLecture().getCode();
        this.korName = ls.getLecture().getKorName();
        this.credit = ls.getLecture().getCredit();
        this.professorName = ls.getLecture().getProfessorName();
        this.status = ls.getStatus();
        this.student = ls.getStudent();
        this.lecture = ls.getLecture();
    }
}
