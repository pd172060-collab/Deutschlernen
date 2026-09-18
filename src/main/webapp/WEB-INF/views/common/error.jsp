<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Error - DeutschLernen" />
<jsp:include page="header.jsp" />
<jsp:include page="navbar.jsp" />

<main class="main-content">
    <div class="card" style="text-align: center; padding: 48px 24px; max-width: 600px; margin: 40px auto;">
        <div style="font-size: 3rem; margin-bottom: 16px;">⚠️</div>
        <h2 style="color: var(--accent-red); margin-bottom: 12px;">${errorTitle != null ? errorTitle : 'An Error Occurred'}</h2>
        <p style="color: var(--text-muted); margin-bottom: 24px;">${errorMessage != null ? errorMessage : 'Something went wrong while processing your request.'}</p>
        <a href="<c:url value='/dashboard'/>" class="btn btn-primary">Return to Dashboard</a>
    </div>
</main>

<jsp:include page="footer.jsp" />

