<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Answer Feedback - Topic Quiz" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="slide-viewer-wrapper" style="max-width: 800px; margin: 0 auto;">
        
        <!-- Breadcrumb -->
        <div class="breadcrumb" style="margin-bottom: 16px;">
            <a href="<c:url value='/dashboard'/>">Dashboard</a>
            <span>&rsaquo;</span>
            <a href="<c:url value='/quizzes'/>">Topic Quizzes</a>
            <span>&rsaquo;</span>
            <span>${quizSession.topicTitle}</span>
        </div>

        <div class="slide-viewer-card">
            
            <!-- Top Header -->
            <div class="slide-top-header">
                <div class="slide-lesson-info">
                    <span class="slide-lesson-tag">Feedback &bull; ${quizSession.moduleTitle}</span>
                    <span class="slide-lesson-title">${quizSession.topicTitle}</span>
                </div>
                <div class="slide-counter-badge">
                    Question ${feedback.questionIndex} / ${feedback.totalQuestions}
                </div>
            </div>

            <!-- Progress Bar -->
            <div class="slide-progress-track">
                <div class="slide-progress-bar" style="width: ${(feedback.questionIndex * 100) / feedback.totalQuestions}%;"></div>
            </div>

            <div class="slide-body-content" style="padding: 36px;">
                
                <!-- Feedback Outcome Banner -->
                <c:choose>
                    <c:when test="${feedback.correct}">
                        <div style="background: #ECFDF5; border: 2px solid #10B981; border-radius: var(--radius-md); padding: 20px 24px; margin-bottom: 28px; display: flex; align-items: center; gap: 16px;">
                            <div style="width: 48px; height: 48px; border-radius: 50%; background: #10B981; color: #FFFFFF; display: flex; align-items: center; justify-content: center; font-size: 1.5rem; font-weight: 800; flex-shrink: 0;">
                                ✓
                            </div>
                            <div>
                                <h3 style="font-size: 1.3rem; font-weight: 800; color: #065F46; margin-bottom: 2px;">
                                    Richtig! Correct!
                                </h3>
                                <p style="font-size: 0.95rem; color: #047857; margin-bottom: 0;">
                                    Well done! You earned <strong>+${feedback.pointsEarned}</strong> points.
                                </p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div style="background: #FEF2F2; border: 2px solid #EF4444; border-radius: var(--radius-md); padding: 20px 24px; margin-bottom: 28px; display: flex; align-items: center; gap: 16px;">
                            <div style="width: 48px; height: 48px; border-radius: 50%; background: #EF4444; color: #FFFFFF; display: flex; align-items: center; justify-content: center; font-size: 1.5rem; font-weight: 800; flex-shrink: 0;">
                                ✗
                            </div>
                            <div>
                                <h3 style="font-size: 1.3rem; font-weight: 800; color: #991B1B; margin-bottom: 2px;">
                                    Leider nicht richtig / Incorrect
                                </h3>
                                <p style="font-size: 0.95rem; color: #B91C1C; margin-bottom: 0;">
                                    Review the explanation below to reinforce your understanding.
                                </p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>

                <!-- Question Prompt -->
                <div class="german-core-card" style="margin-bottom: 24px;">
                    <div class="german-core-label">
                        <span>❓ Question</span>
                    </div>
                    <div class="german-core-text" style="font-size: 1.15rem;">
                        ${feedback.questionText}
                    </div>
                </div>

                <!-- Answer Comparison Card -->
                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 20px 24px; margin-bottom: 24px;">
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
                        <div>
                            <span style="font-size: 0.75rem; font-weight: 700; text-transform: uppercase; color: var(--text-muted); display: block; margin-bottom: 4px;">
                                Your Answer
                            </span>
                            <div style="font-size: 1.1rem; font-weight: 700; color: ${feedback.correct ? '#059669' : '#DC2626'};">
                                <c:choose>
                                    <c:when test="${not empty feedback.userAnswer}">
                                        ${feedback.userAnswer}
                                    </c:when>
                                    <c:otherwise>
                                        <em style="color: var(--text-muted); font-weight: normal;">(No answer given)</em>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div>
                            <span style="font-size: 0.75rem; font-weight: 700; text-transform: uppercase; color: var(--text-muted); display: block; margin-bottom: 4px;">
                                Correct Answer
                            </span>
                            <div style="font-size: 1.1rem; font-weight: 700; color: #059669;">
                                ${feedback.correctAnswer}
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Grammatical Explanation -->
                <c:if test="${not empty feedback.explanation}">
                    <div class="grammar-box" style="margin-bottom: 28px;">
                        <div class="grammar-box-title">
                            <span>💡 Grammar & Vocabulary Explanation</span>
                        </div>
                        <div class="grammar-box-content">
                            ${feedback.explanation}
                        </div>
                    </div>
                </c:if>

                <!-- Next Question / Result Button -->
                <div style="display: flex; justify-content: flex-end; padding-top: 20px; border-top: 1px solid var(--border-color);">
                    <a href="<c:url value='/quizzes/next'/>" class="btn btn-primary" style="padding: 12px 32px; font-size: 1.1rem; font-weight: 700;">
                        <c:choose>
                            <c:when test="${feedback.lastQuestion}">
                                View Final Results &rarr;
                            </c:when>
                            <c:otherwise>
                                Next Question &rarr;
                            </c:otherwise>
                        </c:choose>
                    </a>
                </div>

            </div>
        </div>
    </div>
</main>

<jsp:include page="../common/footer.jsp" />

