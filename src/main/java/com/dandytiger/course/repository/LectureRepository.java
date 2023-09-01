package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.QLecture;
import com.dandytiger.course.domain.timetable.QTimeTable;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.util.StringUtils.*;

@Repository
@RequiredArgsConstructor
@Slf4j
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

    public List<Lecture> findByLectureNameDynamicQuery(String name){
        return query.selectFrom(l)
                .where(nameEq(name))
                .fetch();
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? l.korName.contains(name) : null;
    }

    public List<Lecture> findByLectureCodeDynamicQuery(String code){
        return query.selectFrom(l)
                .where(codeEq(code))
                .fetch();
    }

    private BooleanExpression codeEq(String code) {
        return hasText(code) ? l.code.contains(code) : null;
    }

    public List<Lecture> findGELecture() {
        return em.createQuery("select l from Lecture l where l.lectureType=:type",Lecture.class)
                .setParameter("type","교양")
                .getResultList();
    }

    public List<Lecture> findMLecture(String major) {
        log.info("major = {} ", major);
        return em.createQuery("select l from Lecture l where l.major=:type",Lecture.class)
                .setParameter("type",major)
                .getResultList();
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
