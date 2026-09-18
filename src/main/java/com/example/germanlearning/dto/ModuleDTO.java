package com.example.germanlearning.dto;

import java.util.ArrayList;
import java.util.List;

public class ModuleDTO {
    private Long id;
    private String code;
    private String title;
    private String germanTitle;
    private String description;
    private String level;
    private Integer orderIndex;
    private int topicCount;
    private int lessonCount;
    private int testCount;
    private List<TopicDTO> topics = new ArrayList<>();

    public ModuleDTO() {
    }

    public ModuleDTO(Long id, String code, String title, String germanTitle, String description, String level, Integer orderIndex) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.germanTitle = germanTitle;
        this.description = description;
        this.level = level;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }

    public int getLessonCount() {
        return lessonCount;
    }

    public void setLessonCount(int lessonCount) {
        this.lessonCount = lessonCount;
    }

    public int getTestCount() {
        return testCount;
    }

    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }

    public List<TopicDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDTO> topics) {
        this.topics = topics;
    }
}

