<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${topic.title} - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <a href="<c:url value='/modules'/>">Modules</a>
        <span>&rsaquo;</span>
        <a href="<c:url value='/modules/${topic.moduleId}'/>">${topic.moduleTitle != null ? topic.moduleTitle : 'Module'}</a>
        <span>&rsaquo;</span>
        <span>${topic.title}</span>
    </div>

    <!-- Topic Header Card -->
    <div class="card" style="margin-bottom: 32px; border-left: 6px solid var(--accent-blue);">
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 12px;">
            <div>
                <span class="module-code">Topic ${topic.orderIndex}</span>
                <h1 style="font-size: 1.8rem; font-weight: 800; color: var(--primary);">${topic.title}</h1>
                <div style="font-size: 1.1rem; color: var(--accent-gold); font-weight: 600; margin-top: 2px;">
                    ${topic.germanTitle}
                </div>
            </div>
            <span class="badge-pill" style="font-size: 0.85rem; padding: 6px 14px; background: #EFF6FF; color: #1D4ED8;">
                ${topic.lessonCount} Lessons Available
            </span>
        </div>
        <p style="color: #475569; font-size: 1.05rem; margin-top: 12px;">${topic.description}</p>
        
        <div class="module-meta" style="margin-top: 20px; margin-bottom: 0;">
            <span class="meta-item">📖 ${topic.lessonCount} Lessons</span>
            <span class="meta-item">📝 ${topic.quizCount} Quizzes</span>
        </div>
    </div>

    <!-- Lessons Section -->
    <div class="section-header">
        <div>
            <h2 class="section-title">Lessons in this Topic</h2>
            <p class="section-subtitle">Click on any lesson below to review its overview and start the interactive slide viewer</p>
        </div>
        <a href="<c:url value='/modules/${topic.moduleId}'/>" class="btn btn-outline" style="font-size: 0.85rem;">&larr; Back to Module</a>
    </div>

    <c:choose>
        <c:when test="${not empty topic.lessons}">
            <div style="display: flex; flex-direction: column; gap: 16px;">
                <c:forEach var="lesson" items="${topic.lessons}" varStatus="status">
                    <div class="card" style="padding: 20px 24px; transition: var(--transition);">
                        <div style="display: flex; justify-content: space-between; align-items: center;">
                            <div style="display: flex; align-items: center; gap: 16px;">
                                <div class="lesson-number" style="width: 36px; height: 36px; font-size: 1rem; background: #1E293B; color: #FFFFFF;">
                                    ${status.count}
                                </div>
                                <div>
                                    <h3 style="font-size: 1.15rem; font-weight: 700; color: var(--primary); margin-bottom: 2px;">
                                        ${lesson.title}
                                    </h3>
                                    <div style="font-size: 0.9rem; color: var(--accent-gold); font-weight: 600; margin-bottom: 4px;">
                                        ${lesson.germanTitle}
                                    </div>
                                    <p style="font-size: 0.85rem; color: var(--text-muted); margin-bottom: 0;">
                                        ${lesson.description}
                                    </p>
                                </div>
                            </div>
                            <div style="display: flex; align-items: center; gap: 12px;">
                                <span class="badge-pill">⏱️ ~${lesson.estimatedMinutes} mins</span>
                                <span class="badge-pill">📑 ${lesson.slideCount} Slides</span>
                                <a href="<c:url value='/lessons/${lesson.id}'/>" class="btn btn-primary" style="padding: 8px 16px; font-size: 0.9rem;">
                                    View Lesson &rarr;
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="card" style="text-align: center; padding: 32px; color: var(--text-muted);">
                <p>No lessons are currently loaded for this topic.</p>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="../common/footer.jsp" />

