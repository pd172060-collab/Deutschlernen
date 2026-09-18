package com.example.germanlearning.service;

import com.example.germanlearning.entity.UserProgress;

public interface UserProgressService {
    UserProgress recordLessonProgress(Long userId, Long lessonId, Integer currentSlideOrder, boolean isCompleted);
    UserProgress markLessonCompleted(Long userId, Long lessonId);
    boolean isLessonCompleted(Long userId, Long lessonId);
}

