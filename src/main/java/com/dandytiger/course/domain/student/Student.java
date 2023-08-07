package com.dandytiger.course.domain.student;

import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Long id;

    private Integer grade;

    private String name;

    private String major;

    private String loginId;

    private String password;

    // 학생이 자신이 수강 신청한 과목들을 가져올 경우가 많을 거 같아 단방향 매핑
    @OneToMany
    @JoinColumn(name="student_id")
    private List<LectureStudent> registrationLectures = new ArrayList<>();


}
