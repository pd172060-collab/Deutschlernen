<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Test Instructions: ${test.title} - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div style="max-width: 800px; margin: 0 auto;">
        
        <!-- Breadcrumb -->
        <div class="breadcrumb">
            <a href="<c:url value='/dashboard'/>">Dashboard</a>
            <span>&rsaquo;</span>
            <a href="<c:url value='/tests'/>">Module Tests</a>
            <span>&rsaquo;</span>
            <span>${test.moduleTitle}</span>
        </div>

        <!-- Instructions Card -->
        <div class="card" style="padding: 36px; border-top: 6px solid var(--accent-red); margin-bottom: 24px;">
            
            <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 16px;">
                <div>
                    <span class="module-code" style="color: var(--accent-red);">${test.moduleCode}</span>
                    <span class="badge-level badge-a1" style="margin-left: 8px;">${test.moduleLevel}</span>
                    <h1 style="font-size: 1.8rem; font-weight: 800; color: var(--primary); margin-top: 4px;">
                        ${test.title}
                    </h1>
                    <div style="font-size: 1.05rem; color: var(--accent-gold); font-weight: 600; margin-top: 2px;">
                        ${test.moduleGermanTitle}
                    </div>
                </div>
                <c:if test="${test.passedByCurrentUser}">
                    <span class="badge-pill" style="background: #DCFCE7; color: #15803D; font-weight: 800; padding: 6px 16px;">
                        ✓ Previously Passed
                    </span>
                </c:if>
            </div>

            <p style="font-size: 1.05rem; color: #475569; margin-bottom: 28px; line-height: 1.6;">
                ${test.description}
            </p>

            <!-- Test Specifications Grid -->
            <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px; margin-bottom: 28px;">
                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 18px; text-align: center;">
                    <span style="font-size: 0.75rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Time Limit</span>
                    <div style="font-size: 1.6rem; font-weight: 800; color: var(--primary); margin-top: 4px;">
                        ⏱️ ${test.timeLimitMinutes} min
                    </div>
                </div>

                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 18px; text-align: center;">
                    <span style="font-size: 0.75rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Total Questions</span>
                    <div style="font-size: 1.6rem; font-weight: 800; color: var(--primary); margin-top: 4px;">
                        ❓ ${test.questionCount}
                    </div>
                </div>

                <div style="background: #F8FAFC; border: 1px solid var(--border-color); border-radius: var(--radius-md); padding: 18px; text-align: center;">
                    <span style="font-size: 0.75rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase;">Passing Threshold</span>
                    <div style="font-size: 1.6rem; font-weight: 800; color: var(--accent-green); margin-top: 4px;">
                        🎯 ${test.passingScore}%
                    </div>
                </div>
            </div>

            <!-- Exam Guidelines -->
            <div class="note-alert-box" style="margin-bottom: 32px;">
                <div class="note-alert-title">
                    <span>📋 Examination Guidelines & Rules</span>
                </div>
                <div class="note-alert-content">
                    <ul style="margin-left: 20px; margin-top: 6px; display: flex; flex-direction: column; gap: 6px;">
                        <li>Questions are balanced across all topics in <strong>${test.moduleTitle}</strong>.</li>
                        <li>Includes multiple choice, article identification, verb conjugations, and translations.</li>
                        <li>German special characters toolbar (<code>ä</code>, <code>ö</code>, <code>ü</code>, <code>ß</code>) is provided for text responses.</li>
                        <li>Immediate grammatical explanations are provided after each question submission.</li>
                    </ul>
                </div>
            </div>

            <!-- Action Controls -->
            <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 20px; border-top: 1px solid var(--border-color);">
                <a href="<c:url value='/tests'/>" class="btn btn-outline">
                    &larr; Back to Tests
                </a>

                <form action="<c:url value='/tests/${test.id}/begin'/>" method="POST" style="margin: 0;">
                    <button type="submit" class="btn btn-primary" style="padding: 14px 32px; font-size: 1.1rem; font-weight: 700; background: var(--accent-red);">
                        🎯 Begin Module Test &rarr;
                    </button>
                </form>
            </div>

        </div>

    </div>
</main>

<jsp:include page="../common/footer.jsp" />

