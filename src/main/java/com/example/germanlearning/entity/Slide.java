package com.example.germanlearning.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "slides")
public class Slide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "slide_order", nullable = false)
    private Integer slideOrder;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "german_text", columnDefinition = "TEXT")
    private String germanText;

    @Column(name = "english_translation", columnDefinition = "TEXT")
    private String englishTranslation;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "content_type", length = 50)
    private String contentType = "CONCEPT"; // CONCEPT, VOCABULARY, GRAMMAR, DIALOGUE, TABLE, EXAMPLE

    @Column(columnDefinition = "TEXT")
    private String vocabulary;

    @Column(name = "grammar_rule", columnDefinition = "TEXT")
    private String grammarRule;

    @Column(columnDefinition = "TEXT")
    private String examples;

    @Column(name = "important_note", columnDefinition = "TEXT")
    private String importantNote;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Slide() {
    }

    public Slide(String title, Integer slideOrder, String contentType, Lesson lesson) {
        this.title = title;
        this.slideOrder = slideOrder;
        this.contentType = contentType;
        this.lesson = lesson;
    }

    public Slide(String title, String contentType, String content, Integer slideOrder, Lesson lesson) {
        this.title = title;
        this.contentType = contentType;
        this.content = content;
        this.germanText = content;
        this.slideOrder = slideOrder;
        this.lesson = lesson;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.germanText == null && this.content != null) {
            this.germanText = this.content;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        if (this.germanText == null && this.content != null) {
            this.germanText = this.content;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Integer getSlideOrder() {
        return slideOrder;
    }

    public void setSlideOrder(Integer slideOrder) {
        this.slideOrder = slideOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGermanText() {
        return germanText != null ? germanText : content;
    }

    public void setGermanText(String germanText) {
        this.germanText = germanText;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public void setEnglishTranslation(String englishTranslation) {
        this.englishTranslation = englishTranslation;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getGrammarRule() {
        return grammarRule;
    }

    public void setGrammarRule(String grammarRule) {
        this.grammarRule = grammarRule;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getImportantNote() {
        return importantNote;
    }

    public void setImportantNote(String importantNote) {
        this.importantNote = importantNote;
    }

    public String getContent() {
        return content != null ? content : germanText;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGermanExample() {
        return examples;
    }

    public void setGermanExample(String germanExample) {
        this.examples = germanExample;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
