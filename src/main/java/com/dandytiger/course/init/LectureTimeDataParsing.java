package com.dandytiger.course.init;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j
public class LectureTimeDataParsing {
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
    public void initDayMap() {
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
    public void initPeriodMap(){
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
    public ArrayList<ArrayList<Object>> parsingData(String data) {
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
}


