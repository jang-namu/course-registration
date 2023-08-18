package com.dandytiger.course.domain.schedule;

import com.dandytiger.course.domain.lecture.Lecture;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Schedule {
    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Lecture lecture;

    @Embedded
    private ScheduleMoreInformation scheduleMoreInformation;

    private String code;

    public Schedule(Lecture lecture, ScheduleMoreInformation scheduleMoreInformation, String code) {
        this.lecture = lecture;
        this.scheduleMoreInformation = scheduleMoreInformation;
        this.code = code;
    }

    public Schedule() {
    }
}
