<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Examination Results - ${summary.testTitle}" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div style="max-width: 900px; margin: 0 auto;">
        
        <!-- Breadcrumb -->
        <div class="breadcrumb">
            <a href="<c:url value='/dashboard'/>">Dashboard</a>
            <span>&rsaquo;</span>
            <a href="<c:url value='/tests'/>">Module Tests</a>
            <span>&rsaquo;</span>
            <span>${summary.moduleCode} Results</span>
        </div>

        <!-- Result Overview Banner -->
        <div class="card" style="padding: 36px; margin-bottom: 32px; text-align: center; border-top: 6px solid ${summary.passed ? '#10B981' : '#EF4444'};">
            <c:choose>
                <c:when test="${summary.passed}">
                    <div style="font-size: 3.5rem; margin-bottom: 12px;">🏆</div>
                    <span class="badge-pill" style="font-size: 0.9rem; padding: 6px 18px; background: #DCFCE7; color: #15803D; font-weight: 800; margin-bottom: 12px;">
                        MODULE TEST PASSED
                    </span>
                    <h1 style="font-size: 2.2rem; font-weight: 800; color: var(--primary); margin-top: 8px; margin-bottom: 4px;">
                        Herzlichen Glückwunsch! Congratulations!
                    </h1>
                    <p style="color: var(--text-muted); font-size: 1.05rem; margin-bottom: 28px;">
                        You successfully passed the comprehensive assessment for <strong>${summary.moduleTitle}</strong> with a score of <strong>${summary.scorePercentage}%</strong>.
                    </p>
                </c:when>
                <c:otherwise>
                    <div style="font-size: 3.5rem; margin-bottom: 12px;">📖</div>
                    <span class="badge-pill" style="font-size: 0.9rem; padding: 6px 18px; background: #FEE2E2; color: #DC2626; font-weight: 800; margin-bottom: 12px;">
                        DID NOT PASS
                    </span>
                    <h1 style="font-size: 2.2rem; font-weight: 800; color: var(--primary); margin-top: 8px; margin-bottom: 4px;">
                        Keep studying and try again!
                    </h1>
                    <p style="color: var(--text-muted); font-size: 1.05rem; margin-bottom: 28px;">
                        You scored <strong>${summary.scorePercentage}%</strong>. The required passing score for this module examination is <strong>${summary.passingScore}%</strong>.
                    </p>
                </c:otherwise>
            </c:choose>

            <!-- Metrics Grid -->
            <div style="display: grid; grid-template-columns: repeat(5, 1fr); gap: 14px; margin-bottom: 28px;">
                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 16px;">
                    <span style="font-size: 0.75rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Score</span>
                    <div style="font-size: 1.6rem; font-weight: 800; color: ${summary.passed ? '#10B981' : '#EF4444'}; margin-top: 4px;">
                        ${summary.scorePercentage}%
                    </div>
                </div>

                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 16px;">
                    <span style="font-size: 0.75rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Correct</span>
                    <div style="font-size: 1.6rem; font-weight: 800; color: #10B981; margin-top: 4px;">
                        ${summary.correctAnswers} / ${summary.totalQuestions}
                    </div>
                </div>

                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 16px;">
                    <span style="font-size: 0.75rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Incorrect</span>
                    <div style="font-size: 1.6rem; font-weight: 800; color: #EF4444; margin-top: 4px;">
                        ${summary.incorrectAnswers}
                    </div>
                </div>

                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 16px;">
                    <span style="font-size: 0.75rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Points</span>
                    <div style="font-size: 1.6rem; font-weight: 800; color: var(--accent-gold); margin-top: 4px;">
                        ${summary.earnedPoints} / ${summary.totalPoints}
                    </div>
                </div>

                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 16px;">
                    <span style="font-size: 0.75rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Time Taken</span>
                    <div style="font-size: 1.6rem; font-weight: 800; color: var(--primary); margin-top: 4px;">
                        ${summary.timeSpentFormatted}
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <div style="display: flex; justify-content: center; gap: 16px; flex-wrap: wrap;">
                <c:if test="${summary.testId != null}">
                    <a href="<c:url value='/tests/${summary.testId}/start'/>" class="btn btn-primary" style="padding: 12px 24px; font-size: 1rem; background: var(--accent-red);">
                        🔄 Retake Test
                    </a>
                </c:if>
                <c:if test="${summary.moduleId != null}">
                    <a href="<c:url value='/modules/${summary.moduleId}'/>" class="btn btn-secondary" style="background: #1E293B; color: #FFFFFF; padding: 12px 24px; font-size: 1rem;">
                        📚 Back to Module
                    </a>
                </c:if>
                <a href="<c:url value='/tests/history'/>" class="btn btn-outline" style="padding: 12px 24px; font-size: 1rem;">
                    📜 View Test History
                </a>
            </div>
        </div>

        <!-- Question Breakdown Review List -->
        <div class="section-header">
            <div>
                <h2 class="section-title">Examination Review</h2>
                <p class="section-subtitle">Detailed review of all questions, submitted responses, and solution keys</p>
            </div>
        </div>

        <div style="display: flex; flex-direction: column; gap: 16px; margin-bottom: 40px;">
            <c:forEach var="item" items="${summary.reviewItems}">
                <div class="card" style="padding: 20px 24px; border-left: 5px solid ${item.correct ? '#10B981' : '#EF4444'};">
                    <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 12px;">
                        <div style="display: flex; align-items: center; gap: 10px;">
                            <span style="display: inline-flex; align-items: center; justify-content: center; width: 28px; height: 28px; border-radius: 50%; background: ${item.correct ? '#DCFCE7' : '#FEE2E2'}; color: ${item.correct ? '#15803D' : '#DC2626'}; font-weight: 800; font-size: 0.9rem;">
                                ${item.correct ? '✓' : '✗'}
                            </span>
                            <span style="font-weight: 700; color: var(--primary);">Question ${item.questionIndex}</span>
                            <span class="badge-pill" style="font-size: 0.7rem;">${item.questionType}</span>
                        </div>
                        <span class="badge-pill" style="background: ${item.correct ? '#DCFCE7' : '#FEE2E2'}; color: ${item.correct ? '#15803D' : '#DC2626'}; font-weight: 700;">
                            ${item.earnedPoints} / ${item.points} Points
                        </span>
                    </div>

                    <p style="font-size: 1.05rem; font-weight: 600; color: var(--primary); margin-bottom: 16px;">
                        ${item.questionText}
                    </p>

                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px; margin-bottom: 12px; background: #F8FAFC; padding: 14px 18px; border-radius: var(--radius-sm);">
                        <div>
                            <span style="font-size: 0.75rem; text-transform: uppercase; font-weight: 700; color: var(--text-muted); display: block; margin-bottom: 2px;">Your Answer</span>
                            <div style="font-weight: 700; color: ${item.correct ? '#059669' : '#DC2626'};">
                                <c:choose>
                                    <c:when test="${not empty item.userAnswer}">${item.userAnswer}</c:when>
                                    <c:otherwise><em>(No answer)</em></c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div>
                            <span style="font-size: 0.75rem; text-transform: uppercase; font-weight: 700; color: var(--text-muted); display: block; margin-bottom: 2px;">Correct Answer</span>
                            <div style="font-weight: 700; color: #059669;">
                                ${item.correctAnswer}
                            </div>
                        </div>
                    </div>

                    <c:if test="${not empty item.explanation}">
                        <div style="font-size: 0.9rem; color: #475569; background: #EFF6FF; border-left: 3px solid #3B82F6; padding: 8px 14px; border-radius: var(--radius-sm);">
                            💡 <strong>Rule:</strong> ${item.explanation}
                        </div>
                    </c:if>
                </div>
            </c:forEach>
        </div>

    </div>
</main>

<jsp:include page="../common/footer.jsp" />

