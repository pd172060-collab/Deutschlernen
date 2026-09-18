<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header class="navbar">
    <div class="nav-container">
        <a href="<c:url value='/dashboard'/>" class="brand-logo">
            <div class="brand-icon">DE</div>
            <div class="brand-text">
                <h1>DeutschLernen</h1>
                <span>Language Learning Platform</span>
            </div>
        </a>

        <ul class="nav-links">
            <li>
                <a href="<c:url value='/dashboard'/>" class="nav-link ${activeNav == 'dashboard' ? 'active' : ''}">
                    <span>🏠</span> Dashboard
                </a>
            </li>
            <li>
                <a href="<c:url value='/modules'/>" class="nav-link ${activeNav == 'modules' ? 'active' : ''}">
                    <span>📚</span> Modules
                </a>
            </li>
            <li>
                <a href="<c:url value='/practice'/>" class="nav-link ${activeNav == 'practice' ? 'active' : ''}">
                    <span>✍️</span> Practice
                </a>
            </li>
            <li>
                <a href="<c:url value='/quizzes'/>" class="nav-link ${activeNav == 'quizzes' ? 'active' : ''}">
                    <span>📝</span> Quizzes
                </a>
            </li>
            <li>
                <a href="<c:url value='/tests'/>" class="nav-link ${activeNav == 'tests' ? 'active' : ''}">
                    <span>🎯</span> Tests
                </a>
            </li>
            <li>
                <a href="<c:url value='/progress'/>" class="nav-link ${activeNav == 'progress' ? 'active' : ''}">
                    <span>📊</span> Progress
                </a>
            </li>
        </ul>
    </div>
</header>

