package com.dandytiger.course.domain.student;

import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.exception.ExceedCreditException;
import com.dandytiger.course.exception.NotEnoughCapacityException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter // 이거 열면 안 되는데 test 떄문에 일단 넣어놓게요
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

    private int currentCredit;

    public int showCurrentCredit(){
        return this.currentCredit;
    }

    public void addCurrentCredit(int lectureCredit) {
        int restCapacity = this.currentCredit + lectureCredit;
        if (restCapacity >= 22) {
            throw new ExceedCreditException("학점 초과");
        }
        this.currentCredit+=lectureCredit;
    }

//     학생이 자신이 수강 신청한 과목들을 가져올 경우가 많을 거 같아 양방향 매핑
    @OneToMany(mappedBy ="student")
    private List<LectureStudent> registrationLectures = new ArrayList<>();
}
