package com.dandytiger.course.web.controller;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.repository.LectureStudentSearch;
import com.dandytiger.course.repository.StudentRepository;
import com.dandytiger.course.service.LectureService;
import com.dandytiger.course.service.LectureStudentService;
import com.dandytiger.course.service.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final LectureService lectureService;
    private final LectureStudentService lectureStudentService;
    private final StudentRepository studentRepository;


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
        model.addAttribute("currentCredit",student.showCurrentCredit());

        List<Lecture> lectures = lectureService.findLectures();
        model.addAttribute("lectures",lectures);

        List<LectureStudent> lectureStudents = lectureStudentService.findLectureStudents();
        model.addAttribute("lectureStudents",lectureStudents);

        return "courseRegistration/mainForm";
    }

    // 지금은 회원가입이 없어서 일단 세션은 제외
    @PostMapping("/registration/{id}")
    public String requestCourseRegistration(@PathVariable("id") Long id,
                                            HttpServletRequest request,Model model) {
        HttpSession session = request.getSession(false);

        Student student = (Student) session.getAttribute("student");

        lectureStudentService.apply(student.getId(), id);

        log.info("apply after Current Credit = {} ",student.getCurrentCredit());
        return "redirect:/";
    }

    @GetMapping("/registration/{id}")
    public String beforeRequestCourseRegistration(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id",id);
        return "courseRegistration/beforeRequest";
    }

//    @GetMapping("/registration/{id}/applis")
//    public String applyList(@ModelAttribute("lectureStudentSearch") LectureStudentSearch lectureStudentSearch, Model model) {
//        List<LectureStudent> applies = lectureStudentService.findLectureStudents();
//        model.addAttribute("applies", applies);
//
//        return "/";
//    }
}
