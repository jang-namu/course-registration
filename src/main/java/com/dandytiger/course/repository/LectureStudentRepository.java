package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.domain.lecture.QLecture;
import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import com.dandytiger.course.domain.lecturestudent.QLectureStudent;
import com.dandytiger.course.domain.timetable.QTimeTable;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LectureStudentRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;
    QLectureStudent ls = QLectureStudent.lectureStudent;
    QLecture l = QLecture.lecture;
    QTimeTable t = QTimeTable.timeTable;

    public void save(LectureStudent lectureStudent) {
        em.persist(lectureStudent);
    }

    public LectureStudent findOne(Long id) {
        return em.find(LectureStudent.class, id);
    }


    public List<LectureStudent> findAll() {
        return em.createQuery("select ls from LectureStudent ls", LectureStudent.class)
                .getResultList();
    }

    public void reduceAllWaitLectureStudents(Lecture lecture) {
        em.createQuery("update LectureStudent ls set ls.waitingNumber = ls.waitingNumber - 1 where ls.waitingNumber > 0 and ls.lecture = : lecture")
                .setParameter("lecture", lecture)
                .executeUpdate();
    }

    public void delete(LectureStudent lectureStudent) {
        em.remove(lectureStudent);
    }

    public List<LectureStudent> findRegistrationLectures(Long studentId) {
        return query.selectFrom(ls)
                .rightJoin(ls.lecture, l).fetchJoin()
                .rightJoin(ls.lecture.timeTable, t).fetchJoin()
                .where(ls.student.id.eq(studentId))
                .distinct().fetch();
    }
}
