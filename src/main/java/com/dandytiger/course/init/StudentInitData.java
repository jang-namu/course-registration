package com.dandytiger.course.init;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.service.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Component
@Slf4j
public class StudentInitData {

    private final initStudentService initStudentService;
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        initStudentService.initStudent();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class initStudentService{
        private final StudentServiceImpl studentService;
        public void initStudent() {
            Student student = new Student(3, "김지원", "컴퓨터공학부", "201901554", "1234", 0);
            Student student2 = new Student(3, "김남기", "컴퓨터공학부", "201901542", "1234", 0);

            studentService.testJoin(student);
            studentService.testJoin(student2);
        }
    }
}
