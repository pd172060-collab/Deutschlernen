<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Module Tests - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <span>Module Tests</span>
    </div>

    <!-- Hero Banner -->
    <div class="hero-banner" style="background: linear-gradient(135deg, #1E293B 0%, #0F172A 100%); margin-bottom: 32px;">
        <div class="hero-content">
            <span class="hero-badge" style="background: rgba(220, 38, 38, 0.2); color: #F87171; border-color: rgba(220, 38, 38, 0.4);">
                Comprehensive Assessments
            </span>
            <h1 class="hero-title">Module Assessment Tests</h1>
            <p class="hero-description">
                Formal comprehensive evaluation exams designed to test your mastery across all topic units in each module. Questions cover vocabulary, grammar, verb conjugations, and translations.
            </p>
            <div class="hero-actions">
                <a href="<c:url value='/tests/history'/>" class="btn btn-secondary">
                    📜 View Test History
                </a>
                <a href="<c:url value='/modules'/>" class="btn btn-outline" style="color: #FFFFFF; border-color: rgba(255,255,255,0.3);">
                    📚 Course Modules
                </a>
            </div>
        </div>
    </div>

    <!-- Section Header -->
    <div class="section-header">
        <div>
            <h2 class="section-title">Available Module Tests</h2>
            <p class="section-subtitle">Select a module test below to begin a comprehensive multi-topic examination</p>
        </div>
        <a href="<c:url value='/tests/history'/>" class="btn btn-outline" style="font-size: 0.85rem;">
            📜 History (${tests != null ? tests.size() : 0} Tests)
        </a>
    </div>

    <c:choose>
        <c:when test="${not empty tests}">
            <div class="grid-2" style="gap: 24px;">
                <c:forEach var="test" items="${tests}">
                    <div class="card card-module" style="border-top: 5px solid var(--accent-red); display: flex; flex-direction: column; justify-content: space-between;">
                        <div>
                            <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 10px;">
                                <div>
                                    <span class="module-code" style="color: var(--accent-red);">${test.moduleCode}</span>
                                    <span class="badge-level badge-a1" style="margin-left: 8px;">${test.moduleLevel}</span>
                                </div>
                                <c:choose>
                                    <c:when test="${test.passedByCurrentUser}">
                                        <span class="badge-pill" style="background: #DCFCE7; color: #15803D; font-weight: 700;">
                                            ✓ Passed
                                        </span>
                                    </c:when>
                                    <c:when test="${test.totalAttempts > 0}">
                                        <span class="badge-pill" style="background: #FEF3C7; color: #B45309; font-weight: 600;">
                                            ${test.totalAttempts} ${test.totalAttempts == 1 ? 'Attempt' : 'Attempts'}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-pill" style="background: #F1F5F9; color: #64748B;">
                                            Not Attempted
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <h3 style="font-size: 1.25rem; font-weight: 700; color: var(--primary); margin-bottom: 4px;">
                                ${test.title}
                            </h3>
                            <div style="font-size: 0.9rem; color: var(--accent-gold); font-weight: 600; margin-bottom: 12px;">
                                ${test.moduleGermanTitle}
                            </div>
                            <p style="font-size: 0.9rem; color: #475569; margin-bottom: 20px;">
                                ${test.description}
                            </p>
                        </div>

                        <div>
                            <div class="module-meta" style="margin-bottom: 16px;">
                                <span class="meta-item">❓ ${test.questionCount} Questions</span>
                                <span class="meta-item">⏱️ ${test.timeLimitMinutes} Mins</span>
                                <span class="meta-item">🎯 Pass: ${test.passingScore}%</span>
                            </div>

                            <div style="display: flex; gap: 12px;">
                                <a href="<c:url value='/tests/${test.id}/start'/>" class="btn btn-primary" style="flex: 1; justify-content: center; padding: 12px 20px; font-weight: 700; background: var(--accent-red);">
                                    🎯 Start Module Test &rarr;
                                </a>
                                <a href="<c:url value='/modules/${test.moduleId}'/>" class="btn btn-outline" style="padding: 12px 16px;" title="View Module Lessons">
                                    📖 Lessons
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card" style="text-align: center; padding: 48px; color: var(--text-muted);">
                <p style="font-size: 1.1rem; margin-bottom: 8px;">No Module Tests currently available.</p>
                <p>Please check that course modules and questions are initialized.</p>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../common/footer.jsp" />

