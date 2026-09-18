package com.example.germanlearning.dto;

import java.io.Serializable;

public class ModuleTestQuestionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String questionText;
    private String questionType;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Integer points;
    private String difficulty;
    private int questionIndex; // 1-based index
    private int totalQuestions;
    private String topicTitle;
    private String userAnswer;

    public ModuleTestQuestionDTO() {
    }

    public ModuleTestQuestionDTO(Long id, String questionText, String questionType,
                                 String optionA, String optionB, String optionC, String optionD,
                                 Integer points, String difficulty, int questionIndex, int totalQuestions,
                                 String topicTitle) {
        this.id = id;
        this.questionText = questionText;
        this.questionType = questionType;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.points = points != null ? points : 1;
        this.difficulty = difficulty;
        this.questionIndex = questionIndex;
        this.totalQuestions = totalQuestions;
        this.topicTitle = topicTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}

