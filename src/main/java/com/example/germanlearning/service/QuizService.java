package com.example.germanlearning.service;

import java.util.List;

import com.example.germanlearning.dto.QuizAttemptDTO;
import com.example.germanlearning.dto.QuizDTO;
import com.example.germanlearning.dto.QuizFeedbackDTO;
import com.example.germanlearning.dto.QuizQuestionDTO;
import com.example.germanlearning.dto.QuizSessionDTO;
import com.example.germanlearning.dto.QuizSummaryDTO;

public interface QuizService {

    /**
     * Get all topic quizzes grouped/cataloged for the user.
     */
    List<QuizDTO> getAllTopicQuizzes(Long userId);

    /**
     * Get a quiz by topic ID, creating a virtual or persisted quiz entity if needed.
     */
    QuizDTO getQuizByTopicId(Long topicId, Long userId);

    /**
     * Start a quiz session for a specific topic.
     */
    QuizSessionDTO startTopicQuiz(Long topicId, Long userId);

    /**
     * Start a quiz session by Quiz ID.
     */
    QuizSessionDTO startQuiz(Long quizId, Long userId);

    /**
     * Get the active question for the current session state.
     */
    QuizQuestionDTO getCurrentQuestion(QuizSessionDTO session);

    /**
     * Evaluate the user's answer server-side and record feedback into the session.
     */
    QuizFeedbackDTO submitAnswer(QuizSessionDTO session, Long questionId, String userAnswer);

    /**
     * Advance the session index to the next question.
     */
    QuizSessionDTO advanceToNextQuestion(QuizSessionDTO session);

    /**
     * Finalize the quiz session, persist the QuizAttempt in the database, and build the summary.
     */
    QuizSummaryDTO completeQuiz(QuizSessionDTO session, Long userId);

    /**
     * Get the quiz attempt history for the user.
     */
    List<QuizAttemptDTO> getUserQuizHistory(Long userId);
}

