package com.dandytiger.course.repository;
import com.dandytiger.course.domain.timetable.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable,Long> {
}
