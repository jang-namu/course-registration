package com.dandytiger.course.web.controller;

import com.dandytiger.course.domain.dto.LectureDto;
import com.dandytiger.course.domain.dto.LectureStudentDto;
import com.dandytiger.course.domain.dto.StudentDto;
import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.domain.timetable.TimeTable;
import com.dandytiger.course.repository.LectureStudentSearch;
import com.dandytiger.course.repository.StudentRepository;
import com.dandytiger.course.repository.TimeTableRepository;
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
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final LectureService lectureService;
    private final LectureStudentService lectureStudentService;
    private final StudentServiceImpl studentService;
    private final TimeTableRepository timeTableRepository;


    //로그인 후 메인화면
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

        Student findStudent = studentService.findById(student.getId()).get();
        StudentDto studentDto = new StudentDto();
        studentDto.transferDto(findStudent);
        model.addAttribute("student", studentDto);


        List<LectureDto> lectures = lectureService.findLectures()
                .stream().map(l->new LectureDto(l))
                .collect(Collectors.toList());
        model.addAttribute("lectures",lectures);

        List<LectureStudentDto> lectureStudents = lectureStudentService.findLectureStudents()
                .stream().map(ls-> new LectureStudentDto(ls))
                .collect(Collectors.toList());
        model.addAttribute("lectureStudents",lectureStudents);

        return "courseRegistration/mainForm";
    }

    // 신청
    @PostMapping("/registration/{id}")
    public String requestCourseRegistration(@PathVariable("id") Long id,
                                            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Student student = (Student) session.getAttribute("student");

        lectureStudentService.apply(student.getId(), id);

        return "redirect:/";
    }

    //수강 취소
    @PostMapping("/registration/{id}/cancel")
    public String cancelCourseRegistration(@PathVariable("id") Long id) {

        lectureStudentService.cancelApply(id);

        return "redirect:/";
    }

    @GetMapping("/test")
    public String test(){
        return "timeTable";
    }
}
