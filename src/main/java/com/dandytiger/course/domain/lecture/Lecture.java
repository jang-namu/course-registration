package com.dandytiger.course.domain.lecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.timetable.TimeTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Slf4j
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String code; //과목코드
    private String korName; //과목명(한)
    private String professorName; //교수명
    private String grade; //학년
    private int credit; //학점
    private int capacity; //수강정원
    private int currentCount; //현재인원

    private String lectureType; // 전공인가 ? 교양인가 ?
    private String lectureDivision; // 이수 구분 (기초교양 , 전공기초 , 핵심교양 ...)
    private String major; // 전공학과

    // time field 관련 오류 뜨는게 존재,,(일부 컴퓨터에서만)
//    private int time;

    @OneToMany(mappedBy = "lecture")
    private List<TimeTable> timeTable = new ArrayList<>();

    public Lecture(String code, String korName, String professorName, String grade, int credit,
                   int capacity, int currentCount, String lectureType, String lectureDivision, String major) {
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

    public void initTimeTable(List<TimeTable> timeTables){
        this.timeTable = timeTables;
    }

    public Lecture() {
    }

    //==비즈니스 로직==/

    /**
     * 현재인원 증가
     */
    public void addCurrentCount() {
        this.currentCount += 1;
    }

    /**
     * 현재인원 감소
     */
    public void reduceCurrentCount() {
        this.currentCount -= 1;
    }


}