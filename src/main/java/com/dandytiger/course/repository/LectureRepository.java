package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.QLecture;
import com.dandytiger.course.domain.timetable.QTimeTable;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;
    QLecture l = QLecture.lecture;
    QTimeTable t = QTimeTable.timeTable;

    public void save(Lecture lecture) {
        em.persist(lecture);
    }

    //하나만 검색(pk값으로)
    public Lecture findOne(Long id) {
        return em.find(Lecture.class, id);
    }

    //==동적쿼리==//

    //과목명으로 검색
    public List<Lecture> findByLectureName(String name) {
        return query.selectFrom(l)
                .where(l.korName.contains(name))
                .fetch();
    }

    //과목코드으로 검색
    public List<Lecture> findByCode(String code) {
        return query.selectFrom(l)
                .where(l.code.contains(code))
                .fetch();
    }


    //교양과목 검색
    public List<Lecture> findGELecture() {
        return query.selectFrom(l)
                .where(l.lectureType.contains("교양"))
//                .where(l.type.contains("교양"))
                .fetch();
    }

    //전공과목 검색(지금은 강의가 컴공밖에 없어서 이렇게 만듬
    public List<Lecture> findMLecture(String major) {
        return query.selectFrom(l)
                .where(l.major.contains(major))
                .fetch();
    }
    //==동적쿼리 끝==//

    //전체 다 검색
    public List<Lecture> findAll() {
        return em.createQuery("select l from Lecture l", Lecture.class)
                .getResultList();
    }

    public Lecture findOneWithFetchJoin(Long id){
        return query.selectFrom(l)
                .rightJoin(l.timeTable,t).fetchJoin()
                .where(l.id.eq(id))
                .fetchOne();

    }

}
