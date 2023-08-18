package com.dandytiger.course.init;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.schedule.Schedule;
import com.dandytiger.course.domain.schedule.ScheduleMoreInformation;
import com.dandytiger.course.service.LectureService;
import com.dandytiger.course.service.LectureStudentService;
import com.dandytiger.course.service.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class MajorInitData {

    private final initService initService;
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        initService.beforeTimeDataParsingApply();
        initService.initLecture();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class initService{
        private final LectureService lectureService;
        private final LectureTimeDataParsing lectureTimeDataParsing;
        public void beforeTimeDataParsingApply(){
            lectureTimeDataParsing.initPeriodMap();
            lectureTimeDataParsing.initDayMap();
        }

        public void initLecture(){
            try{
                String excelFilePath = "src/main/java/com/dandytiger/course/init/종합시간표_전공.xlsx";

                FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));

                // 워크북(엑셀 파일) 생성
                Workbook workbook = WorkbookFactory.create(fileInputStream);

                // 첫 번째 시트 선택
                Sheet sheet = workbook.getSheetAt(0);

                // 데이터 저장을 위한 리스트 생성 (메모리 상으로 확인 생성 , DB 에 잘 들어가면 없앨 예정)
                List<Lecture> dataList = new ArrayList<>();

                // 헤더 행은 건너뛰기 위해 반복자를 첫 번째 데이터 행으로 이동
                Iterator<Row> rowIterator = sheet.iterator();
                if (rowIterator.hasNext()) {
                    rowIterator.next(); // 헤더 행은 건너뜁니다.
                }

                // 각 행을 순회하며 데이터를 읽어서 객체로 변환하여 리스트에 추가
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();



                    String major = row.getCell(2).getStringCellValue(); // 전공인지 교양인지 ?
                    // 전공중에서도 어떤 전공인가? , 교양중에서도 어떤 교양인가?
                    String division = row.getCell(4).getStringCellValue();
                    String lectureType = "전공";


                    String code = row.getCell(6).getStringCellValue(); //과목코드

                    String korName = row.getCell(7).getStringCellValue(); // 과목명
                    String professorName = row.getCell(9).getStringCellValue(); // 교수명

                    int credit = (int)row.getCell(12).getNumericCellValue();

                    //   전학년 이라는 거 때문에 나눠서 작성 -> 0 을 전학생으로 하는 걸로 (제안)
                    String grade = row.getCell(3).getStringCellValue();

                    String timeData = row.getCell(11).getStringCellValue();

                    /**
                     * 수강정원(15명)/현재인원(0)명으로 통일
                     */
                    int capacity = 15;
                    int currentCount = 0;


                    // Todo : Parsing 이전에 initDayMap 과 initPeriodMap 함수 호출해야지 정상적 Parsing 가능합니다.
                    // Todo : 여기서 Data 를 Parsing 해주세요

                    ArrayList<ArrayList<Object>> parsingData = lectureTimeDataParsing.parsingData(timeData);
//                    4. List 의 원소 { 0 : 강의실 , 1 : 요일, 2 : 시작 인덱스, 3 : 종료 인덱스, 4 : 강의 시간 String 형식 }


//                    ScheduleMoreInformation information = new ScheduleMoreInformation();
//
//                    Schedule schedule = new Schedule(lecture,information,);




                    // 하나의 0 이 강의실




                    /** Parsing Data 특징
                     * List<Triple<String,Triple<Int,Int,Int>,String>> 형태
                     * List 의 하나의 원소는 Excel 파일 시간표 속성에서 [] 하나에 포함된 데이터 -> 강의의 하루 데이터
                     * 하나의 원소중 String : 강의실, Triple<Int,Int,Int> : < 요일, 시작 인덱스, 종료 인덱스 > , String : 강의 시간 문자열
                     * */

                    // Todo : Excel 파일 Table 에 저장
                    /** 알고리즘
                     * 1. Excel 파일의 한 행을 읽음
                     * 2. 학수번호 데이터를 변수로 가짐
                     * 3. 행에서 시간표 속성을 Parsing 함
                     * 4. 2에서 변수에 저장한 학수번호 데이터와 Parsing Data 를 Schedule Table 에 저장
                     * 5. 나머지 데이터들을 Lecture Table 에 저장
                     * 6. (1 ~ 5 까지의 과정)을 Excel 파일의 모든 행에 반복
                     * */




                    Lecture lecture = new Lecture(code, korName, professorName, grade, credit, capacity, currentCount, lectureType, division, major);


                    // 이거는 아마 split 써서 파싱 해가지고 해야할듯..? 더 좋은 방법이 있다면 그 방법으로
//                lecture.setClassroom(row.getCell());
//                lecture.setTime(); 강의 시간 넣기 매우 애매 . .

                    lectureService.saveLecture(lecture);
                }
                // 리소스 정리
                workbook.close();
                fileInputStream.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
