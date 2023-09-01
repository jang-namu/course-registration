package com.dandytiger.course.domain.dto;

import com.dandytiger.course.domain.student.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class StudentDto {
    private Long id;
    private String major;
    private String name;
    private int currentCredit;

    public void transferDto(Student student){
        this.id = student.getId();
        this.major = student.getMajor();
        this.name = student.getName();
        this.currentCredit = student.getCurrentCredit();
    }

}
