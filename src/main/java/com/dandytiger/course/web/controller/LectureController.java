package com.dandytiger.course.web.controller;

import com.dandytiger.course.domain.dto.LectureDto;
import com.dandytiger.course.domain.dto.LectureStudentDto;
import com.dandytiger.course.domain.dto.StudentDto;
import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.service.LectureService;
import com.dandytiger.course.service.LectureStudentService;
import com.dandytiger.course.service.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Log4j2
public class LectureController {

    private final LectureService lectureService;
    private final StudentServiceImpl studentService;
    private final LectureStudentService lectureStudentService;


    //전공과목
    @GetMapping("/major")
    public String majorLecture(Model model, HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);

        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            session.invalidate();
            return "redirect:/";
        }

        Student findStudent = studentService.findById(student.getId()).get();

        StudentDto studentDto = new StudentDto();
        studentDto.transferDto(student);
        model.addAttribute("student", studentDto);


        List<LectureDto> lectures = lectureService.findMLecture(student.getMajor())
                .stream().map(l->new LectureDto(l))
                .collect(Collectors.toList());

        model.addAttribute("lectures", lectures);

        List<LectureStudentDto> lectureStudents = lectureStudentService.findLectureStudents()
                        .stream().map(ls->new LectureStudentDto(ls))
                        .collect(Collectors.toList());

        model.addAttribute("lectureStudents",lectureStudents);

        return "courseRegistration/mainForm";
    }

    //교양과목
    @GetMapping("/generalElective")
    public String GELecture(Model model, HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);

        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            session.invalidate();
            return "redirect:/";
        }

        Student findStudent = studentService.findById(student.getId()).get();
        StudentDto studentDto = new StudentDto();
        studentDto.transferDto(student);
        model.addAttribute("student", studentDto);

        List<LectureDto> lectures = lectureService.findGELecture()
                .stream().map(l->new LectureDto(l))
                .collect(Collectors.toList());

        model.addAttribute("lectures", lectures);


        List<LectureStudentDto> lectureStudents = lectureStudentService.findLectureStudents()
                .stream().map(ls -> new LectureStudentDto(ls))
                .collect(Collectors.toList());
        model.addAttribute("lectureStudents",lectureStudents);

        return "courseRegistration/mainForm";
    }

    // 과목명으로 조회
    @GetMapping("/lectureName")
    public String searchByLectureName(Model model, String LectureName,HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);
        Student student = (Student) session.getAttribute("student");


        Student findStudent = studentService.findById(student.getId()).get();
        StudentDto studentDto = new StudentDto();
        studentDto.transferDto(findStudent);
        model.addAttribute("student", studentDto);


        List<LectureDto> lectures = lectureService.findByLectureName(LectureName)
                .stream().map(l -> new LectureDto(l))
                .collect(Collectors.toList());

        model.addAttribute("lectures", lectures);

        List<LectureStudentDto> lectureStudents = lectureStudentService.findLectureStudents()
                .stream().map(ls -> new LectureStudentDto(ls))
                .collect(Collectors.toList());
        model.addAttribute("lectureStudents",lectureStudents);

        return "courseRegistration/mainForm";
    }

    //과목코드로 조회
    @GetMapping("/code")
    public String searchCode(Model model, HttpServletRequest httpServletRequest, String code) {

        HttpSession session = httpServletRequest.getSession(false);

        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            session.invalidate();
            return "redirect:/";
        }

        Student findStudent = studentService.findById(student.getId()).get();
        StudentDto studentDto = new StudentDto();
        studentDto.transferDto(findStudent);
        model.addAttribute("student", studentDto);


        List<LectureDto> lectures = lectureService.findByCode(code)
                .stream().map(l->new LectureDto(l))
                .collect(Collectors.toList());

        model.addAttribute("lectures", lectures);

        List<LectureStudentDto> lectureStudents = lectureStudentService.findLectureStudents()
                .stream().map(ls -> new LectureStudentDto(ls))
                .collect(Collectors.toList());
        model.addAttribute("lectureStudents",lectureStudents);

        return "courseRegistration/mainForm";
    }

}
