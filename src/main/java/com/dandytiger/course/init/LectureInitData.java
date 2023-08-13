//package com.dandytiger.course.init;
//
//import com.dandytiger.course.domain.lecture.Lecture;
//import com.dandytiger.course.domain.lecture.Major;
//import com.dandytiger.course.domain.student.Student;
//import com.dandytiger.course.service.LectureService;
//import com.dandytiger.course.service.LectureStudentService;
//import com.dandytiger.course.service.StudentServiceImpl;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.*;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class LectureInitData {
//
//    private final initService initService;
//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        initService.initLecture();
//        initService.initStudent();
////        initService.initTest();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class initService{
//        private final LectureService lectureService;
//        private final StudentServiceImpl studentService;
//        private final LectureStudentService lectureStudentService;
//
//        public void initLecture(){
//            try{
//
//                log.info("LectureInitData.init() 시작");
//                // 엑셀 파일 경로 공통화 시키기 위해서 변경할 필요가 있음
//                String excelFilePath = "/Users/supportkim/Desktop/종합시간표.xlsx";
////                (base) supportkim@gimjiwon-ui-MacBookPro ~ % /Users/supportkim/Desktop/종합시간 표.xlsx
//
//                FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
//
//                // 워크북(엑셀 파일) 생성
//                Workbook workbook = WorkbookFactory.create(fileInputStream);
//
//                // 첫 번째 시트 선택
//                Sheet sheet = workbook.getSheetAt(0);
//
//                // 데이터 저장을 위한 리스트 생성 (메모리 상으로 확인 생성 , DB 에 잘 들어가면 없앨 예정)
//                List<Lecture> dataList = new ArrayList<>();
//
//                // 헤더 행은 건너뛰기 위해 반복자를 첫 번째 데이터 행으로 이동
//                Iterator<Row> rowIterator = sheet.iterator();
//                if (rowIterator.hasNext()) {
//                    rowIterator.next(); // 헤더 행은 건너뜁니다.
//                }
//
//                // 각 행을 순회하며 데이터를 읽어서 객체로 변환하여 리스트에 추가
//                while (rowIterator.hasNext()) {
//                    Row row = rowIterator.next();
//
//                    Major lecture = new Major();
//
//                    lecture.setMajor(row.getCell(2).getStringCellValue()); //학과
//                    log.info("Type = {} ",row.getCell(2).getCellType());
//
//                    lecture.setCode(row.getCell(6).getStringCellValue()); //과목코드
//
//                    lecture.setKorName(row.getCell(7).getStringCellValue()); // 과목명
//                    lecture.setType(row.getCell(4).getStringCellValue()); // 이수구분
//                    lecture.setProfessorName(row.getCell(9).getStringCellValue()); // 교수명
//
//                    lecture.setCredit((int)row.getCell(12).getNumericCellValue());
//
//                    //   전학년 이라는 거 때문에 나눠서 작성 -> 0 을 전학생으로 하는 걸로 (제안)
//                    lecture.setGrade(row.getCell(3).getStringCellValue());
//
//                    /**
//                     * 수강정원(15명)/현재인원(0)명으로 통일
//                     */
//                    lecture.setCapacity(15);
//                    lecture.setCurrentCount(0);
//
//                    log.info("LectureInitData CurrentCount =  {}",lecture.getCurrentCount());
//
//                    // 이거는 아마 split 써서 파싱 해가지고 해야할듯..? 더 좋은 방법이 있다면 그 방법으로
////                lecture.setClassroom(row.getCell());
////                lecture.setTime(); 강의 시간 넣기 매우 애매 . .
//
//                    Lecture realLecture = lecture;
//                    lectureService.saveLecture(realLecture);
//                }
//                // 리소스 정리
//                workbook.close();
//                fileInputStream.close();
//            }catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        public void initStudent() {
//            Student student = new Student();
//            student.setName("김지원");
//            student.setLoginId("201901554");
//            student.setGrade(3);
//            student.setPassword("1234");
//            student.setMajor("컴퓨터공학과");
//            studentService.testJoin(student);
//
//            List<Lecture> lectures = lectureService.findLectures();
//            int capacity1 = lectures.get(0).getCurrentCount();
//            int capacity2 = lectures.get(1).getCurrentCount();
//
//
//            log.info("initStudent after -> {}",capacity1);
//            log.info("initStudent after -> {}",capacity2);
//        }
//
//        public void initTest(){
//            Student testStudent = new Student();
//            testStudent.setName("테스트");
//            studentService.testJoin(testStudent);
//            List<Lecture> lectures = lectureService.findLectures();
//            Lecture testLecture = lectures.get(23);
//            for (int i = 0; i < 30; i++) {
//                lectureStudentService.apply(testStudent.getId(), testLecture.getId());
//            }
//        }
//    }
//
//}
