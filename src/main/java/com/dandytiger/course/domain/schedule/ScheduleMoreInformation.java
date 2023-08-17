package com.dandytiger.course.domain.schedule;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class ScheduleMoreInformation {

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

}
