package com.dandytiger.course.domain.lecture;
import com.dandytiger.course.domain.timetable.TimeTable;
import com.dandytiger.course.exception.NotEnoughCapacityException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Slf4j
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String code; //과목코드

    private String korName; //과목명(한)
    private String engName; //과목명(영)

//    private String type; //이수구분 값 타입으로 일단 수정

    private String professorName; //교수명
    private int time; //강의 시간
    private String grade; //학년
    private String classroom; //강의실
    private int credit; //학점
    private int capacity; //수강정원
    private int currentCount; //현재인원

    private String lectureType; // 전공인가 ? 교양인가 ?
    private String lectureDivision; // 이수 구분 (기초교양 , 전공기초 , 핵심교양 ...)
    private String major;

    @OneToMany(mappedBy = "lecture")
    private List<TimeTable> timeTable = new ArrayList<>();

    //==비즈니스 로직==/

    /**
     * 현재인원 증가
     */
    public void addCurrentCount() {
        int restCapacity = this.capacity - currentCount;
        log.info("addCurrentCount");
        log.info("restCapacity = {}",restCapacity);
        if (restCapacity <= 0) {
            throw new NotEnoughCapacityException("no more capacity");
        }
        this.currentCount += 1;
    }

    /**
     * 현재인원 감소
     */
    public void reduceCurrentCount() {
        this.currentCount -= 1;
    }


}