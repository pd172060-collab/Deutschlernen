package com.example.germanlearning.service;

import java.util.List;

import com.example.germanlearning.dto.ModuleTestAttemptDTO;
import com.example.germanlearning.dto.ModuleTestDTO;
import com.example.germanlearning.dto.ModuleTestFeedbackDTO;
import com.example.germanlearning.dto.ModuleTestQuestionDTO;
import com.example.germanlearning.dto.ModuleTestSessionDTO;
import com.example.germanlearning.dto.ModuleTestSummaryDTO;

public interface ModuleTestService {

    /**
     * Get all Module Tests catalog (Modules I-IV).
     */
    List<ModuleTestDTO> getAllModuleTests(Long userId);

    /**
     * Get Module Test by Module ID.
     */
    ModuleTestDTO getModuleTestByModuleId(Long moduleId, Long userId);

    /**
     * Get Module Test by Test ID.
     */
    ModuleTestDTO getModuleTestById(Long testId, Long userId);

    /**
     * Start a Module Test session for the specified Test ID.
     */
    ModuleTestSessionDTO startModuleTest(Long testId, Long userId);

    /**
     * Start a Module Test session for the specified Module ID.
     */
    ModuleTestSessionDTO startModuleTestByModule(Long moduleId, Long userId);

    /**
     * Get the active question for the current test session state.
     */
    ModuleTestQuestionDTO getCurrentQuestion(ModuleTestSessionDTO session);

    /**
     * Evaluate submitted answer server-side, record feedback into session, and return result.
     */
    ModuleTestFeedbackDTO submitAnswer(ModuleTestSessionDTO session, Long questionId, String userAnswer);

    /**
     * Advance the session index to the next question.
     */
    ModuleTestSessionDTO advanceToNextQuestion(ModuleTestSessionDTO session);

    /**
     * Finalize the module test session, persist TestAttempt to MySQL, and build summary.
     */
    ModuleTestSummaryDTO completeTest(ModuleTestSessionDTO session, Long userId);

    /**
     * Get the test attempt history for the user.
     */
    List<ModuleTestAttemptDTO> getUserTestHistory(Long userId);
}

