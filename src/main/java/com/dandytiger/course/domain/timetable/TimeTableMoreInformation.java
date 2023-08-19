package com.dandytiger.course.domain.timetable;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class TimeTableMoreInformation {
    // 요일
    private int day;

    // 시작 시간
    private int startTIme;

    // 종료 시간
    private int endTime;

    // 시작시간 ~ 종료시간 (보기쉽게)
    private String startTimeToEndTime;

    // 강의실
    private String classroom;

    public void initData(int day, int startTIme, int endTime, String startTimeToEndTime, String classroom) {
        this.day = day;
        this.startTIme = startTIme;
        this.endTime = endTime;
        this.startTimeToEndTime = startTimeToEndTime;
        this.classroom = classroom;
    }

    public TimeTableMoreInformation() {
    }
}
