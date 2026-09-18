package com.example.germanlearning.dto;

import java.util.ArrayList;
import java.util.List;

public class LessonDTO {
    private Long id;
    private Long topicId;
    private String topicTitle;
    private Long moduleId;
    private String moduleTitle;
    private String title;
    private String germanTitle;
    private String description;
    private Integer orderIndex;
    private Integer estimatedMinutes;
    private int slideCount;
    private boolean completed;
    private List<SlideDTO> slides = new ArrayList<>();

    public LessonDTO() {
    }

    public LessonDTO(Long id, Long topicId, String title, String germanTitle, String description, Integer orderIndex, Integer estimatedMinutes, int slideCount) {
        this.id = id;
        this.topicId = topicId;
        this.title = title;
        this.germanTitle = germanTitle;
        this.description = description;
        this.orderIndex = orderIndex;
        this.estimatedMinutes = estimatedMinutes;
        this.slideCount = slideCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(Integer estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    public int getSlideCount() {
        return slideCount;
    }

    public void setSlideCount(int slideCount) {
        this.slideCount = slideCount;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<SlideDTO> getSlides() {
        return slides;
    }

    public void setSlides(List<SlideDTO> slides) {
        this.slides = slides;
    }
}
