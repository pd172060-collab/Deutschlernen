package com.example.germanlearning.dto;

import java.io.Serializable;

public class QuizDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String description;
    private Integer passingScore;
    private Integer timeLimitMinutes;
    private Long topicId;
    private String topicTitle;
    private String germanTopicTitle;
    private Long moduleId;
    private String moduleTitle;
    private String moduleCode;
    private int questionCount;
    private int totalAttempts;
    private boolean passedByCurrentUser;

    public QuizDTO() {
    }

    public QuizDTO(Long id, String title, String description, Integer passingScore, Integer timeLimitMinutes,
                   Long topicId, String topicTitle, String germanTopicTitle, Long moduleId, String moduleTitle,
                   String moduleCode, int questionCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.passingScore = passingScore;
        this.timeLimitMinutes = timeLimitMinutes;
        this.topicId = topicId;
        this.topicTitle = topicTitle;
        this.germanTopicTitle = germanTopicTitle;
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.moduleCode = moduleCode;
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

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
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

