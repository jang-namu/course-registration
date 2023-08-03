package com.dandytiger.course.domain.lecturestudent;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LectureStudent {

    @Id
    @GeneratedValue

    private Long id;

    // 대기 번호
    private int waitingNumber;

    // 상태
    @Enumerated(EnumType.STRING)
    private registrationStatus status;

    // 시간
    private LocalDateTime registrationTime;

}