package com.dandytiger.course.exception;

import com.dandytiger.course.domain.timetable.TimeTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DuplicateTimeCheck {

    /** 함수명 : check_time_duplication
     *  매개변수 : ( Int old_start, Int old_end, Int new_start, Int new_end )
     *  반환형 : Boolean true : 중복 O / false : 중복 X
     *  함수 설명 :
     *      기존 신청 목록과 현재 신청 내용이 강의 시간이 겹치는지 확인
     * */
    public Boolean check_time_duplication(int old_start,int old_end,int new_start,int new_end){
        Boolean result = true;
        if (old_start > new_end ) result = false;
        else if (old_end < new_start) result = false;
        return result;
    }
    /** 함수명 : is_available_registration
     *  매개변수 : ( List<Registration> olds, Registration new_registration )
     *  반환형 : Boolean true : 신청가능 / false : 신청불가
     *  함수설명 :
     *      기존 신청 목록을 모두 검사하여, 수강신청 가능 여부를 반환
     * */
    public Boolean is_available_registration(List<List<TimeTable>> olds, List<TimeTable> new_registration){
        Boolean result = true ;
        log.info("is_available_registration start");

        /**
         * List<List<>> 로 파라미터를 넣어야 해서 2중 for 문..?
         * 일단 이렇게 해보고 너무 성능이 안 나오면 ..
         * JPQL 쿼리를 짜야할듯
         * ㅋㅋㅋㅋㅋㅋㅋㅋ3중 포문인데..?
         * student_id 로 timeTable 을 가져올 수 있는 한방 쿼리..
         */
        for (List<TimeTable> old : olds) {
            log.info("is_available_registration start2");
            for (TimeTable o : old) {
                log.info("두번째 for 문 start");
                for (TimeTable nr : new_registration) {
                    log.info("세번째 for 문 start");
                    if (o.getTimeTableMoreInformation().getDay() == nr.getTimeTableMoreInformation().getDay()) {
                        result = false;
                        break;
                    }else {
                        if (check_time_duplication(
                                o.getTimeTableMoreInformation().getStartTIme(),
                                o.getTimeTableMoreInformation().getEndTime(),
                                nr.getTimeTableMoreInformation().getStartTIme(),
                                nr.getTimeTableMoreInformation().getEndTime())){
                            result = false;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
}
