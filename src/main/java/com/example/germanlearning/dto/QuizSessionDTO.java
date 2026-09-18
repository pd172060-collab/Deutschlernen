package com.example.germanlearning.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizSessionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long quizId;
    private String quizTitle;
    private Long topicId;
    private String topicTitle;
    private String germanTopicTitle;
    private Long moduleId;
    private String moduleTitle;
    private Integer passingScore;
    private List<Long> questionIds = new ArrayList<>();
    private int currentIndex = 0; // 0-based index
    private Map<Long, String> userAnswers = new HashMap<>();
    private List<QuizReviewItemDTO> feedbackHistory = new ArrayList<>();
    private QuizFeedbackDTO lastFeedback;
    private LocalDateTime startTime;
    private boolean completed = false;

    public QuizSessionDTO() {
        this.startTime = LocalDateTime.now();
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getGermanTopicTitle() {
        return germanTopicTitle;
    }

    public void setGermanTopicTitle(String germanTopicTitle) {
        this.germanTopicTitle = germanTopicTitle;
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

    public Integer getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(Integer passingScore) {
        this.passingScore = passingScore;
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

    public QuizFeedbackDTO getLastFeedback() {
        return lastFeedback;
    }

    public void setLastFeedback(QuizFeedbackDTO lastFeedback) {
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

