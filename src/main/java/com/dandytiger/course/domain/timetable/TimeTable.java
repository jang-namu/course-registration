package com.dandytiger.course.domain.timetable;

import com.dandytiger.course.domain.lecture.Lecture;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
public class TimeTable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timetable_id")
    private Long id;

    @Embedded
    private TimeTableMoreInformation timeTableMoreInformation;

    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    public void initTimeTable(Lecture lecture, TimeTableMoreInformation scheduleMoreInformation, String code) {
        this.lecture = lecture;
        this.timeTableMoreInformation = scheduleMoreInformation;
        this.code = code;
    }
    public TimeTable() {
    }
}
