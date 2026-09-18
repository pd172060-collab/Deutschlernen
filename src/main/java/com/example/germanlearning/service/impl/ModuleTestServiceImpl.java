package com.example.germanlearning.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.germanlearning.dto.ModuleTestAttemptDTO;
import com.example.germanlearning.dto.ModuleTestDTO;
import com.example.germanlearning.dto.ModuleTestFeedbackDTO;
import com.example.germanlearning.dto.ModuleTestQuestionDTO;
import com.example.germanlearning.dto.ModuleTestSessionDTO;
import com.example.germanlearning.dto.ModuleTestSummaryDTO;
import com.example.germanlearning.dto.QuizReviewItemDTO;
import com.example.germanlearning.entity.Module;
import com.example.germanlearning.entity.Question;
import com.example.germanlearning.entity.Test;
import com.example.germanlearning.entity.TestAttempt;
import com.example.germanlearning.entity.User;
import com.example.germanlearning.exception.ResourceNotFoundException;
import com.example.germanlearning.repository.ModuleRepository;
import com.example.germanlearning.repository.QuestionRepository;
import com.example.germanlearning.repository.TestAttemptRepository;
import com.example.germanlearning.repository.TestRepository;
import com.example.germanlearning.repository.UserRepository;
import com.example.germanlearning.service.ModuleTestService;

@Service
@Transactional
public class ModuleTestServiceImpl implements ModuleTestService {

    private static final int DEFAULT_MAX_TEST_QUESTIONS = 20;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final TestRepository testRepository;
    private final TestAttemptRepository testAttemptRepository;
    private final QuestionRepository questionRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;

