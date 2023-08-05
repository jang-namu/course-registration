package com.dandytiger.course.web.controller;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.service.LectureService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final LectureService lectureService;
    @GetMapping("/registration")
    public String courseRegistration(Model model,
                                     HttpServletRequest httpServletRequest){

        HttpSession session = httpServletRequest.getSession(false);

        if (session == null) {
            return "redirect:/";
        }

        Student student = (Student) session.getAttribute("student");

        if (student == null) {
            session.invalidate();
            return "redirect:/";
        }

        model.addAttribute("name", student.getName());
        model.addAttribute("major", student.getMajor());

        List<Lecture> lectures = lectureService.findLectures();
        model.addAttribute("lectures",lectures);
        return "courseRegistration/mainForm";
    }
    @GetMapping("/registration/test")
    public String test() {
        return "test";
    }

    // 지금은 회원가입이 없어서 일단 세션은 제외
    @PostMapping("/registration/{id}")
    public String requestCourseRegistration(@PathVariable("id") Long id,
                                            @SessionAttribute(name = "student", required = true) Student student) {



        return "redirect:/";
    }
}
