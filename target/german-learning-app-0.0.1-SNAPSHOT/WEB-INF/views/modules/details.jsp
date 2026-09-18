<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${module.title} - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <a href="<c:url value='/modules'/>">Modules</a>
        <span>&rsaquo;</span>
        <span>${module.code}</span>
    </div>

    <!-- Module Header Card -->
    <div class="card" style="margin-bottom: 32px; border-left: 6px solid var(--accent-red);">
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 12px;">
            <div>
                <span class="module-code">${module.code}</span>
                <h1 style="font-size: 1.8rem; font-weight: 800; color: var(--primary);">${module.title}</h1>
                <div style="font-size: 1.1rem; color: var(--accent-gold); font-weight: 600; margin-top: 2px;">
                    ${module.germanTitle}
                </div>
            </div>
            <span class="badge-level ${module.level.startsWith('A1') ? 'badge-a1' : (module.level.startsWith('A2') ? 'badge-a2' : 'badge-b1')}" style="font-size: 0.9rem; padding: 6px 14px;">
                CEFR ${module.level}
            </span>
        </div>
        <p style="color: #475569; font-size: 1.05rem; margin-top: 12px;">${module.description}</p>
        
        <div class="module-meta" style="margin-top: 20px; margin-bottom: 0;">
            <span class="meta-item">📁 ${module.topicCount} Topics</span>
            <span class="meta-item">📖 ${module.lessonCount} Total Lessons</span>
            <span class="meta-item">🎯 ${module.testCount} Final Assessment</span>
        </div>
    </div>

    <!-- Topics & Lessons Section -->
    <div class="section-header">
        <div>
            <h2 class="section-title">Topics & Learning Units</h2>
            <p class="section-subtitle">Follow the structured lessons below</p>
        </div>
    </div>

    <c:choose>
        <c:when test="${not empty module.topics}">
            <c:forEach var="topic" items="${module.topics}" varStatus="status">
                <div class="topic-accordion">
                    <div class="topic-header">
                        <div>
                            <span style="font-size: 0.8rem; font-weight: 700; color: var(--accent-blue); text-transform: uppercase;">
                                Topic ${status.count}
                            </span>
                            <div class="topic-title">${topic.title}</div>
                            <div style="font-size: 0.85rem; color: var(--text-muted); font-style: italic;">
                                ${topic.germanTitle}
                            </div>
                        </div>
                        <div style="display: flex; gap: 8px; align-items: center;">
                            <span class="badge-pill">${topic.lessonCount} Lessons</span>
                            <a href="<c:url value='/topics/${topic.id}'/>" class="btn btn-outline" style="font-size: 0.8rem; padding: 4px 10px;">
                                Topic Overview &rarr;
                            </a>
                        </div>
                    </div>
                    <div class="topic-body">
                        <p style="font-size: 0.9rem; color: var(--text-muted); margin-bottom: 16px;">
                            ${topic.description}
                        </p>

                        <c:choose>
                            <c:when test="${not empty topic.lessons}">
                                <ul class="lesson-list">
                                    <c:forEach var="lesson" items="${topic.lessons}" varStatus="lStatus">
                                        <li class="lesson-item">
                                            <div class="lesson-info">
                                                <div class="lesson-number">${lStatus.count}</div>
                                                <div>
                                                    <div style="font-weight: 600; font-size: 0.95rem; color: var(--primary);">
                                                        ${lesson.title}
                                                    </div>
                                                    <div style="font-size: 0.8rem; color: var(--text-muted);">
                                                        ${lesson.germanTitle} &bull; ~${lesson.estimatedMinutes} mins
                                                    </div>
                                                </div>
                                            </div>
                                            <div style="display: flex; gap: 8px; align-items: center;">
                                                <span class="badge-pill">${lesson.slideCount} Slides</span>
                                                <a href="<c:url value='/lessons/${lesson.id}'/>" class="btn btn-primary" style="font-size: 0.8rem; padding: 4px 12px;">
                                                    Start Lesson &rarr;
                                                </a>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <p style="color: var(--text-muted); font-style: italic; font-size: 0.85rem;">
                                    Lessons for this topic will be populated in subsequent parts.
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="card" style="text-align: center; padding: 32px; color: var(--text-muted);">
                <p>No topics have been loaded for this module yet.</p>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- Module Test Card Section -->
    <div class="card" style="margin-top: 32px; background: #F8FAFC; border: 2px dashed var(--border-color);">
        <div style="display: flex; justify-content: space-between; align-items: center;">
            <div>
                <div style="display: flex; align-items: center; gap: 8px;">
                    <span style="font-size: 1.5rem;">🎯</span>
                    <h3 style="font-size: 1.15rem; font-weight: 700; color: var(--primary);">${module.code} Final Assessment</h3>
                </div>
                <p style="color: var(--text-muted); font-size: 0.9rem; margin-top: 4px;">
                    Test your comprehensive mastery of all topics in this module. Pass with 75% to unlock the next level badge.
                </p>
            </div>
            <div>
                <a href="<c:url value='/tests'/>" class="btn btn-outline" style="font-size: 0.85rem;">
                    Test Overview
                </a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../common/footer.jsp" />

