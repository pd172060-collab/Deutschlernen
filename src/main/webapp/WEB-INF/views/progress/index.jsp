<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Learning Progress - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <span>Progress Tracker</span>
    </div>

    <div class="section-header">
        <div>
            <h2 class="section-title">Your Learning Milestones</h2>
            <p class="section-subtitle">Real-time breakdown of completed lessons, topic quizzes, and exam scores</p>
        </div>
    </div>

    <div class="grid-4" style="margin-bottom: 32px;">
        <div class="card" style="text-align: center;">
            <div style="font-size: 2rem; font-weight: 800; color: var(--accent-blue);">${summary.overallProgressPercent}%</div>
            <div style="font-size: 0.85rem; color: var(--text-muted); text-transform: uppercase; font-weight: 600;">Curriculum Progress</div>
        </div>
        <div class="card" style="text-align: center;">
            <div style="font-size: 2rem; font-weight: 800; color: var(--accent-green);">${summary.completedLessons} / ${summary.totalLessons}</div>
            <div style="font-size: 0.85rem; color: var(--text-muted); text-transform: uppercase; font-weight: 600;">Lessons Completed</div>
        </div>
        <div class="card" style="text-align: center;">
            <div style="font-size: 2rem; font-weight: 800; color: var(--accent-gold);">${summary.passedQuizzes} / ${summary.totalQuizzes}</div>
            <div style="font-size: 0.85rem; color: var(--text-muted); text-transform: uppercase; font-weight: 600;">Quizzes Passed</div>
        </div>
        <div class="card" style="text-align: center;">
            <div style="font-size: 2rem; font-weight: 800; color: var(--accent-red);">${summary.passedTests} / ${summary.totalTests}</div>
            <div style="font-size: 0.85rem; color: var(--text-muted); text-transform: uppercase; font-weight: 600;">Tests Passed</div>
        </div>
    </div>

    <div class="card">
        <h3 style="margin-bottom: 16px; font-size: 1.15rem;">Module Progression Breakdown</h3>
        <c:forEach var="moduleItem" items="${summary.modules}">
            <div style="margin-bottom: 20px; padding-bottom: 16px; border-bottom: 1px solid var(--border-color);">
                <div style="display: flex; justify-content: space-between; margin-bottom: 6px;">
                    <div>
                        <strong>${moduleItem.code}: ${moduleItem.title}</strong>
                        <span class="badge-pill" style="margin-left: 8px;">Level ${moduleItem.level}</span>
                    </div>
                    <span style="font-size: 0.85rem; color: var(--text-muted); font-weight: 600;">
                        ${moduleItem.lessonCount} Lessons &bull; ${moduleItem.topicCount} Topics
                    </span>
                </div>
                <div class="progress-bar-container">
                    <div class="progress-bar-fill" style="width: 0%;"></div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

<jsp:include page="../common/footer.jsp" />

