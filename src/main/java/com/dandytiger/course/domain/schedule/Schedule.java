package com.dandytiger.course.domain.schedule;

import com.dandytiger.course.domain.lecture.Lecture;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Schedule {
    @Id @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Lecture lecture;

    @Embedded
    private ScheduleMoreInformation scheduleMoreInformation;

}
