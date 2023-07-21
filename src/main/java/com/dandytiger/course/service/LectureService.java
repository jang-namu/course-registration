package com.dandytiger.course.service;

import com.dandytiger.course.domain.lecture.Lecture;
import com.dandytiger.course.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    @Transactional
    public void saveLecture(Lecture lecture) {
        lectureRepository.save(lecture);
    }

    public List<Lecture> findLectures() {
        return lectureRepository.findAll();
    }

    public Lecture findOne(Long lectureId) {
        return lectureRepository.findOne(lectureId);
    }
}
