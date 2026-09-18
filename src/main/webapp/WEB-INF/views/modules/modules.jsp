<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Learning Modules - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <span>All Modules</span>
    </div>

    <div class="section-header">
        <div>
            <h2 class="section-title">All Curriculum Modules</h2>
            <p class="section-subtitle">Select a module below to view its topics, lessons, and assessment criteria</p>
        </div>
    </div>

    <div class="grid-2">
        <c:forEach var="moduleItem" items="${modules}">
            <div class="card card-module">
                <div class="card-module-header">
                    <div>
                        <span class="module-code">${moduleItem.code}</span>
                        <h3 class="module-title" style="font-size: 1.3rem;">${moduleItem.title}</h3>
                        <div class="module-german-title">${moduleItem.germanTitle}</div>
                    </div>
                    <span class="badge-level ${moduleItem.level.startsWith('A1') ? 'badge-a1' : (moduleItem.level.startsWith('A2') ? 'badge-a2' : 'badge-b1')}">
                        Level ${moduleItem.level}
                    </span>
                </div>

                <p class="module-desc">${moduleItem.description}</p>

                <div class="module-meta">
                    <span class="meta-item">📁 <strong>${moduleItem.topicCount}</strong> Topics</span>
                    <span class="meta-item">📖 <strong>${moduleItem.lessonCount}</strong> Lessons</span>
                    <span class="meta-item">🎯 <strong>${moduleItem.testCount}</strong> Module Test</span>
                </div>

                <div style="display: flex; gap: 12px; margin-top: auto;">
                    <a href="<c:url value='/modules/${moduleItem.id}'/>" class="btn btn-primary" style="flex: 1; justify-content: center;">
                        View Module Syllabus &rarr;
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

<jsp:include page="../common/footer.jsp" />
