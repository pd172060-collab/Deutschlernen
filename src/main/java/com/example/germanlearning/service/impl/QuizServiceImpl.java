package com.example.germanlearning.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.germanlearning.dto.QuizAttemptDTO;
import com.example.germanlearning.dto.QuizDTO;
import com.example.germanlearning.dto.QuizFeedbackDTO;
import com.example.germanlearning.dto.QuizQuestionDTO;
import com.example.germanlearning.dto.QuizReviewItemDTO;
import com.example.germanlearning.dto.QuizSessionDTO;
import com.example.germanlearning.dto.QuizSummaryDTO;
import com.example.germanlearning.entity.Module;
import com.example.germanlearning.entity.Question;
import com.example.germanlearning.entity.Quiz;
import com.example.germanlearning.entity.QuizAttempt;
import com.example.germanlearning.entity.Topic;
import com.example.germanlearning.entity.User;
import com.example.germanlearning.exception.ResourceNotFoundException;
import com.example.germanlearning.repository.ModuleRepository;
import com.example.germanlearning.repository.QuestionRepository;
import com.example.germanlearning.repository.QuizAttemptRepository;
import com.example.germanlearning.repository.QuizRepository;
import com.example.germanlearning.repository.TopicRepository;
import com.example.germanlearning.repository.UserRepository;
import com.example.germanlearning.service.QuizService;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public QuizServiceImpl(
            QuizRepository quizRepository,
            QuizAttemptRepository quizAttemptRepository,
            QuestionRepository questionRepository,
            TopicRepository topicRepository,
            ModuleRepository moduleRepository,
            UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizDTO> getAllTopicQuizzes(Long userId) {
        List<Module> modules = moduleRepository.findAllByOrderByOrderIndexAsc();
        List<QuizDTO> result = new ArrayList<>();

        for (Module module : modules) {
            List<Topic> topics = topicRepository.findByModuleIdOrderByOrderIndexAsc(module.getId());
            for (Topic topic : topics) {
                List<Quiz> quizzes = quizRepository.findByTopicId(topic.getId());
                if (!quizzes.isEmpty()) {
                    for (Quiz quiz : quizzes) {
                        List<Question> questions = questionRepository.findByTopicId(topic.getId());

                        QuizDTO dto = new QuizDTO(
                                quiz.getId(),
                                quiz.getTitle(),
                                quiz.getDescription(),
                                quiz.getPassingScore() != null ? quiz.getPassingScore() : 70,
                                quiz.getTimeLimitMinutes() != null ? quiz.getTimeLimitMinutes() : 15,
                                topic.getId(),
                                topic.getTitle(),
                                topic.getGermanTitle(),
                                module.getId(),
                                module.getTitle(),
                                module.getCode(),
                                questions.size()
                        );

                        if (userId != null) {
                            List<QuizAttempt> attempts = quizAttemptRepository.findByUserIdAndQuizId(userId, quiz.getId());
                            dto.setTotalAttempts(attempts.size());
                            boolean passed = attempts.stream().anyMatch(a -> Boolean.TRUE.equals(a.getPassed()));
                            dto.setPassedByCurrentUser(passed);
                        }

                        result.add(dto);
                    }
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public QuizDTO getQuizByTopicId(Long topicId, Long userId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with ID: " + topicId));

        List<Quiz> quizzes = quizRepository.findByTopicId(topicId);
        if (quizzes.isEmpty()) {
            throw new ResourceNotFoundException("No quiz configured for topic ID: " + topicId);
        }

        Quiz quiz = quizzes.get(0);
        Module module = topic.getModule();
        List<Question> questions = questionRepository.findByTopicId(topicId);

        QuizDTO dto = new QuizDTO(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getDescription(),
                quiz.getPassingScore() != null ? quiz.getPassingScore() : 70,
                quiz.getTimeLimitMinutes() != null ? quiz.getTimeLimitMinutes() : 15,
                topic.getId(),
                topic.getTitle(),
                topic.getGermanTitle(),
                module != null ? module.getId() : null,
                module != null ? module.getTitle() : "",
                module != null ? module.getCode() : "",
                questions.size()
        );

        if (userId != null) {
            List<QuizAttempt> attempts = quizAttemptRepository.findByUserIdAndQuizId(userId, quiz.getId());
            dto.setTotalAttempts(attempts.size());
            boolean passed = attempts.stream().anyMatch(a -> Boolean.TRUE.equals(a.getPassed()));
            dto.setPassedByCurrentUser(passed);
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public QuizSessionDTO startTopicQuiz(Long topicId, Long userId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with ID: " + topicId));

        List<Quiz> existingQuizzes = quizRepository.findByTopicId(topicId);
        Quiz quiz = existingQuizzes.isEmpty() ? null : existingQuizzes.get(0);

        // Strictly fetch questions belonging ONLY to this specific topic
        List<Question> questions = questionRepository.findByTopicId(topicId);

        if (questions.isEmpty()) {
            throw new ResourceNotFoundException("No questions found for topic: " + topic.getTitle());
        }

        Module module = topic.getModule();

        // Randomize questions for the attempt
        List<Question> shuffled = new ArrayList<>(questions);
        Collections.shuffle(shuffled);

        // Select up to 10 questions per quiz attempt
        int targetCount = Math.min(10, shuffled.size());
        List<Question> selectedQuestions = shuffled.subList(0, targetCount);

        QuizSessionDTO session = new QuizSessionDTO();
        session.setQuizId(quiz != null ? quiz.getId() : null);
        session.setQuizTitle(quiz != null ? quiz.getTitle() : "Quiz: " + topic.getTitle());
        session.setTopicId(topic.getId());
        session.setTopicTitle(topic.getTitle());
        session.setGermanTopicTitle(topic.getGermanTitle());
        session.setModuleId(module != null ? module.getId() : null);
        session.setModuleTitle(module != null ? module.getTitle() : "");
        session.setPassingScore(quiz != null && quiz.getPassingScore() != null ? quiz.getPassingScore() : 70);
        session.setQuestionIds(selectedQuestions.stream().map(Question::getId).collect(Collectors.toList()));
        session.setCurrentIndex(0);
        session.setStartTime(LocalDateTime.now());
        session.setCompleted(false);

        return session;
    }

    @Override
    @Transactional(readOnly = true)
    public QuizSessionDTO startQuiz(Long quizId, Long userId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with ID: " + quizId));
        if (quiz.getTopic() != null) {
            return startTopicQuiz(quiz.getTopic().getId(), userId);
        }
        throw new ResourceNotFoundException("Quiz has no associated Topic.");
    }

    @Override
    @Transactional(readOnly = true)
    public QuizQuestionDTO getCurrentQuestion(QuizSessionDTO session) {
        if (session == null || session.getQuestionIds().isEmpty()) {
            return null;
        }
        if (session.getCurrentIndex() >= session.getQuestionIds().size()) {
            return null;
        }

        Long questionId = session.getQuestionIds().get(session.getCurrentIndex());
        Question q = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

        QuizQuestionDTO dto = new QuizQuestionDTO(
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
                session.getQuestionIds().size()
        );

        if (session.getUserAnswers() != null) {
            dto.setUserAnswer(session.getUserAnswers().get(questionId));
        }

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public QuizFeedbackDTO submitAnswer(QuizSessionDTO session, Long questionId, String userAnswer) {
        if (session == null) {
            throw new IllegalStateException("No active quiz session found.");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + questionId));

        boolean correct = isAnswerCorrect(question, userAnswer);
        int pointsPossible = question.getPoints() != null ? question.getPoints() : 1;
        int pointsEarned = correct ? pointsPossible : 0;
        int qIndex = session.getCurrentIndex() + 1;
        int totalQ = session.getTotalQuestions();
        boolean isLast = (qIndex >= totalQ);

        String displayCorrectAnswer = getDisplayCorrectAnswer(question);

        QuizFeedbackDTO feedback = new QuizFeedbackDTO(
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
                isLast
        );

        // Record in session
        session.getUserAnswers().put(questionId, userAnswer);
        session.setLastFeedback(feedback);

        // Add or update review item
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
    public QuizSessionDTO advanceToNextQuestion(QuizSessionDTO session) {
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
    public QuizSummaryDTO completeQuiz(QuizSessionDTO session, Long userId) {
        if (session == null) {
            throw new IllegalStateException("No active quiz session to complete.");
        }

        int totalQuestions = session.getFeedbackHistory().size();
        int correctAnswers = (int) session.getFeedbackHistory().stream().filter(QuizReviewItemDTO::isCorrect).count();
        int totalPoints = session.getFeedbackHistory().stream().mapToInt(QuizReviewItemDTO::getPoints).sum();
        int earnedPoints = session.getFeedbackHistory().stream().mapToInt(QuizReviewItemDTO::getEarnedPoints).sum();

        if (totalPoints == 0 && totalQuestions > 0) {
            totalPoints = totalQuestions;
            earnedPoints = correctAnswers;
        }

        int scorePercentage = totalPoints > 0 ? (int) Math.round(((double) earnedPoints / totalPoints) * 100.0) : 0;
        int passingScore = session.getPassingScore() != null ? session.getPassingScore() : 70;
        boolean passed = (scorePercentage >= passingScore);

        // Calculate time spent
        String timeSpentFormatted = "00:00";
        if (session.getStartTime() != null) {
            Duration duration = Duration.between(session.getStartTime(), LocalDateTime.now());
            long mins = duration.toMinutes();
            long secs = duration.minusMinutes(mins).getSeconds();
            timeSpentFormatted = String.format("%02d:%02d", mins, secs);
        }

        // Persist attempt to MySQL if Quiz and User exist
        User user = findUser(userId);
        Quiz quiz = null;
        if (session.getQuizId() != null) {
            quiz = quizRepository.findById(session.getQuizId()).orElse(null);
        } else if (session.getTopicId() != null) {
            List<Quiz> quizzes = quizRepository.findByTopicId(session.getTopicId());
            if (!quizzes.isEmpty()) {
                quiz = quizzes.get(0);
            }
        }

        if (quiz != null && user != null) {
            QuizAttempt attempt = new QuizAttempt(user, quiz, earnedPoints, totalPoints, passed);
            quizAttemptRepository.save(attempt);
        }

        session.setCompleted(true);

        QuizSummaryDTO summary = new QuizSummaryDTO();
        summary.setQuizId(quiz != null ? quiz.getId() : session.getQuizId());
        summary.setQuizTitle(session.getQuizTitle());
        summary.setTopicId(session.getTopicId());
        summary.setTopicTitle(session.getTopicTitle());
        summary.setGermanTopicTitle(session.getGermanTopicTitle());
        summary.setModuleId(session.getModuleId());
        summary.setModuleTitle(session.getModuleTitle());
        summary.setTotalQuestions(totalQuestions);
        summary.setCorrectAnswers(correctAnswers);
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
    public List<QuizAttemptDTO> getUserQuizHistory(Long userId) {
        User user = findUser(userId);
        if (user == null) {
            return Collections.emptyList();
        }

        List<QuizAttempt> attempts = quizAttemptRepository.findByUserIdOrderByCompletedAtDesc(user.getId());

        return attempts.stream().map(a -> {
            Quiz q = a.getQuiz();
            String quizTitle = q != null ? q.getTitle() : "Topic Quiz";
            Topic t = q != null ? q.getTopic() : null;
            String topicTitle = t != null ? t.getTitle() : "";
            Long topicId = t != null ? t.getId() : null;

            int score = a.getScore() != null ? a.getScore() : 0;
            int maxScore = a.getMaxScore() != null ? a.getMaxScore() : 1;
            int percentage = maxScore > 0 ? (int) Math.round(((double) score / maxScore) * 100.0) : 0;

            String formattedDate = a.getCompletedAt() != null ? a.getCompletedAt().format(DATE_FORMATTER) : "";

            return new QuizAttemptDTO(
                    a.getId(),
                    q != null ? q.getId() : null,
                    quizTitle,
                    topicId,
                    topicTitle,
                    score,
                    maxScore,
                    percentage,
                    a.getPassed(),
                    a.getCompletedAt(),
                    formattedDate
            );
        }).collect(Collectors.toList());
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
