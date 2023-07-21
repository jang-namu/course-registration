package com.dandytiger.course.repository;

import com.dandytiger.course.domain.lecture.Lecture;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepository {

    private final EntityManager em;

    public void save(Lecture lecture) {
        em.persist(lecture);
    }

    //하나만 검색(강의 코드로)
    public Lecture findOne(Long id) {
        return em.find(Lecture.class, id);
    }

    /**
     * 전공별로 검색, 과목명으로, 교수명으로 검색 메소드 전부다 따로 만들어야하나?
     */

    //전체 다 검색
    public List<Lecture> findAll() {
        return em.createQuery("select l from Lecture l", Lecture.class)
                .getResultList();
    }

}
