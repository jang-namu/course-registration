package com.dandytiger.course.web.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentForm {

    @NotEmpty(message = "학번을 입력하세요")
    private String id;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String pw;

}
