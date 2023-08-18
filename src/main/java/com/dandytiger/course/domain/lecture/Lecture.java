package com.dandytiger.course.domain.lecture;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String code; //과목코드

    private String korName; //과목명(한)

//    private String type; //이수구분 값 타입으로 일단 수정

    private String professorName; //교수명
    private String grade; //학년
    private int credit; //학점
    private int capacity; //수강정원
    private int currentCount; //현재인원

    private String lectureType; // 전공인가 ? 교양인가 ?
    private String lectureDivision; // 이수 구분 (기초교양 , 전공기초 , 핵심교양 ...)
    private String major;

//    @OneToOne
//    private Schedule schedule;

    public Lecture(String code, String korName, String professorName, String grade, int credit, int capacity, int currentCount, String lectureType, String lectureDivision, String major) {
        this.code = code;
        this.korName = korName;
        this.professorName = professorName;
        this.grade = grade;
        this.credit = credit;
        this.capacity = capacity;
        this.currentCount = currentCount;
        this.lectureType = lectureType;
        this.lectureDivision = lectureDivision;
        this.major = major;
    }

    public Lecture() {
    }

    //==비즈니스 로직==/

    /**
     * 현재인원 증가
     */
    public void addCurrentCount() {
        log.info("addCurrentCount");

        this.currentCount += 1;
    }

    /**
     * 현재인원 감소
     */
    public void reduceCurrentCount() {
        this.currentCount -= 1;
    }


}