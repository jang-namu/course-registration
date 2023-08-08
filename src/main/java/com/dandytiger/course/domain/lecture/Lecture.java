package com.dandytiger.course.domain.lecture;

import com.dandytiger.course.exception.NotEnoughCapacityException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter @Setter
@Slf4j
public abstract class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String korName; //과목명(한)
    private String engName; //과목명(영)

    private String type; //이수구분
    private String professorName; //교수명
    private int time; //강의 시간
    private String grade; //학년
    private String classroom; //강의실
    private int credit; //학점
    private int capacity; //수강정원
    private int currentCount; //현재인원

    //==비즈니스 로직==/

    /**
     * 현재인원 증가
     */
    public void addCurrentCount() {
        int restCapacity = this.capacity - currentCount;
        log.info("addCurrentCount");
        log.info("restCapacity = {}",restCapacity);
        if (restCapacity <= 0) {
            throw new NotEnoughCapacityException("no more capacity");
        }
        this.currentCount += 1;

    }

    /**
     * 현재인원 감소
     */
    public void reduceCurrentCount() {
        this.currentCount -= 1;
    }


}