package com.example.germanlearning.service.impl;

import com.example.germanlearning.dto.DashboardSummaryDTO;
import com.example.germanlearning.dto.ModuleDTO;
import com.example.germanlearning.repository.*;
import com.example.germanlearning.service.DashboardService;
import com.example.germanlearning.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final ModuleService moduleService;
    private final TopicRepository topicRepository;
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final TestRepository testRepository;
    private final UserProgressRepository userProgressRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final TestAttemptRepository testAttemptRepository;

    public DashboardServiceImpl(
            ModuleService moduleService,
            TopicRepository topicRepository,
            LessonRepository lessonRepository,
            QuizRepository quizRepository,
            TestRepository testRepository,
            UserProgressRepository userProgressRepository,
            QuizAttemptRepository quizAttemptRepository,
            TestAttemptRepository testAttemptRepository) {
        this.moduleService = moduleService;
        this.topicRepository = topicRepository;
        this.lessonRepository = lessonRepository;
        this.quizRepository = quizRepository;
        this.testRepository = testRepository;
        this.userProgressRepository = userProgressRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.testAttemptRepository = testAttemptRepository;
    }

    @Override
    public DashboardSummaryDTO getDashboardSummary() {
        DashboardSummaryDTO summary = new DashboardSummaryDTO();
        List<ModuleDTO> modules = moduleService.getAllModules();
        summary.setModules(modules);

        int totalModules = modules.size();
        int totalTopics = (int) topicRepository.count();
        int totalLessons = (int) lessonRepository.count();
        int totalQuizzes = (int) quizRepository.count();
        int totalTests = (int) testRepository.count();

        summary.setTotalModules(totalModules);
        summary.setTotalTopics(totalTopics);
        summary.setTotalLessons(totalLessons);
        summary.setTotalQuizzes(totalQuizzes);
        summary.setTotalTests(totalTests);

        // Calculate progress placeholders (0% initially or actual if completed)
        long completedLessons = userProgressRepository.countByUserIdAndStatus(1L, "COMPLETED");
        long passedQuizzes = quizAttemptRepository.countByUserIdAndPassedTrue(1L);
        long passedTests = testAttemptRepository.countByUserIdAndPassedTrue(1L);

        summary.setCompletedLessons((int) completedLessons);
        summary.setPassedQuizzes((int) passedQuizzes);
        summary.setPassedTests((int) passedTests);

        int progressPercent = 0;
        if (totalLessons > 0) {
            progressPercent = (int) Math.round(((double) completedLessons / totalLessons) * 100);
        }
        summary.setOverallProgressPercent(progressPercent);

        return summary;
    }
}

