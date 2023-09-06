package com.dandytiger.course.domain.dto;

import com.dandytiger.course.domain.timetable.TimeTableMoreInformation;
import lombok.Data;

@Data
public class TimeTableMoreInformationDto {
    private int day;
    private int startTime;
    private int endTime;
    private String classroom;

    public TimeTableMoreInformationDto(TimeTableMoreInformation timeTableMoreInformation) {
        this.day = timeTableMoreInformation.getDay();
        this.startTime = timeTableMoreInformation.getStartTIme();
        this.endTime = timeTableMoreInformation.getEndTime();
        this.classroom = timeTableMoreInformation.getClassroom();
    }
}
