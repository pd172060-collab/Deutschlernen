<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Slide ${slide.slideOrder} - ${slide.title} | DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <!-- Breadcrumbs -->
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <c:if test="${not empty slide.moduleId}">
            <a href="<c:url value='/modules/${slide.moduleId}'/>">${slide.moduleTitle != null ? slide.moduleTitle : 'Module'}</a>
            <span>&rsaquo;</span>
        </c:if>
        <c:if test="${not empty slide.topicId}">
            <a href="<c:url value='/topics/${slide.topicId}'/>">${slide.topicTitle != null ? slide.topicTitle : 'Topic'}</a>
            <span>&rsaquo;</span>
        </c:if>
        <a href="<c:url value='/lessons/${slide.lessonId}'/>">${slide.lessonTitle}</a>
        <span>&rsaquo;</span>
        <span>Slide ${slide.slideOrder} of ${slide.totalSlides}</span>
    </div>

    <div class="slide-viewer-wrapper">
        <div class="slide-viewer-card">
            <!-- Header with Lesson Title & Slide Counter -->
            <div class="slide-top-header">
                <div class="slide-lesson-info">
                    <span class="slide-lesson-tag">${slide.topicTitle != null ? slide.topicTitle : 'German Learning Unit'}</span>
                    <span class="slide-lesson-title">${slide.lessonTitle}</span>
                </div>
                <div class="slide-counter-badge">
                    Slide ${slide.slideOrder} / ${slide.totalSlides}
                </div>
            </div>

            <!-- Dynamic Progress Bar -->
            <div class="slide-progress-track">
                <div class="slide-progress-bar" style="width: ${slide.progressPercentage}%;"></div>
            </div>

            <!-- Main Slide Content Area -->
            <div class="slide-body-content">
                <!-- Slide Title & Content Type Badge -->
                <div class="slide-title-area">
                    <h2 class="slide-main-title">${slide.title}</h2>
                    <span class="slide-type-badge">${slide.contentType}</span>
                </div>

                <!-- 1. German Text (Prominently Displayed) -->
                <c:if test="${not empty slide.germanText}">
                    <div class="german-core-card">
                        <div class="german-core-label">🇩🇪 Deutsch</div>
                        <div class="german-core-text">${slide.germanText}</div>
                    </div>
                </c:if>

                <!-- 2. English Translation (Visually Separated) -->
                <c:if test="${not empty slide.englishTranslation}">
                    <div class="translation-card">
                        <div class="translation-label">🇬🇧 English Translation</div>
                        <div class="translation-text">${slide.englishTranslation}</div>
                    </div>
                </c:if>

                <!-- 3. Educational Explanation -->
                <c:if test="${not empty slide.explanation}">
                    <div class="explanation-block">
                        <h4>💡 Explanation & Context</h4>
                        <p>${slide.explanation}</p>
                    </div>
                </c:if>

                <!-- 4. Grammar Rule Callout -->
                <c:if test="${not empty slide.grammarRule}">
                    <div class="grammar-box">
                        <div class="grammar-box-title">
                            <span>📐</span> Grammatik-Regel (Grammar Rule)
                        </div>
                        <div class="grammar-box-content">${slide.grammarRule}</div>
                    </div>
                </c:if>

                <!-- 5. Examples Section -->
                <c:if test="${not empty slide.examples}">
                    <div class="example-box">
                        <div class="example-box-title">
                            <span>💬</span> Beispielsätze (Examples)
                        </div>
                        <div class="example-box-content">${slide.examples}</div>
                    </div>
                </c:if>

                <!-- 6. Vocabulary Pills / Grid -->
                <c:if test="${not empty slide.vocabulary}">
                    <div class="vocab-box">
                        <div class="vocab-box-title">
                            <span>📖</span> Neuer Wortschatz (Vocabulary)
                        </div>
                        <div class="vocab-pills">
                            <c:forTokens items="${slide.vocabulary}" delims="|" var="vocabItem">
                                <span class="vocab-pill">${vocabItem}</span>
                            </c:forTokens>
                        </div>
                    </div>
                </c:if>

                <!-- 7. Important Note Callout -->
                <c:if test="${not empty slide.importantNote}">
                    <div class="note-alert-box">
                        <div class="note-alert-title">
                            <span>⚠️</span> Wichtiger Hinweis (Important Note)
                        </div>
                        <div class="note-alert-content">${slide.importantNote}</div>
                    </div>
                </c:if>
            </div>

            <!-- Slide Navigation Bar: Previous / Next / Complete Lesson -->
            <div class="slide-bottom-bar">
                <div>
                    <c:choose>
                        <c:when test="${slide.hasPrevious}">
                            <a href="<c:url value='/lessons/${slide.lessonId}/slides/${slide.previousSlideOrder}'/>" class="btn btn-outline">
                                &larr; Previous Slide
                            </a>
                        </c:when>
                        <c:otherwise>
                            <span class="btn btn-outline btn-nav-disabled">&larr; Previous Slide</span>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div style="display: flex; gap: 12px; align-items: center;">
                    <a href="<c:url value='/lessons/${slide.lessonId}'/>" class="btn btn-outline" style="font-size: 0.85rem;">
                        Overview
                    </a>

                    <c:choose>
                        <c:when test="${slide.hasNext}">
                            <a href="<c:url value='/lessons/${slide.lessonId}/slides/${slide.nextSlideOrder}'/>" class="btn btn-primary">
                                Next Slide &rarr;
                            </a>
                        </c:when>
                        <c:otherwise>
                            <form action="<c:url value='/lessons/${slide.lessonId}/complete'/>" method="post" style="display: inline; margin: 0;">
                                <button type="submit" class="btn btn-complete">
                                    ✓ Complete Lesson
                                </button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../common/footer.jsp" />

