<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Topic Quizzes - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <span>Topic Quizzes</span>
    </div>

    <!-- Hero Header -->
    <div class="hero-banner" style="background: linear-gradient(135deg, #1E293B 0%, #0F172A 100%); margin-bottom: 32px;">
        <div class="hero-content">
            <span class="hero-badge">Knowledge Checkpoints</span>
            <h1 class="hero-title">Topic Quizzes</h1>
            <p class="hero-description">
                Reinforce your German mastery with topic-specific quizzes covering vocabulary, grammar rules, sentence structure, and verb conjugations from Modules I–IV.
            </p>
            <div class="hero-actions">
                <a href="<c:url value='/quizzes/history'/>" class="btn btn-secondary">
                    📜 View Quiz History
                </a>
                <a href="<c:url value='/modules'/>" class="btn btn-outline" style="color: #FFFFFF; border-color: rgba(255,255,255,0.3);">
                    📚 Review Course Modules
                </a>
            </div>
        </div>
    </div>

    <!-- Quizzes Catalog -->
    <div class="section-header">
        <div>
            <h2 class="section-title">Available Topic Quizzes</h2>
            <p class="section-subtitle">Select a topic below to test your knowledge with interactive questions</p>
        </div>
        <a href="<c:url value='/quizzes/history'/>" class="btn btn-outline" style="font-size: 0.85rem;">
            📜 History (${quizzes != null ? quizzes.size() : 0} Quizzes)
        </a>
    </div>

    <c:choose>
        <c:when test="${not empty quizzes}">
            <div class="grid-3" style="gap: 24px;">
                <c:forEach var="quiz" items="${quizzes}">
                    <div class="card card-module" style="border-top: 4px solid var(--accent-gold); display: flex; flex-direction: column; justify-content: space-between;">
                        <div>
                            <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 8px;">
                                <span class="module-code" style="color: var(--accent-blue);">${quiz.moduleCode}</span>
                                <c:choose>
                                    <c:when test="${quiz.passedByCurrentUser}">
                                        <span class="badge-pill" style="background: #DCFCE7; color: #15803D; font-weight: 700;">
                                            ✓ Passed
                                        </span>
                                    </c:when>
                                    <c:when test="${quiz.totalAttempts > 0}">
                                        <span class="badge-pill" style="background: #FEF3C7; color: #B45309; font-weight: 600;">
                                            ${quiz.totalAttempts} ${quiz.totalAttempts == 1 ? 'Attempt' : 'Attempts'}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-pill" style="background: #F1F5F9; color: #64748B;">
                                            New
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <h3 style="font-size: 1.15rem; font-weight: 700; color: var(--primary); margin-bottom: 4px;">
                                ${quiz.topicTitle}
                            </h3>
                            <div style="font-size: 0.85rem; color: var(--accent-gold); font-weight: 600; margin-bottom: 12px;">
                                ${quiz.germanTopicTitle}
                            </div>
                            <p style="font-size: 0.85rem; color: var(--text-muted); margin-bottom: 16px;">
                                ${quiz.description}
                            </p>
                        </div>

                        <div>
                            <div class="module-meta" style="margin-bottom: 16px;">
                                <span class="meta-item">❓ ${quiz.questionCount} Questions</span>
                                <span class="meta-item">⏱️ ~${quiz.timeLimitMinutes} mins</span>
                                <span class="meta-item">🎯 Pass: ${quiz.passingScore}%</span>
                            </div>

                            <a href="<c:url value='/quizzes/topic/${quiz.topicId}/start'/>" class="btn btn-primary" style="width: 100%; justify-content: center; padding: 10px 16px;">
                                📝 Start Topic Quiz &rarr;
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card" style="text-align: center; padding: 48px; color: var(--text-muted);">
                <p style="font-size: 1.1rem; margin-bottom: 8px;">No topic quizzes found.</p>
                <p>Please check that course topics and questions are initialized.</p>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../common/footer.jsp" />

