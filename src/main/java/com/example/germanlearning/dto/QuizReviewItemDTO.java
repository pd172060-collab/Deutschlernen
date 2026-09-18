package com.example.germanlearning.dto;

import java.io.Serializable;

public class QuizReviewItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long questionId;
    private int questionIndex;
    private String questionText;
    private String questionType;
    private String userAnswer;
    private String correctAnswer;
    private String explanation;
    private boolean correct;
    private int points;
    private int earnedPoints;

    public QuizReviewItemDTO() {
    }

    public QuizReviewItemDTO(Long questionId, int questionIndex, String questionText,
                             String questionType, String userAnswer, String correctAnswer,
                             String explanation, boolean correct, int points, int earnedPoints) {
        this.questionId = questionId;
        this.questionIndex = questionIndex;
        this.questionText = questionText;
        this.questionType = questionType;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.correct = correct;
        this.points = points;
        this.earnedPoints = earnedPoints;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
    }
}

