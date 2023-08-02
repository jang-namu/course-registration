package com.dandytiger.course.web.controller;

import com.dandytiger.course.web.form.StudentForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@ModelAttribute("studentForm") StudentForm studentForm) {
        return "home";
    }


}
