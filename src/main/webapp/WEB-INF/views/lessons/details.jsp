<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${lesson.title} - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <a href="<c:url value='/modules'/>">Modules</a>
        <span>&rsaquo;</span>
        <c:if test="${not empty lesson.moduleId}">
            <a href="<c:url value='/modules/${lesson.moduleId}'/>">${lesson.moduleTitle != null ? lesson.moduleTitle : 'Module'}</a>
            <span>&rsaquo;</span>
        </c:if>
        <a href="<c:url value='/topics/${lesson.topicId}'/>">${lesson.topicTitle != null ? lesson.topicTitle : 'Topic'}</a>
        <span>&rsaquo;</span>
        <span>${lesson.title}</span>
    </div>

    <!-- Just Completed Notification -->
    <c:if test="${justCompleted}">
        <div class="completion-banner">
            <div style="font-size: 2rem;">🎉</div>
            <div>
                <h3 style="font-size: 1.1rem; font-weight: 700; margin-bottom: 2px;">Glückwunsch! Lesson Completed!</h3>
                <p style="font-size: 0.9rem; margin: 0;">You have successfully completed all slides for this lesson. Progress has been saved.</p>
            </div>
        </div>
    </c:if>

    <!-- Main Lesson Overview Card -->
    <div class="card" style="margin-bottom: 32px; border-left: 6px solid var(--accent-gold);">
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 12px;">
            <div>
                <span class="module-code">Lesson ${lesson.orderIndex}</span>
                <h1 style="font-size: 2rem; font-weight: 800; color: var(--primary); margin-bottom: 4px;">${lesson.title}</h1>
                <div style="font-size: 1.2rem; color: var(--accent-gold); font-weight: 600;">
                    ${lesson.germanTitle}
                </div>
            </div>
            <c:choose>
                <c:when test="${lesson.completed}">
                    <span class="badge-pill" style="font-size: 0.85rem; padding: 6px 14px; background: #DCFCE7; color: #15803D; font-weight: 700;">
                        ✓ Completed
                    </span>
                </c:when>
                <c:otherwise>
                    <span class="badge-pill" style="font-size: 0.85rem; padding: 6px 14px; background: #EFF6FF; color: #1D4ED8; font-weight: 700;">
                        ● Ready to Start
                    </span>
                </c:otherwise>
            </c:choose>
        </div>

        <p style="color: #475569; font-size: 1.1rem; margin-top: 16px; margin-bottom: 24px;">
            ${lesson.description}
        </p>

        <div class="grid-3" style="margin-bottom: 28px;">
            <div class="card" style="background: #F8FAFC; text-align: center; padding: 16px;">
                <div style="font-size: 1.5rem; margin-bottom: 4px;">⏱️</div>
                <div style="font-size: 1.1rem; font-weight: 700; color: var(--primary);">~${lesson.estimatedMinutes} Mins</div>
                <div style="font-size: 0.8rem; color: var(--text-muted); text-transform: uppercase;">Estimated Time</div>
            </div>
            <div class="card" style="background: #F8FAFC; text-align: center; padding: 16px;">
                <div style="font-size: 1.5rem; margin-bottom: 4px;">📑</div>
                <div style="font-size: 1.1rem; font-weight: 700; color: var(--primary);">${lesson.slideCount} Slides</div>
                <div style="font-size: 0.8rem; color: var(--text-muted); text-transform: uppercase;">Total Slides</div>
            </div>
            <div class="card" style="background: #F8FAFC; text-align: center; padding: 16px;">
                <div style="font-size: 1.5rem; margin-bottom: 4px;">💡</div>
                <div style="font-size: 1.1rem; font-weight: 700; color: var(--primary);">Interactive</div>
                <div style="font-size: 0.8rem; color: var(--text-muted); text-transform: uppercase;">Slide Engine</div>
            </div>
        </div>

        <div style="display: flex; gap: 16px; align-items: center;">
            <a href="<c:url value='/lessons/${lesson.id}/slides/1'/>" class="btn btn-primary" style="font-size: 1.1rem; padding: 12px 28px;">
                ▶ ${lesson.completed ? 'Review Lesson Slides' : 'Start Lesson Now'}
            </a>
            <a href="<c:url value='/topics/${lesson.topicId}'/>" class="btn btn-outline">
                &larr; Back to Topic
            </a>
        </div>
    </div>
</main>

<jsp:include page="../common/footer.jsp" />

