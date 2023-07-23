package com.dandytiger.course.domain.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {

    private Long id;

    private Integer grade;

    private String major;

    private String name;

    // private String password;

}
