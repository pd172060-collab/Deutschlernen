package com.example.germanlearning.dto;

import java.util.List;

public class DashboardSummaryDTO {
    private int totalModules;
    private int totalTopics;
    private int totalLessons;
    private int totalQuizzes;
    private int totalTests;
    private int completedLessons;
    private int passedQuizzes;
    private int passedTests;
    private int overallProgressPercent;
    private List<ModuleDTO> modules;

    public DashboardSummaryDTO() {
    }

    public int getTotalModules() {
        return totalModules;
    }

    public void setTotalModules(int totalModules) {
        this.totalModules = totalModules;
    }

    public int getTotalTopics() {
        return totalTopics;
    }

    public void setTotalTopics(int totalTopics) {
        this.totalTopics = totalTopics;
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public void setTotalLessons(int totalLessons) {
        this.totalLessons = totalLessons;
    }

    public int getTotalQuizzes() {
        return totalQuizzes;
    }

    public void setTotalQuizzes(int totalQuizzes) {
        this.totalQuizzes = totalQuizzes;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
    }

    public int getPassedQuizzes() {
        return passedQuizzes;
    }

    public void setPassedQuizzes(int passedQuizzes) {
        this.passedQuizzes = passedQuizzes;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public void setPassedTests(int passedTests) {
        this.passedTests = passedTests;
    }

    public int getOverallProgressPercent() {
        return overallProgressPercent;
    }

    public void setOverallProgressPercent(int overallProgressPercent) {
        this.overallProgressPercent = overallProgressPercent;
    }

    public List<ModuleDTO> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDTO> modules) {
        this.modules = modules;
    }
}

