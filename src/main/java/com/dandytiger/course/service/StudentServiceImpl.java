package com.dandytiger.course.service;

import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.domain.student.dto.StudentResponseDto;
import com.dandytiger.course.repository.StudentRepository;
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

    public StudentResponseDto findById(Long id) throws Exception {
        Optional<Student> student = studentRepository.findById(id);

        Student selectedStudent;
        if (student.isPresent()) {
            selectedStudent = student.get();
        } else {
            throw new Exception();
        }

        /*
            password 정보는 dto에 넣을까?
        */
        return StudentResponseDto.builder()
                .id(selectedStudent.getId())
                .name(selectedStudent.getName())
                .grade(selectedStudent.getGrade())
                .major(selectedStudent.getMajor())
                .build();
    }



}

