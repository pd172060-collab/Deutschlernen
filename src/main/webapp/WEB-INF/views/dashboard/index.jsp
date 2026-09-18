<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Dashboard - DeutschLernen German Platform" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <!-- Hero Banner -->
    <section class="hero-banner">
        <div class="hero-content">
            <span class="hero-badge">🇩🇪 Wilkommen beim DeutschLernen</span>
            <h1 class="hero-title">Start Your German Journey</h1>
            <p class="hero-description">
                A structured, module-based platform designed to take you from foundational basics (A1) to confident German communication through guided lessons, interactive exercises, and module assessments.
            </p>
            <div class="hero-actions">
                <a href="<c:url value='/modules'/>" class="btn btn-primary">Explore Modules</a>
                <a href="<c:url value='/modules/1'/>" class="btn btn-secondary">Start Module I</a>
            </div>
        </div>
    </section>

    <!-- Learning Modules Overview -->
    <section style="margin-bottom: 40px;">
        <div class="section-header">
            <div>
                <h2 class="section-title">Core Learning Modules</h2>
                <p class="section-subtitle">Structured roadmap based on the CEFR curriculum</p>
            </div>
            <a href="<c:url value='/modules'/>" class="btn btn-outline" style="font-size: 0.85rem; padding: 6px 14px;">View All Modules &rarr;</a>
        </div>

        <div class="grid-4">
            <c:forEach var="moduleItem" items="${summary.modules}">
                <div class="card card-module">
                    <div class="card-module-header">
                        <span class="module-code">${moduleItem.code}</span>
                        <span class="badge-level ${moduleItem.level.startsWith('A1') ? 'badge-a1' : (moduleItem.level.startsWith('A2') ? 'badge-a2' : 'badge-b1')}">
                            ${moduleItem.level}
                        </span>
                    </div>
                    <h3 class="module-title">${moduleItem.title}</h3>
                    <div class="module-german-title">${moduleItem.germanTitle}</div>
                    <p class="module-desc">${moduleItem.description}</p>
                    
                    <div class="module-meta">
                        <span class="meta-item">📖 ${moduleItem.topicCount} Topics</span>
                        <span class="meta-item">📝 ${moduleItem.lessonCount} Lessons</span>
                    </div>

                    <a href="<c:url value='/modules/${moduleItem.id}'/>" class="btn btn-primary" style="width: 100%; justify-content: center; margin-top: auto;">
                        Open Module
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

    <!-- Placeholder Sections: Practice, Quizzes, Tests, Progress -->
    <section style="margin-bottom: 40px;">
        <div class="section-header">
            <div>
                <h2 class="section-title">Learning Hub & Activities</h2>
                <p class="section-subtitle">Practice vocabulary, attempt quizzes, and take module tests</p>
            </div>
        </div>

        <div class="grid-3">

            <!-- Quizzes Card -->
            <div class="feature-card">
                <div class="feature-icon-wrapper icon-quiz">📝</div>
                <div class="feature-info">
                    <h3>Topic Quizzes</h3>
                    <p>Short check-in quizzes after each topic to assess understanding.</p>
                    <span class="badge-pill">${summary.totalQuizzes} Available &bull; Part 2</span>
                    <div style="margin-top: 12px;">
                        <a href="<c:url value='/quizzes'/>" class="btn btn-outline" style="font-size: 0.8rem; padding: 4px 10px;">View Quizzes</a>
                    </div>
                </div>
            </div>

            <!-- Module Tests Card -->
            <div class="feature-card">
                <div class="feature-icon-wrapper icon-test">🎯</div>
                <div class="feature-info">
                    <h3>Module Tests</h3>
                    <p>Comprehensive end-of-module assessment exams to earn certification.</p>
                    <span class="badge-pill">${summary.totalTests} Tests &bull; Part 2</span>
                    <div style="margin-top: 12px;">
                        <a href="<c:url value='/tests'/>" class="btn btn-outline" style="font-size: 0.8rem; padding: 4px 10px;">View Tests</a>
                    </div>
                </div>
            </div>

            <!-- Progress Card -->
            <div class="feature-card">
                <div class="feature-icon-wrapper icon-progress">📊</div>
                <div class="feature-info">
                    <h3>Overall Progress</h3>
                    <p>Track completed lessons, quiz scores, and module milestones.</p>
                    <div class="progress-bar-container">
                        <div class="progress-bar-fill" style="width: ${summary.overallProgressPercent}%;"></div>
                    </div>
                    <span class="badge-pill">${summary.overallProgressPercent}% Complete</span>
                    <div style="margin-top: 8px;">
                        <a href="<c:url value='/progress'/>" class="btn btn-outline" style="font-size: 0.8rem; padding: 4px 10px;">Track Progress</a>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<jsp:include page="../common/footer.jsp" />

