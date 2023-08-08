package com.dandytiger.course.web.controller;

import com.dandytiger.course.service.LectureService;
import com.dandytiger.course.service.StudentServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    private final StudentServiceImpl studentService;



    /**
     * 페이지 매핑해야함
     */

}
