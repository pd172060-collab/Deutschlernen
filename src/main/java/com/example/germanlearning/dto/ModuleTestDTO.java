package com.example.germanlearning.dto;

import java.io.Serializable;

public class ModuleTestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String description;
    private Integer passingScore;
    private Integer timeLimitMinutes;
    private Long moduleId;
    private String moduleTitle;
    private String moduleGermanTitle;
    private String moduleCode;
    private String moduleLevel;
    private int questionCount;
    private int totalAttempts;
    private boolean passedByCurrentUser;

    public ModuleTestDTO() {
    }

    public ModuleTestDTO(Long id, String title, String description, Integer passingScore, Integer timeLimitMinutes,
                         Long moduleId, String moduleTitle, String moduleGermanTitle, String moduleCode,
                         String moduleLevel, int questionCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.passingScore = passingScore;
        this.timeLimitMinutes = timeLimitMinutes;
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.moduleGermanTitle = moduleGermanTitle;
        this.moduleCode = moduleCode;
        this.moduleLevel = moduleLevel;
        this.questionCount = questionCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getModuleLevel() {
        return moduleLevel;
    }

    public void setModuleLevel(String moduleLevel) {
        this.moduleLevel = moduleLevel;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public boolean isPassedByCurrentUser() {
        return passedByCurrentUser;
    }

    public void setPassedByCurrentUser(boolean passedByCurrentUser) {
        this.passedByCurrentUser = passedByCurrentUser;
    }
}

