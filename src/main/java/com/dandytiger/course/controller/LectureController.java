package com.dandytiger.course.controller;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.Major;
import com.dandytiger.course.repository.LectureRepository;
import com.dandytiger.course.service.LectureService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    /**
     * 페이지 매핑해야함
     */
}
