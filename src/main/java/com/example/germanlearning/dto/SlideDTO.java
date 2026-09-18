package com.example.germanlearning.dto;

public class SlideDTO {
    private Long id;
    private Long lessonId;
    private String lessonTitle;
    private Long topicId;
    private String topicTitle;
    private Long moduleId;
    private String moduleTitle;
    private Integer slideOrder;
    private Integer totalSlides;
    private String title;
    private String germanText;
    private String englishTranslation;
    private String explanation;
    private String contentType; // CONCEPT, VOCABULARY, GRAMMAR, DIALOGUE, TABLE, EXAMPLE
    private String vocabulary;
    private String grammarRule;
    private String examples;
    private String importantNote;
    private boolean hasPrevious;
    private boolean hasNext;
    private Integer previousSlideOrder;
    private Integer nextSlideOrder;
    private boolean firstSlide;
    private boolean lastSlide;
    private int progressPercentage;

    public SlideDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
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

    public Integer getSlideOrder() {
        return slideOrder;
    }

    public void setSlideOrder(Integer slideOrder) {
        this.slideOrder = slideOrder;
    }

    public Integer getTotalSlides() {
        return totalSlides;
    }

    public void setTotalSlides(Integer totalSlides) {
        this.totalSlides = totalSlides;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGermanText() {
        return germanText;
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

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Integer getPreviousSlideOrder() {
        return previousSlideOrder;
    }

    public void setPreviousSlideOrder(Integer previousSlideOrder) {
        this.previousSlideOrder = previousSlideOrder;
    }

    public Integer getNextSlideOrder() {
        return nextSlideOrder;
    }

    public void setNextSlideOrder(Integer nextSlideOrder) {
        this.nextSlideOrder = nextSlideOrder;
    }

    public boolean isFirstSlide() {
        return firstSlide;
    }

    public void setFirstSlide(boolean firstSlide) {
        this.firstSlide = firstSlide;
    }

    public boolean isLastSlide() {
        return lastSlide;
    }

    public void setLastSlide(boolean lastSlide) {
        this.lastSlide = lastSlide;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}

