package com.example.germanlearning.service;

import com.example.germanlearning.dto.SlideDTO;
import java.util.List;

public interface SlideService {
    List<SlideDTO> getSlidesByLessonId(Long lessonId);
    SlideDTO getSlideById(Long slideId);
    SlideDTO getSlideByLessonAndOrder(Long lessonId, Integer slideOrder);
    long countSlidesByLessonId(Long lessonId);
    SlideDTO getFirstSlideOfLesson(Long lessonId);
}

