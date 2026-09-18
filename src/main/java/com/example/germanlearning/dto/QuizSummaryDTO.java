package com.example.germanlearning.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizSummaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long quizId;
    private String quizTitle;
    private Long topicId;
    private String topicTitle;
    private String germanTopicTitle;
    private Long moduleId;
    private String moduleTitle;
    private int totalQuestions;
    private int correctAnswers;
    private int totalPoints;
    private int earnedPoints;
    private int scorePercentage;
    private int passingScore;
    private boolean passed;
    private String timeSpentFormatted;
    private List<QuizReviewItemDTO> reviewItems = new ArrayList<>();

    public QuizSummaryDTO() {
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

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    public int getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(int scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public int getPassingScore() {
        return passingScore;
    }

    public void setPassingScore(int passingScore) {
        this.passingScore = passingScore;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getTimeSpentFormatted() {
        return timeSpentFormatted;
    }

    public void setTimeSpentFormatted(String timeSpentFormatted) {
        this.timeSpentFormatted = timeSpentFormatted;
    }

    public List<QuizReviewItemDTO> getReviewItems() {
        return reviewItems;
    }

    public void setReviewItems(List<QuizReviewItemDTO> reviewItems) {
        this.reviewItems = reviewItems;
    }
}

