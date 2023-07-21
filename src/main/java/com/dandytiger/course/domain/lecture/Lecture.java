package com.dandytiger.course.domain.lecture;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public abstract class Lecture {

    @Id @GeneratedValue
    @Column(name = "lecture_id")
    private Long id;

    private String korName; //과목명(한)
    private String engName; //과목명(영)

    private String type; //이수구분
    private String professorName; //교수명
    private int time; //강의 시간
    private int grade; //학년
    private String classroom; //강의실
    private int credit; //학점
    private int capacity; //수강정원


}
