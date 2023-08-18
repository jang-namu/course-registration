package com.dandytiger.course.exception.advice;

import com.dandytiger.course.exception.ApplySameLectureException;
import com.dandytiger.course.exception.ExceedCreditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExceedCreditException.class)
        public ModelAndView exceedCreditException(ExceedCreditException e){
        return new ModelAndView("error/exceedCredit");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplySameLectureException.class)
    public ModelAndView exceedCreditException(ApplySameLectureException e){
        return new ModelAndView("error/applySameLectureException");
    }
}
