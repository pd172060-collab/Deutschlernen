package com.example.germanlearning.dto;

import java.util.ArrayList;
import java.util.List;

public class TopicDTO {
    private Long id;
    private Long moduleId;
    private String moduleTitle;
    private String title;
    private String germanTitle;
    private String description;
    private Integer orderIndex;
    private int lessonCount;
    private int quizCount;
    private List<LessonDTO> lessons = new ArrayList<>();

    public TopicDTO() {
    }

    public TopicDTO(Long id, Long moduleId, String title, String germanTitle, String description, Integer orderIndex) {
        this.id = id;
        this.moduleId = moduleId;
        this.title = title;
        this.germanTitle = germanTitle;
        this.description = description;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGermanTitle() {
        return germanTitle;
    }

    public void setGermanTitle(String germanTitle) {
        this.germanTitle = germanTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public int getQuizCount() {
        return quizCount;
    }

    public void setQuizCount(int quizCount) {
        this.quizCount = quizCount;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDTO> lessons) {
        this.lessons = lessons;
    }
}
