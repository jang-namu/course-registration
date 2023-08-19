package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecturestudent.LectureStudent;
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

    public void reduceAllWaitLectureStudents(Long id) {

        em.createQuery("update LectureStudent ls set ls.waitingNumber = ls.waitingNumber - 1 where ls.waitingNumber > 0 and ls.lectureId = : id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public void delete(LectureStudent lectureStudent) {
        em.remove(lectureStudent);
    }
}