    public ModuleTestServiceImpl(
            TestRepository testRepository,
            TestAttemptRepository testAttemptRepository,
            QuestionRepository questionRepository,
            ModuleRepository moduleRepository,
            UserRepository userRepository) {
        this.testRepository = testRepository;
        this.testAttemptRepository = testAttemptRepository;
        this.questionRepository = questionRepository;
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleTestDTO> getAllModuleTests(Long userId) {
        List<Module> modules = moduleRepository.findAllByOrderByOrderIndexAsc();
        List<ModuleTestDTO> result = new ArrayList<>();

        for (Module module : modules) {
            List<Test> tests = testRepository.findByModuleId(module.getId());
            if (!tests.isEmpty()) {
                for (Test test : tests) {
                    List<Question> moduleQuestions = questionRepository.findByModuleId(module.getId());
                    if (moduleQuestions.isEmpty()) {
                        moduleQuestions = questionRepository.findByTestId(test.getId());
                    }

                    ModuleTestDTO dto = new ModuleTestDTO(
                            test.getId(),
                            test.getTitle(),
                            test.getDescription(),
                            test.getPassingScore() != null ? test.getPassingScore() : 75,
                            test.getTimeLimitMinutes() != null ? test.getTimeLimitMinutes() : 30,
                            module.getId(),
                            module.getTitle(),
                            module.getGermanTitle(),
                            module.getCode(),
                            module.getLevel(),
                            moduleQuestions.size()
                    );

                    if (userId != null) {
                        List<TestAttempt> attempts = testAttemptRepository.findByUserIdAndTestId(userId, test.getId());
                        dto.setTotalAttempts(attempts.size());
                        boolean passed = attempts.stream().anyMatch(a -> Boolean.TRUE.equals(a.getPassed()));
                        dto.setPassedByCurrentUser(passed);
                    }

                    result.add(dto);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleTestDTO getModuleTestByModuleId(Long moduleId, Long userId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with ID: " + moduleId));

        List<Test> tests = testRepository.findByModuleId(moduleId);
        if (tests.isEmpty()) {
            throw new ResourceNotFoundException("No Module Test found for Module ID: " + moduleId);
        }

        Test test = tests.get(0);
        List<Question> moduleQuestions = questionRepository.findByModuleId(moduleId);
        if (moduleQuestions.isEmpty()) {
            moduleQuestions = questionRepository.findByTestId(test.getId());
        }

        ModuleTestDTO dto = new ModuleTestDTO(
                test.getId(),
                test.getTitle(),
                test.getDescription(),
                test.getPassingScore() != null ? test.getPassingScore() : 75,
                test.getTimeLimitMinutes() != null ? test.getTimeLimitMinutes() : 30,
                module.getId(),
                module.getTitle(),
                module.getGermanTitle(),
                module.getCode(),
                module.getLevel(),
                moduleQuestions.size()
        );

        if (userId != null) {
            List<TestAttempt> attempts = testAttemptRepository.findByUserIdAndTestId(userId, test.getId());
            dto.setTotalAttempts(attempts.size());
            boolean passed = attempts.stream().anyMatch(a -> Boolean.TRUE.equals(a.getPassed()));
            dto.setPassedByCurrentUser(passed);
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleTestDTO getModuleTestById(Long testId, Long userId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Module Test not found with ID: " + testId));

        Module module = test.getModule();
        List<Question> moduleQuestions = module != null ? questionRepository.findByModuleId(module.getId()) : Collections.emptyList();
        if (moduleQuestions.isEmpty()) {
            moduleQuestions = questionRepository.findByTestId(test.getId());
        }

        ModuleTestDTO dto = new ModuleTestDTO(
                test.getId(),
                test.getTitle(),
                test.getDescription(),
                test.getPassingScore() != null ? test.getPassingScore() : 75,
                test.getTimeLimitMinutes() != null ? test.getTimeLimitMinutes() : 30,
                module != null ? module.getId() : null,
                module != null ? module.getTitle() : "",
                module != null ? module.getGermanTitle() : "",
                module != null ? module.getCode() : "",
                module != null ? module.getLevel() : "",
                moduleQuestions.size()
        );

        if (userId != null) {
            List<TestAttempt> attempts = testAttemptRepository.findByUserIdAndTestId(userId, test.getId());
            dto.setTotalAttempts(attempts.size());
            boolean passed = attempts.stream().anyMatch(a -> Boolean.TRUE.equals(a.getPassed()));
            dto.setPassedByCurrentUser(passed);
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleTestSessionDTO startModuleTest(Long testId, Long userId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Module Test not found with ID: " + testId));

        Module module = test.getModule();
        if (module == null) {
            throw new ResourceNotFoundException("Module Test is not linked to any Module.");
        }

        return buildSessionForModuleTest(test, module);
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleTestSessionDTO startModuleTestByModule(Long moduleId, Long userId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with ID: " + moduleId));

        List<Test> tests = testRepository.findByModuleId(moduleId);
        if (tests.isEmpty()) {
            throw new ResourceNotFoundException("No test configured for Module: " + module.getTitle());
        }

        return buildSessionForModuleTest(tests.get(0), module);
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleTestQuestionDTO getCurrentQuestion(ModuleTestSessionDTO session) {
        if (session == null || session.getQuestionIds().isEmpty()) {
            return null;
        }
        if (session.getCurrentIndex() >= session.getQuestionIds().size()) {
            return null;
        }

        Long questionId = session.getQuestionIds().get(session.getCurrentIndex());
        Question q = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

        String topicTitle = q.getTopic() != null ? q.getTopic().getTitle() : session.getModuleTitle();

        ModuleTestQuestionDTO dto = new ModuleTestQuestionDTO(
                q.getId(),
                q.getQuestionText(),
                q.getQuestionType(),
                q.getOptionA(),
                q.getOptionB(),
                q.getOptionC(),
                q.getOptionD(),
                q.getPoints(),
                q.getDifficulty(),
                session.getCurrentIndex() + 1,
                session.getQuestionIds().size(),
                topicTitle
        );

        if (session.getUserAnswers() != null) {
            dto.setUserAnswer(session.getUserAnswers().get(questionId));
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleTestFeedbackDTO submitAnswer(ModuleTestSessionDTO session, Long questionId, String userAnswer) {
        if (session == null) {
            throw new IllegalStateException("No active module test session found.");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

        boolean correct = isAnswerCorrect(question, userAnswer);
        int pointsPossible = question.getPoints() != null ? question.getPoints() : 1;
        int pointsEarned = correct ? pointsPossible : 0;
        int qIndex = session.getCurrentIndex() + 1;
        int totalQ = session.getTotalQuestions();
        boolean isLast = (qIndex >= totalQ);
        String topicTitle = question.getTopic() != null ? question.getTopic().getTitle() : session.getModuleTitle();

        String displayCorrectAnswer = getDisplayCorrectAnswer(question);

        ModuleTestFeedbackDTO feedback = new ModuleTestFeedbackDTO(
                question.getId(),
                question.getQuestionText(),
                question.getQuestionType(),
                userAnswer != null ? userAnswer.trim() : "",
                displayCorrectAnswer,
                question.getExplanation(),
                correct,
                pointsEarned,
                pointsPossible,
                qIndex,
                totalQ,
                topicTitle,
                isLast
        );

        // Record in session
        session.getUserAnswers().put(questionId, userAnswer);
        session.setLastFeedback(feedback);

        // Add review item
        QuizReviewItemDTO reviewItem = new QuizReviewItemDTO(
                question.getId(),
                qIndex,
                question.getQuestionText(),
                question.getQuestionType(),
                userAnswer != null ? userAnswer.trim() : "",
                displayCorrectAnswer,
                question.getExplanation(),
                correct,
                pointsPossible,
                pointsEarned
        );
        session.getFeedbackHistory().add(reviewItem);

        return feedback;
    }

    @Override
    public ModuleTestSessionDTO advanceToNextQuestion(ModuleTestSessionDTO session) {
        if (session != null) {
            session.setCurrentIndex(session.getCurrentIndex() + 1);
            session.setLastFeedback(null);
            if (session.getCurrentIndex() >= session.getQuestionIds().size()) {
                session.setCompleted(true);
            }
        }
        return session;
    }

    @Override
    public ModuleTestSummaryDTO completeTest(ModuleTestSessionDTO session, Long userId) {
        if (session == null) {
            throw new IllegalStateException("No active module test session to complete.");
        }

        int totalQuestions = session.getFeedbackHistory().size();
        int correctAnswers = (int) session.getFeedbackHistory().stream().filter(QuizReviewItemDTO::isCorrect).count();
        int incorrectAnswers = totalQuestions - correctAnswers;
        int totalPoints = session.getFeedbackHistory().stream().mapToInt(QuizReviewItemDTO::getPoints).sum();
        int earnedPoints = session.getFeedbackHistory().stream().mapToInt(QuizReviewItemDTO::getEarnedPoints).sum();

        if (totalPoints == 0 && totalQuestions > 0) {
            totalPoints = totalQuestions;
            earnedPoints = correctAnswers;
        }

        int scorePercentage = totalPoints > 0 ? (int) Math.round(((double) earnedPoints / totalPoints) * 100.0) : 0;
        int passingScore = session.getPassingScore() != null ? session.getPassingScore() : 75;
        boolean passed = (scorePercentage >= passingScore);

        // Calculate time spent
        String timeSpentFormatted = "00:00";
        if (session.getStartTime() != null) {
            Duration duration = Duration.between(session.getStartTime(), LocalDateTime.now());
            long mins = duration.toMinutes();
            long secs = duration.minusMinutes(mins).getSeconds();
            timeSpentFormatted = String.format("%02d:%02d", mins, secs);
        }

        // Persist attempt to MySQL
        User user = findUser(userId);
        Test test = testRepository.findById(session.getTestId()).orElse(null);

        if (test != null && user != null) {
            TestAttempt attempt = new TestAttempt(user, test, earnedPoints, totalPoints, passed);
            testAttemptRepository.save(attempt);
        }

        session.setCompleted(true);

        ModuleTestSummaryDTO summary = new ModuleTestSummaryDTO();
        summary.setTestId(session.getTestId());
        summary.setTestTitle(session.getTestTitle());
        summary.setModuleId(session.getModuleId());
        summary.setModuleTitle(session.getModuleTitle());
        summary.setModuleGermanTitle(session.getModuleGermanTitle());
        summary.setModuleCode(session.getModuleCode());
        summary.setTotalQuestions(totalQuestions);
        summary.setCorrectAnswers(correctAnswers);
        summary.setIncorrectAnswers(incorrectAnswers);
        summary.setTotalPoints(totalPoints);
        summary.setEarnedPoints(earnedPoints);
        summary.setScorePercentage(scorePercentage);
        summary.setPassingScore(passingScore);
        summary.setPassed(passed);
        summary.setTimeSpentFormatted(timeSpentFormatted);
        summary.setReviewItems(session.getFeedbackHistory());

        return summary;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModuleTestAttemptDTO> getUserTestHistory(Long userId) {
        User user = findUser(userId);
        if (user == null) {
            return Collections.emptyList();
        }

        List<TestAttempt> attempts = testAttemptRepository.findByUserIdOrderByCompletedAtDesc(user.getId());

        return attempts.stream().map(a -> {
            Test t = a.getTest();
            String testTitle = t != null ? t.getTitle() : "Module Test";
            Module m = t != null ? t.getModule() : null;
            String moduleTitle = m != null ? m.getTitle() : "";
            String moduleCode = m != null ? m.getCode() : "";
            Long moduleId = m != null ? m.getId() : null;

            int score = a.getScore() != null ? a.getScore() : 0;
            int maxScore = a.getMaxScore() != null ? a.getMaxScore() : 1;
            int percentage = maxScore > 0 ? (int) Math.round(((double) score / maxScore) * 100.0) : 0;
            String formattedDate = a.getCompletedAt() != null ? a.getCompletedAt().format(DATE_FORMATTER) : "";

            return new ModuleTestAttemptDTO(
                    a.getId(),
                    t != null ? t.getId() : null,
                    testTitle,
                    moduleId,
                    moduleTitle,
                    moduleCode,
                    score,
                    maxScore,
                    percentage,
                    a.getPassed(),
                    a.getCompletedAt(),
                    formattedDate
            );
        }).collect(Collectors.toList());
    }

    /**
     * Question selection & distribution algorithm across module topics.
     */
    private ModuleTestSessionDTO buildSessionForModuleTest(Test test, Module module) {
        List<Question> allModuleQuestions = questionRepository.findByModuleId(module.getId());
        if (allModuleQuestions.isEmpty()) {
            allModuleQuestions = questionRepository.findByTestId(test.getId());
        }

        if (allModuleQuestions.isEmpty()) {
            throw new ResourceNotFoundException("No questions available for Module: " + module.getTitle());
        }

        // Group questions by Topic to ensure even multi-topic distribution
        Map<Long, List<Question>> topicBuckets = new LinkedHashMap<>();
        for (Question q : allModuleQuestions) {
            Long topicKey = q.getTopic() != null ? q.getTopic().getId() : 0L;
            topicBuckets.computeIfAbsent(topicKey, k -> new ArrayList<>()).add(q);
        }

        // Shuffle within each topic bucket
        for (List<Question> bucket : topicBuckets.values()) {
            Collections.shuffle(bucket);
        }

        List<Question> selectedQuestions = new ArrayList<>();
        int targetCount = Math.min(DEFAULT_MAX_TEST_QUESTIONS, allModuleQuestions.size());

        // Round-robin selection across topics
        boolean questionAdded;
        do {
            questionAdded = false;
            for (List<Question> bucket : topicBuckets.values()) {
                if (!bucket.isEmpty() && selectedQuestions.size() < targetCount) {
                    selectedQuestions.add(bucket.remove(0));
                    questionAdded = true;
                }
            }
        } while (questionAdded && selectedQuestions.size() < targetCount);

        // Final shuffle of selected test questions
        Collections.shuffle(selectedQuestions);

        ModuleTestSessionDTO session = new ModuleTestSessionDTO();
        session.setTestId(test.getId());
        session.setTestTitle(test.getTitle());
        session.setModuleId(module.getId());
        session.setModuleTitle(module.getTitle());
        session.setModuleGermanTitle(module.getGermanTitle());
        session.setModuleCode(module.getCode());
        session.setPassingScore(test.getPassingScore() != null ? test.getPassingScore() : 75);
        session.setTimeLimitMinutes(test.getTimeLimitMinutes() != null ? test.getTimeLimitMinutes() : 30);
        session.setQuestionIds(selectedQuestions.stream().map(Question::getId).collect(Collectors.toList()));
        session.setCurrentIndex(0);
        session.setStartTime(LocalDateTime.now());
        session.setCompleted(false);

        return session;
    }

    private User findUser(Long userId) {
        if (userId != null) {
            Optional<User> u = userRepository.findById(userId);
            if (u.isPresent()) {
                return u.get();
            }
        }
        return userRepository.findAll().stream().findFirst().orElse(null);
    }

    private boolean isAnswerCorrect(Question q, String userAnswer) {
        if (userAnswer == null || userAnswer.trim().isEmpty()) {
            return false;
        }

        String cleanUser = normalizeString(userAnswer);
        String correct = q.getCorrectAnswer();
        if (correct == null) {
            return false;
        }

        String qType = q.getQuestionType() != null ? q.getQuestionType().toUpperCase() : "MULTIPLE_CHOICE";

        if ("MULTIPLE_CHOICE".equals(qType) || "TRUE_FALSE".equals(qType)) {
            if (cleanUser.equalsIgnoreCase(normalizeString(correct))) {
                return true;
            }

            String userOptionText = getOptionText(q, cleanUser);
            String correctOptionText = getOptionText(q, correct);

            if (userOptionText != null && userOptionText.equalsIgnoreCase(correctOptionText)) {
                return true;
            }

            if (normalizeString(correctOptionText).equalsIgnoreCase(cleanUser)) {
                return true;
            }

            if (normalizeString(correct).equalsIgnoreCase(normalizeString(userOptionText))) {
                return true;
            }
        }

        // Alternative options split by / or | or ;
        String[] alternatives = correct.split("[/|;]");
        for (String alt : alternatives) {
            if (normalizeString(alt).equalsIgnoreCase(cleanUser)) {
                return true;
            }
        }

        return false;
    }

    private String getOptionText(Question q, String keyOrText) {
        if (keyOrText == null) return null;
        String k = keyOrText.trim().toUpperCase();
        if ("A".equals(k)) return q.getOptionA();
        if ("B".equals(k)) return q.getOptionB();
        if ("C".equals(k)) return q.getOptionC();
        if ("D".equals(k)) return q.getOptionD();
        return keyOrText;
    }

    private String getDisplayCorrectAnswer(Question q) {
        String correct = q.getCorrectAnswer();
        if (correct == null) return "";

        String qType = q.getQuestionType() != null ? q.getQuestionType().toUpperCase() : "";
        if ("MULTIPLE_CHOICE".equals(qType) || "TRUE_FALSE".equals(qType)) {
            String k = correct.trim().toUpperCase();
            if ("A".equals(k) && q.getOptionA() != null) return "A: " + q.getOptionA();
            if ("B".equals(k) && q.getOptionB() != null) return "B: " + q.getOptionB();
            if ("C".equals(k) && q.getOptionC() != null) return "C: " + q.getOptionC();
            if ("D".equals(k) && q.getOptionD() != null) return "D: " + q.getOptionD();
        }
        return correct;
    }

    private String normalizeString(String input) {
        if (input == null) return "";
        return input.replaceAll("[.,!?\"'‘’`()„“”]", "")
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();
    }
}

