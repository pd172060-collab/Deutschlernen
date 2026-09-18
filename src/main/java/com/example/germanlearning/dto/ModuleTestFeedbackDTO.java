package com.example.germanlearning.dto;

import java.io.Serializable;

public class ModuleTestFeedbackDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long questionId;
    private String questionText;
    private String questionType;
    private String userAnswer;
    private String correctAnswer;
    private String explanation;
    private boolean correct;
    private int pointsEarned;
    private int pointsPossible;
    private int questionIndex;
    private int totalQuestions;
    private String topicTitle;
    private boolean lastQuestion;

    public ModuleTestFeedbackDTO() {
    }

    public ModuleTestFeedbackDTO(Long questionId, String questionText, String questionType,
                                 String userAnswer, String correctAnswer, String explanation,
                                 boolean correct, int pointsEarned, int pointsPossible,
                                 int questionIndex, int totalQuestions, String topicTitle,
                                 boolean lastQuestion) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.correct = correct;
        this.pointsEarned = pointsEarned;
        this.pointsPossible = pointsPossible;
        this.questionIndex = questionIndex;
        this.totalQuestions = totalQuestions;
        this.topicTitle = topicTitle;
        this.lastQuestion = lastQuestion;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public int getPointsPossible() {
        return pointsPossible;
    }

    public void setPointsPossible(int pointsPossible) {
        this.pointsPossible = pointsPossible;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public boolean isLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(boolean lastQuestion) {
        this.lastQuestion = lastQuestion;
    }
}

