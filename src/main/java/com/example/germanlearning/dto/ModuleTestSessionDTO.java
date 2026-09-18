package com.example.germanlearning.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleTestSessionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long testId;
    private String testTitle;
    private Long moduleId;
    private String moduleTitle;
    private String moduleGermanTitle;
    private String moduleCode;
    private Integer passingScore;
    private Integer timeLimitMinutes;
    private List<Long> questionIds = new ArrayList<>();
    private int currentIndex = 0;
    private Map<Long, String> userAnswers = new HashMap<>();
    private List<QuizReviewItemDTO> feedbackHistory = new ArrayList<>();
    private ModuleTestFeedbackDTO lastFeedback;
    private LocalDateTime startTime;
    private boolean completed = false;

    public ModuleTestSessionDTO() {
        this.startTime = LocalDateTime.now();
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getModuleGermanTitle() {
        return moduleGermanTitle;
    }

    public void setModuleGermanTitle(String moduleGermanTitle) {
        this.moduleGermanTitle = moduleGermanTitle;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Integer passingScore) {
        this.passingScore = passingScore;
    }

    public Integer getTimeLimitMinutes() {
        return timeLimitMinutes;
    }

    public void setTimeLimitMinutes(Integer timeLimitMinutes) {
        this.timeLimitMinutes = timeLimitMinutes;
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Map<Long, String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Map<Long, String> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public List<QuizReviewItemDTO> getFeedbackHistory() {
        return feedbackHistory;
    }

    public void setFeedbackHistory(List<QuizReviewItemDTO> feedbackHistory) {
        this.feedbackHistory = feedbackHistory;
    }

    public ModuleTestFeedbackDTO getLastFeedback() {
        return lastFeedback;
    }

    public void setLastFeedback(ModuleTestFeedbackDTO lastFeedback) {
        this.lastFeedback = lastFeedback;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getTotalQuestions() {
        return questionIds != null ? questionIds.size() : 0;
    }

    public Long getCurrentQuestionId() {
        if (questionIds != null && currentIndex >= 0 && currentIndex < questionIds.size()) {
            return questionIds.get(currentIndex);
        }
        return null;
    }
}

