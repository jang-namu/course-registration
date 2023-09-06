package com.dandytiger.course.api.response;

import com.dandytiger.course.domain.dto.LectureDto;
import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.timetable.TimeTable;
import lombok.Data;

import java.sql.Array;
import java.util.List;

@Data
public class StudentTimeTableResponse {
    private List<LectureDto> lectureDtos;

    public StudentTimeTableResponse(List<LectureDto> lectureDtos) {
        this.lectureDtos = lectureDtos;
    }
}