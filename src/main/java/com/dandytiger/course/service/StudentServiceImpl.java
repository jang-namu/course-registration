package com.dandytiger.course.service;

import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.repository.StudentRepository;
import com.dandytiger.course.web.form.JoinForm;
import com.dandytiger.course.web.form.StudentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


/*
    service에 모든 비즈니스 로직과 로그인 검증 등을 넣을까?
 */

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {

    private final StudentRepository studentRepository;

    public Student login(StudentForm studentForm) {

        Optional<Student> optionalStudent = studentRepository.findByLoginId(studentForm.getId());

        if (optionalStudent.isEmpty()) {
            return null;
        }

        Student student = optionalStudent.get();

        if (!student.getPassword().equals(studentForm.getPw())) {
            return null;
        }

        return student;
    }

    public boolean join(JoinForm joinForm) {

        Optional<Student> optionalStudent = studentRepository.findByLoginId(joinForm.getLoginId());

        if (optionalStudent.isPresent()) {
            return false;
        }

        Student savedStudent = Student.builder()
                .grade(joinForm.getGrade())
                .name(joinForm.getName())
                .major(joinForm.getMajor())
                .loginId(joinForm.getLoginId())
                .password(joinForm.getPw())
                .build();

        studentRepository.save(savedStudent);

        return true;
    }

    public Optional<Student> findById(Long studentId){
        return studentRepository.findById(studentId);
    }

    // 테스트용 Join ..
    public Student testJoin(Student student){
        studentRepository.save(student);
        return student;
    }

}

