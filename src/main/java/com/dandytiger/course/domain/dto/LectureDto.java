package com.dandytiger.course.domain.dto;

import com.dandytiger.course.domain.lecture.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class LectureDto {

    private Long id;
    private String major;
    private String lectureType;
    private String lectureDivision;
    private String code;
    private String korName;
    private int credit;
    private String professorName;
    private int capacity;
    private int currentCount;

    public LectureDto(Lecture lecture) {
        this.id = lecture.getId();
        this.major = lecture.getMajor();
        this.lectureType = lecture.getLectureType();
        this.lectureDivision = lecture.getLectureDivision();
        this.code = lecture.getCode();
        this.korName = lecture.getKorName();
        this.credit = lecture.getCredit();
        this.professorName = lecture.getProfessorName();
        this.capacity = lecture.getCapacity();
        this.currentCount = lecture.getCurrentCount();
    }
}
