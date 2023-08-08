package com.dandytiger.course.web.controller;

import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.domain.student.dto.StudentResponseDto;
import com.dandytiger.course.service.StudentServiceImpl;
import com.dandytiger.course.web.form.JoinForm;
import com.dandytiger.course.web.form.StudentForm;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StudentServiceImpl studentService;

    @GetMapping("/")
    public String home(@ModelAttribute("studentForm") StudentForm studentForm,
                       HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);

        if (session != null) {
            return "redirect:/registration";
        }

        return "home";
    }


    @PostMapping("/")
    public String login(@Validated @ModelAttribute("studentForm") StudentForm studentForm, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()){
            return "home";
        }

        Student student = studentService.login(studentForm);

        if (student == null) {
            return "home";
        }

        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);

        session.setAttribute("student", student);
        session.setMaxInactiveInterval(1800);

        return "redirect:/registration";
    }


    @GetMapping("/join")
    public String joinPage(@ModelAttribute("joinForm")JoinForm joinForm) {
        return "join";
    }


    @PostMapping("/join")
    public String join(@Validated @ModelAttribute("joinForm") JoinForm joinForm,BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return "join";
        }

        if (!studentService.join(joinForm)) {
            return "join";
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/ ";
    }

}
