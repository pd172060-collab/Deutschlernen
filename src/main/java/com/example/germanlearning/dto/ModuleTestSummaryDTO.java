package com.example.germanlearning.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModuleTestSummaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long testId;
    private String testTitle;
    private Long moduleId;
    private String moduleTitle;
    private String moduleGermanTitle;
    private String moduleCode;
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private int totalPoints;
    private int earnedPoints;
    private int scorePercentage;
    private int passingScore;
    private boolean passed;
    private String timeSpentFormatted;
    private List<QuizReviewItemDTO> reviewItems = new ArrayList<>();

    public ModuleTestSummaryDTO() {
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

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
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

