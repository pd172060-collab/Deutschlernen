<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Practice Exercises - DeutschLernen" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="breadcrumb">
        <a href="<c:url value='/dashboard'/>">Dashboard</a>
        <span>&rsaquo;</span>
        <span>Practice Hub</span>
    </div>

    <div class="card" style="text-align: center; padding: 48px 24px; max-width: 700px; margin: 20px auto;">
        <div style="font-size: 3.5rem; margin-bottom: 16px;">✍️</div>
        <h2 style="font-size: 1.8rem; font-weight: 800; color: var(--primary); margin-bottom: 8px;">Practice Hub</h2>
        <p style="color: var(--text-muted); font-size: 1.05rem; margin-bottom: 24px;">
            Interactive exercises including flashcards, vocabulary drills, sentence construction, and listening comprehension will be activated in upcoming parts.
        </p>
        <div style="display: inline-block; background: #EEF2FF; color: #4F46E5; font-weight: 600; padding: 6px 16px; border-radius: 20px; font-size: 0.9rem; margin-bottom: 24px;">
            Placeholder &bull; Scheduled for Part 2
        </div>
        <div>
            <a href="<c:url value='/dashboard'/>" class="btn btn-primary">Back to Dashboard</a>
        </div>
    </div>
</main>

<jsp:include page="../common/footer.jsp" />

