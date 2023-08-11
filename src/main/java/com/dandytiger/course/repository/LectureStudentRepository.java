package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecturestudent.LectureStudent;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
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
}
