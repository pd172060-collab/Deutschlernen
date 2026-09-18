package com.example.germanlearning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code; // e.g., "MODULE_1", "MODULE_2", "MODULE_3", "MODULE_4"

    @Column(nullable = false, length = 150)
    private String title; // English title e.g. "Module I: Foundations & Greetings"

    @Column(name = "german_title", length = 150)
    private String germanTitle; // German title e.g. "Modul I: Grundlagen & Begrüßung"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 20)
    private String level; // e.g. "A1.1", "A1.2", "A2.1", "B1"

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "is_active")
    private Boolean active = true;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orderIndex ASC")
    private List<Topic> topics = new ArrayList<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Test> tests = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Module() {
    }

    public Module(String code, String title, String germanTitle, String description, String level, Integer orderIndex) {
        this.code = code;
        this.title = title;
        this.germanTitle = germanTitle;
        this.description = description;
        this.level = level;
        this.orderIndex = orderIndex;
        this.active = true;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Helper methods for bidirectional relationships
    public void addTopic(Topic topic) {
        topics.add(topic);
        topic.setModule(this);
    }

    public void removeTopic(Topic topic) {
        topics.remove(topic);
        topic.setModule(null);
    }

    public void addTest(Test test) {
        tests.add(test);
        test.setModule(this);
    }

    public void removeTest(Test test) {
        tests.remove(test);
        test.setModule(null);
    }

    // Getters and Setters
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}

