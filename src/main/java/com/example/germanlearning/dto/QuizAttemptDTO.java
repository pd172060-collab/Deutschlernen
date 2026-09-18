package com.example.germanlearning.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class QuizAttemptDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long quizId;
    private String quizTitle;
    private Long topicId;
    private String topicTitle;
    private Integer score;
    private Integer maxScore;
    private int scorePercentage;
    private Boolean passed;
    private LocalDateTime completedAt;
    private String completedAtFormatted;

    public QuizAttemptDTO() {
    }

    public QuizAttemptDTO(Long id, Long quizId, String quizTitle, Long topicId, String topicTitle,
                          Integer score, Integer maxScore, int scorePercentage, Boolean passed,
                          LocalDateTime completedAt, String completedAtFormatted) {
        this.id = id;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.topicId = topicId;
        this.topicTitle = topicTitle;
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

