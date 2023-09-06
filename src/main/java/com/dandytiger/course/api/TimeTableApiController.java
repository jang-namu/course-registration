package com.dandytiger.course.api;

import com.dandytiger.course.api.response.StudentTimeTableResponse;
import com.dandytiger.course.domain.dto.LectureDto;
import com.dandytiger.course.domain.dto.StudentDto;
import com.dandytiger.course.domain.dto.TimeTableMoreInformationDto;
import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.student.Student;
import com.dandytiger.course.domain.timetable.TimeTable;
import com.dandytiger.course.domain.timetable.TimeTableMoreInformation;
import com.dandytiger.course.repository.TimeTableRepository;
import com.dandytiger.course.service.LectureService;
import com.dandytiger.course.service.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TimeTableApiController {

    private final StudentServiceImpl studentService;
    private LectureService lectureService;

    @GetMapping("/api/{id}/timetable")
    public Result viewStudentTimeTableNotUserSession(@PathVariable("id") Long id){
        // 이렇게 꺼내면 안 되긴 함
        Student findStudent = studentService.findById(id).get();
        List<LectureStudent> rl = findStudent.getRegistrationLectures();
        List<LectureDto> lectureDtos = new ArrayList<>();

        for (LectureStudent lectureStudent : rl) {
            Lecture lecture = lectureStudent.getLecture();
            List<TimeTable> timeTable = lecture.getTimeTable();
            LectureDto lectureDto = new LectureDto(lecture);
            for (TimeTable table : timeTable) {
                TimeTableMoreInformation timeTableMoreInformation = table.getTimeTableMoreInformation();
                TimeTableMoreInformationDto timeTableMoreInformationDto = new TimeTableMoreInformationDto(timeTableMoreInformation);
                lectureDto.setTimeTableMoreInformationDto(timeTableMoreInformationDto);
            }
            lectureDtos.add(lectureDto);
        }
        StudentTimeTableResponse response = new StudentTimeTableResponse(lectureDtos);
        return new Result(response);
    }
    @GetMapping("/api/checkStudentId")
    public Result checkStudentId(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        Student student = (Student) session.getAttribute("student");
        if (student == null) {
            session.invalidate();
            return null;
        }
        Long checkStudentId = student.getId();
        return new Result(checkStudentId);
    }
}
