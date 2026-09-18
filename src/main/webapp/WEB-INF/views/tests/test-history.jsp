<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Module Test History - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <a href="<c:url value='/tests'/>">Module Tests</a>
        <span>&rsaquo;</span>
        <span>History</span>
    </div>

    <!-- Section Header -->
    <div class="section-header">
        <div>
            <h1 class="section-title">Module Test Attempt History</h1>
            <p class="section-subtitle">Review your comprehensive examination scores across Modules I–IV</p>
        </div>
        <a href="<c:url value='/tests'/>" class="btn btn-primary" style="font-size: 0.9rem; background: var(--accent-red);">
            &larr; Back to Module Tests
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
                                <th>Module Test</th>
                                <th>Module</th>
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
                                        <strong>${attempt.testTitle}</strong>
                                    </td>
                                    <td>
                                        <span class="module-code" style="color: var(--accent-red);">${attempt.moduleCode}</span>
                                    </td>
                                    <td><strong>${attempt.score}</strong> / ${attempt.maxScore} pts</td>
                                    <td class="col-highlight" style="font-weight: 800; color: ${attempt.passed ? '#10B981' : '#EF4444'};">
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
                                        <c:if test="${attempt.testId != null}">
                                            <a href="<c:url value='/tests/${attempt.testId}/start'/>" class="btn btn-outline" style="padding: 6px 14px; font-size: 0.85rem;">
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
                <h3 style="font-size: 1.3rem; font-weight: 700; color: var(--primary); margin-bottom: 6px;">No Test Attempts Yet</h3>
                <p style="margin-bottom: 24px;">Complete your first module assessment test to view past examination records here.</p>
                <a href="<c:url value='/tests'/>" class="btn btn-primary" style="background: var(--accent-red);">Browse Module Tests &rarr;</a>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../common/footer.jsp" />

