package com.example.germanlearning.service;

import com.example.germanlearning.dto.LessonDTO;
import java.util.List;

public interface LessonService {
    List<LessonDTO> getLessonsByTopicId(Long topicId);
    LessonDTO getLessonById(Long lessonId);
    long countLessons();
}

