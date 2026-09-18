package com.example.germanlearning.repository;

import com.example.germanlearning.entity.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
    List<Slide> findByLessonIdOrderBySlideOrderAsc(Long lessonId);
    Optional<Slide> findByLessonIdAndSlideOrder(Long lessonId, Integer slideOrder);
    long countByLessonId(Long lessonId);
}
