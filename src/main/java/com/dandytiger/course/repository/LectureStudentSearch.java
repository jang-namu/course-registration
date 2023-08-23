package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecturestudent.RegistrationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LectureStudentSearch {

    private String studentName;
    private RegistrationStatus registrationStatus;
}
