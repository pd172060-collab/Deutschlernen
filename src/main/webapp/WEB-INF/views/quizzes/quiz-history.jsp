<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Quiz History - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <a href="<c:url value='/quizzes'/>">Topic Quizzes</a>
        <span>&rsaquo;</span>
        <span>History</span>
    </div>

    <!-- Section Header -->
    <div class="section-header">
        <div>
            <h1 class="section-title">Quiz Attempt History</h1>
            <p class="section-subtitle">Review your past scores and progress across all topic checkpoints</p>
        </div>
        <a href="<c:url value='/quizzes'/>" class="btn btn-primary" style="font-size: 0.9rem;">
            &larr; Back to Topic Quizzes
        </a>
    </div>

    <c:choose>
        <c:when test="${not empty history}">
            <div class="card" style="padding: 0; overflow: hidden;">
                <div class="slide-table-wrapper" style="margin: 0; border: none;">
                    <table class="slide-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Date & Time</th>
                                <th>Topic Quiz</th>
                                <th>Score</th>
                                <th>Percentage</th>
                                <th>Status</th>
                                <th style="text-align: right;">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="attempt" items="${history}" varStatus="status">
                                <tr>
                                    <td style="font-weight: 700; color: var(--text-muted);">${status.count}</td>
                                    <td>${attempt.completedAtFormatted}</td>
                                    <td class="col-german">
                                        <strong>${attempt.quizTitle}</strong>
                                        <c:if test="${not empty attempt.topicTitle}">
                                            <div style="font-size: 0.8rem; color: var(--text-muted);">${attempt.topicTitle}</div>
                                        </c:if>
                                    </td>
                                    <td><strong>${attempt.score}</strong> / ${attempt.maxScore} pts</td>
                                    <td class="col-highlight" style="font-weight: 800;">
                                        ${attempt.scorePercentage}%
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${attempt.passed}">
                                                <span class="badge-pill" style="background: #DCFCE7; color: #15803D; font-weight: 700;">
                                                    ✓ Passed
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge-pill" style="background: #FEE2E2; color: #DC2626; font-weight: 700;">
                                                    ✗ Failed
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="text-align: right;">
                                        <c:if test="${attempt.topicId != null}">
                                            <a href="<c:url value='/quizzes/topic/${attempt.topicId}/start'/>" class="btn btn-outline" style="padding: 6px 14px; font-size: 0.85rem;">
                                                🔄 Retake
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card" style="text-align: center; padding: 48px; color: var(--text-muted);">
                <div style="font-size: 3rem; margin-bottom: 12px;">📜</div>
                <h3 style="font-size: 1.3rem; font-weight: 700; color: var(--primary); margin-bottom: 6px;">No Quiz Attempts Yet</h3>
                <p style="margin-bottom: 24px;">Start practicing with topic quizzes to see your attempt history and score trends.</p>
                <a href="<c:url value='/quizzes'/>" class="btn btn-primary">Browse Topic Quizzes &rarr;</a>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../common/footer.jsp" />

