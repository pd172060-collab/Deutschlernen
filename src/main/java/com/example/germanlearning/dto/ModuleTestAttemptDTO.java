package com.example.germanlearning.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ModuleTestAttemptDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long testId;
    private String testTitle;
    private Long moduleId;
    private String moduleTitle;
    private String moduleCode;
    private Integer score;
    private Integer maxScore;
    private int scorePercentage;
    private Boolean passed;
    private LocalDateTime completedAt;
    private String completedAtFormatted;

    public ModuleTestAttemptDTO() {
    }

    public ModuleTestAttemptDTO(Long id, Long testId, String testTitle, Long moduleId, String moduleTitle,
                                String moduleCode, Integer score, Integer maxScore, int scorePercentage,
                                Boolean passed, LocalDateTime completedAt, String completedAtFormatted) {
        this.id = id;
        this.testId = testId;
        this.testTitle = testTitle;
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.moduleCode = moduleCode;
        this.score = score;
        this.maxScore = maxScore;
        this.scorePercentage = scorePercentage;
        this.passed = passed;
        this.completedAt = completedAt;
        this.completedAtFormatted = completedAtFormatted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public int getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(int scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getCompletedAtFormatted() {
        return completedAtFormatted;
    }

    public void setCompletedAtFormatted(String completedAtFormatted) {
        this.completedAtFormatted = completedAtFormatted;
    }
}

