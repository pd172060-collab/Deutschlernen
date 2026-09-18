package com.example.germanlearning.service.impl;

import com.example.germanlearning.entity.Lesson;
import com.example.germanlearning.entity.User;
import com.example.germanlearning.entity.UserProgress;
import com.example.germanlearning.exception.ResourceNotFoundException;
import com.example.germanlearning.repository.LessonRepository;
import com.example.germanlearning.repository.SlideRepository;
import com.example.germanlearning.repository.UserProgressRepository;
import com.example.germanlearning.repository.UserRepository;
import com.example.germanlearning.service.UserProgressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserProgressServiceImpl implements UserProgressService {

    private final UserProgressRepository userProgressRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final SlideRepository slideRepository;

    public UserProgressServiceImpl(
            UserProgressRepository userProgressRepository,
            UserRepository userRepository,
            LessonRepository lessonRepository,
            SlideRepository slideRepository) {
        this.userProgressRepository = userProgressRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.slideRepository = slideRepository;
    }

    @Override
    public UserProgress recordLessonProgress(Long userId, Long lessonId, Integer currentSlideOrder, boolean isCompleted) {
        User user = userRepository.findById(userId)
                .orElseGet(() -> {
                    // Fallback to first existing user or create demo user
                    return userRepository.findAll().stream().findFirst()
                            .orElseGet(() -> userRepository.save(new User("learndeutsch", "user@deutschlernen.com", "German Learner")));
                });

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with ID: " + lessonId));

        int totalSlides = (int) slideRepository.countByLessonId(lessonId);

        UserProgress progress = userProgressRepository.findByUserIdAndLessonId(user.getId(), lessonId)
                .orElseGet(() -> new UserProgress(user, lesson, "IN_PROGRESS"));

        progress.setTotalSlides(totalSlides);
        if (currentSlideOrder != null && currentSlideOrder > progress.getCompletedSlides()) {
            progress.setCompletedSlides(currentSlideOrder);
        }

        if (isCompleted || (currentSlideOrder != null && currentSlideOrder >= totalSlides)) {
            progress.setStatus("COMPLETED");
            progress.setCompletedAt(LocalDateTime.now());
            progress.setCompletedSlides(totalSlides);
        } else {
            progress.setStatus("IN_PROGRESS");
        }

        progress.setLastAccessedAt(LocalDateTime.now());
        return userProgressRepository.save(progress);
    }

    @Override
    public UserProgress markLessonCompleted(Long userId, Long lessonId) {
        int total = (int) slideRepository.countByLessonId(lessonId);
        return recordLessonProgress(userId, lessonId, total, true);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLessonCompleted(Long userId, Long lessonId) {
        return userProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .map(p -> "COMPLETED".equalsIgnoreCase(p.getStatus()))
                .orElse(false);
    }
}

