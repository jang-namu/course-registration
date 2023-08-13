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
            Student student = new Student();
            student.setName("김지원");
            student.setLoginId("201901554");
            student.setGrade(3);
            student.setPassword("1234");
            student.setMajor("컴퓨터공학과");
            student.setCurrentCredit(0);
            studentService.testJoin(student);
        }
    }
}
