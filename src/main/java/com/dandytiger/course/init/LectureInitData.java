package com.dandytiger.course.init;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.Major;
import com.dandytiger.course.service.LectureService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class LectureInitData {

    static class Triple<A, B, C> {
        A first;
        B second;
        C third;

        public Triple(A first, B second, C third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    }
    private int firstClassIndex = 0;
    private int firstDayIndex = 0;
    private Map<String, Triple<Integer, Integer, String>> periodMap = new HashMap<>();
    private Map<Character,Integer> dayMap = new HashMap<>();

    /** 함수명 : initDayMap
     *  함수설명 : 요일정보를 Index 로 가지고있는 Map
     * */
    private void initDayMap() {
        dayMap.put('월', firstDayIndex);
        dayMap.put('화', firstDayIndex + 1);
        dayMap.put('수', firstDayIndex + 2);
        dayMap.put('목', firstDayIndex + 3);
        dayMap.put('금', firstDayIndex + 4);
        dayMap.put('토', firstDayIndex + 5);
    }

    /** 함수명 : initPeriodMap
     *  함수설명 : 시간표 데이터를 Index 정보로 가지고 있는 Map 초기화
     * */
    private void initPeriodMap(){
        periodMap.put("0", new Triple<>(firstClassIndex + 2, firstClassIndex + 5, "08:00 ~ 08:50"));
        periodMap.put("1", new Triple<>(firstClassIndex + 6, firstClassIndex + 9, "09:00 ~ 09:50"));
        periodMap.put("2", new Triple<>(firstClassIndex + 10, firstClassIndex + 13, "10:00 ~ 10:50"));
        periodMap.put("3", new Triple<>(firstClassIndex + 14, firstClassIndex + 17, "11:00 ~ 11:50"));
        periodMap.put("4", new Triple<>(firstClassIndex + 18, firstClassIndex + 21, "12:00 ~ 12:50"));
        periodMap.put("5", new Triple<>(firstClassIndex + 22, firstClassIndex + 25, "13:00 ~ 13:50"));
        periodMap.put("6", new Triple<>(firstClassIndex + 26, firstClassIndex + 29, "14:00 ~ 14:50"));
        periodMap.put("7", new Triple<>(firstClassIndex + 30, firstClassIndex + 33, "15:00 ~ 15:50"));
        periodMap.put("8", new Triple<>(firstClassIndex + 34, firstClassIndex + 37, "16:00 ~ 16:50"));
        periodMap.put("9", new Triple<>(firstClassIndex + 38, firstClassIndex + 41, "17:00 ~ 17:50"));
        periodMap.put("0A-0", new Triple<>(firstClassIndex, firstClassIndex + 4, "07:30 ~ 08:45"));
        periodMap.put("1-2A", new Triple<>(firstClassIndex + 6, firstClassIndex + 10, "09:00 ~ 10:15"));
        periodMap.put("2B-3", new Triple<>(firstClassIndex + 12, firstClassIndex + 16, "10:30 ~ 11:45"));
        periodMap.put("4-5A", new Triple<>(firstClassIndex + 18, firstClassIndex + 22, "12:00 ~ 13:15"));
        periodMap.put("5B-6", new Triple<>(firstClassIndex + 24, firstClassIndex + 28, "13:30 ~ 14:45"));
        periodMap.put("7-8A", new Triple<>(firstClassIndex + 30, firstClassIndex + 34, "15:00 ~ 16:15"));
        periodMap.put("8B-9", new Triple<>(firstClassIndex + 36, firstClassIndex + 40, "16:30 ~ 17:45"));
        periodMap.put("야1", new Triple<>(firstClassIndex + 42, firstClassIndex + 45, "18:00 ~ 18:50"));
        periodMap.put("야2", new Triple<>(firstClassIndex + 46, firstClassIndex + 49, "18:55 ~ 19:45"));
        periodMap.put("야3", new Triple<>(firstClassIndex + 50, firstClassIndex + 53, "19:50 ~ 20:40"));
        periodMap.put("야4", new Triple<>(firstClassIndex + 54, firstClassIndex + 57, "20:45 ~ 21:35"));
        periodMap.put("야5", new Triple<>(firstClassIndex + 58, firstClassIndex + 61, "21:40 ~ 22:30"));
        periodMap.put("야1-2A", new Triple<>(firstClassIndex + 42, firstClassIndex + 46, "18:00 ~ 19:15"));
        periodMap.put("야2B-3", new Triple<>(firstClassIndex + 48, firstClassIndex + 52, "19:25 ~ 20:40"));
        periodMap.put("야4-5A", new Triple<>(firstClassIndex + 54, firstClassIndex + 58, "20:50 ~ 22:05"));

    }
    /** 함수명 : parsingData
     *  매개변수 : String
     *  반환형 : ArrayList<Object>
     *  함수 설명 :
     *      1. 매개변수로 종합시간표.xlsx 파일의 시간표 속성을 받는다.
     *      2. 강의실 []을 기준으로 1차로 분류한다.
     *      3. 같은 강의실일때, 강의 쉬는시간 () 로 2차 분류한다.
     *      4. List 의 원소 { 0 : 강의실 , 1 : 요일, 2 : 시작 인덱스, 3 : 종료 인덱스, 4 : 강의 시간 String 형식 }
     * */
    private ArrayList<ArrayList<Object>> parsingData(String data) {
        String dayClass = "";
        List<String> one_classroom_lectures = new ArrayList<>();
        ArrayList<ArrayList<Object>> parsing_data = new ArrayList<>();

        char[] dataChars = data.toCharArray();

        for (char c : dataChars) {
            switch (c) {
                case '[':
                    dayClass = "";
                    break;
                case ']':
                    one_classroom_lectures.add(dayClass);
                    break;
                default:
                    dayClass += c;
                    break;
            }
        }

        for (String ocl : one_classroom_lectures) {
            String[] classroom_schedule = ocl.split(":");     // 강의실과 수업 데이터 분리
            String[] one_day_schedule = classroom_schedule[1].split(","); // 요일에 따른 수업 분리

            for (String schedule : one_day_schedule) {
                schedule = schedule.trim();
                int day = dayMap.get(schedule.charAt(0));
                String start = "",end = "",temp = "";

                for (int i = 1; i<schedule.length();i++){
                    switch (schedule.charAt(i)){
                        case '(':
                            temp = "";
                            break;
                        case ')':
                            if (start.equals("")) start = temp;
                            end = temp;
                            break;
                        default:
                            temp += schedule.charAt(i);
                            break;
                    }
                }

                Triple<Integer,Integer,String> start_data = periodMap.get(start);
                Triple<Integer,Integer,String> end_data = periodMap.get(end);
                String time_str = start_data.third.substring(0,8) + end_data.third.substring(8);

                parsing_data.add(new ArrayList<>(
                        Arrays.asList(
                                classroom_schedule[0],
                                day,
                                start_data.first,
                                end_data.second,
                                time_str)));
            }
        }
        return parsing_data;
    }
    private final initService initService;
    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class initService{
        private final LectureService lectureService;

        public void init() {
            try{

                log.info("LectureInitData.init() 시작");
                // 엑셀 파일 경로 공통화 시키기 위해서 변경할 필요가 있음
                String excelFilePath = "/Users/namu/강의계획서.xlsx";
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

                    Major lecture = new Major();

                    lecture.setMajor("컴퓨터공학부");


                    lecture.setKorName(row.getCell(7).getStringCellValue()); // 과목명
                    lecture.setType(row.getCell(4).getStringCellValue()); // 이수구분
                    lecture.setProfessorName(row.getCell(9).getStringCellValue()); // 교수명

                    lecture.setCredit((int)row.getCell(12).getNumericCellValue());

                    //   전학년 이라는 거 때문에 나눠서 작성 -> 0 을 전학생으로 하는 걸로 (제안)
                    lecture.setGrade(row.getCell(5).getStringCellValue());

                    // Todo : Parsing 이전에 initDayMap 과 initPeriodMap 함수 호출해야지 정상적 Parsing 가능합니다.
                    // Todo : 여기서 Data 를 Parsing 해주세요
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
                    // 이거는 아마 split 써서 파싱 해가지고 해야할듯..? 더 좋은 방법이 있다면 그 방법으로
//                lecture.setClassroom(row.getCell());
//                lecture.setTime(); 강의 시간 넣기 매우 애매 . .

                    lectureService.saveLecture(lecture);
                    log.info("LectureInitData.init() 끝");

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
