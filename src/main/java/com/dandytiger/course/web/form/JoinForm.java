package com.dandytiger.course.web.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinForm {

    @NotEmpty(message = "학년을 입력하세요")
    private Integer grade;

    @NotEmpty(message = "이름을 입력하세요")
    private String name;

    @NotEmpty(message = "학과를 입력하세요")
    private String major;

    @NotEmpty(message = "아이디를 입력하세요")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력하세요")
    private String pw;

}
