<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Question ${question.questionIndex} of ${question.totalQuestions} - Topic Quiz" />
<jsp:include page="../common/header.jsp" />
<jsp:include page="../common/navbar.jsp" />

<main class="main-content">
    <div class="slide-viewer-wrapper" style="max-width: 800px; margin: 0 auto;">
        
        <!-- Breadcrumb & Header Navigation -->
        <div class="breadcrumb" style="margin-bottom: 16px;">
            <a href="<c:url value='/dashboard'/>">Dashboard</a>
            <span>&rsaquo;</span>
            <a href="<c:url value='/quizzes'/>">Topic Quizzes</a>
            <span>&rsaquo;</span>
            <span>${quizSession.topicTitle}</span>
        </div>

        <!-- Quiz Container Card -->
        <div class="slide-viewer-card">
            
            <!-- Top Progress Header -->
            <div class="slide-top-header">
                <div class="slide-lesson-info">
                    <span class="slide-lesson-tag">Topic Quiz &bull; ${quizSession.moduleTitle}</span>
                    <span class="slide-lesson-title">${quizSession.topicTitle}</span>
                </div>
                <div class="slide-counter-badge">
                    Question ${question.questionIndex} / ${question.totalQuestions}
                </div>
            </div>

            <!-- Progress Track Bar -->
            <div class="slide-progress-track">
                <div class="slide-progress-bar" style="width: ${(question.questionIndex * 100) / question.totalQuestions}%;"></div>
            </div>

            <!-- Main Question Body -->
            <div class="slide-body-content" style="padding: 36px;">
                
                <div class="slide-title-area" style="margin-bottom: 20px;">
                    <span class="badge-pill" style="font-size: 0.8rem; padding: 4px 12px; background: #EEF2FF; color: #4338CA; font-weight: 700;">
                        ${question.questionType}
                    </span>
                    <span class="badge-pill" style="font-size: 0.8rem; padding: 4px 12px; background: #FEF3C7; color: #B45309; font-weight: 700;">
                        ⭐ ${question.points} ${question.points == 1 ? 'Point' : 'Points'}
                    </span>
                </div>

                <!-- German Question Prompt -->
                <div class="german-core-card" style="margin-bottom: 28px; background: linear-gradient(135deg, #1E293B 0%, #0F172A 100%);">
                    <div class="german-core-label">
                        <span>❓ Question</span>
                    </div>
                    <div class="german-core-text" style="font-size: 1.3rem;">
                        ${question.questionText}
                    </div>
                </div>

                <!-- Answer Submission Form -->
                <form id="quizAnswerForm" action="<c:url value='/quizzes/answer'/>" method="POST">
                    <input type="hidden" name="questionId" value="${question.id}" />

                    <c:choose>
                        <%-- MULTIPLE CHOICE / TRUE FALSE --%>
                        <c:when test="${question.questionType == 'MULTIPLE_CHOICE' || question.questionType == 'TRUE_FALSE'}">
                            <div style="display: flex; flex-direction: column; gap: 12px; margin-bottom: 28px;">
                                <c:if test="${not empty question.optionA}">
                                    <label class="quiz-option-item" style="display: flex; align-items: center; gap: 14px; padding: 14px 20px; border: 2px solid var(--border-color); border-radius: var(--radius-md); cursor: pointer; transition: var(--transition);">
                                        <input type="radio" name="userAnswer" value="A" required style="accent-color: var(--accent-blue); width: 18px; height: 18px;" />
                                        <span style="font-weight: 700; color: var(--primary); min-width: 24px;">A.</span>
                                        <span style="font-size: 1.05rem; color: var(--text-main);">${question.optionA}</span>
                                    </label>
                                </c:if>

                                <c:if test="${not empty question.optionB}">
                                    <label class="quiz-option-item" style="display: flex; align-items: center; gap: 14px; padding: 14px 20px; border: 2px solid var(--border-color); border-radius: var(--radius-md); cursor: pointer; transition: var(--transition);">
                                        <input type="radio" name="userAnswer" value="B" required style="accent-color: var(--accent-blue); width: 18px; height: 18px;" />
                                        <span style="font-weight: 700; color: var(--primary); min-width: 24px;">B.</span>
                                        <span style="font-size: 1.05rem; color: var(--text-main);">${question.optionB}</span>
                                    </label>
                                </c:if>

                                <c:if test="${not empty question.optionC}">
                                    <label class="quiz-option-item" style="display: flex; align-items: center; gap: 14px; padding: 14px 20px; border: 2px solid var(--border-color); border-radius: var(--radius-md); cursor: pointer; transition: var(--transition);">
                                        <input type="radio" name="userAnswer" value="C" required style="accent-color: var(--accent-blue); width: 18px; height: 18px;" />
                                        <span style="font-weight: 700; color: var(--primary); min-width: 24px;">C.</span>
                                        <span style="font-size: 1.05rem; color: var(--text-main);">${question.optionC}</span>
                                    </label>
                                </c:if>

                                <c:if test="${not empty question.optionD}">
                                    <label class="quiz-option-item" style="display: flex; align-items: center; gap: 14px; padding: 14px 20px; border: 2px solid var(--border-color); border-radius: var(--radius-md); cursor: pointer; transition: var(--transition);">
                                        <input type="radio" name="userAnswer" value="D" required style="accent-color: var(--accent-blue); width: 18px; height: 18px;" />
                                        <span style="font-weight: 700; color: var(--primary); min-width: 24px;">D.</span>
                                        <span style="font-size: 1.05rem; color: var(--text-main);">${question.optionD}</span>
                                    </label>
                                </c:if>
                            </div>
                        </c:when>

                        <%-- TEXT INPUT (Fill-in-the-blank, Translation, Verb Conjugation, Article Practice, Sentence Completion) --%>
                        <c:otherwise>
                            <div style="margin-bottom: 28px;">
                                <label for="textAnswerInput" style="display: block; font-size: 0.9rem; font-weight: 700; color: var(--primary); margin-bottom: 8px;">
                                    Your Answer:
                                </label>
                                
                                <input type="text" id="textAnswerInput" name="userAnswer" required autocomplete="off" autofocus
                                       placeholder="Type your German answer here..."
                                       style="width: 100%; padding: 14px 18px; font-size: 1.1rem; border: 2px solid var(--border-color); border-radius: var(--radius-md); outline: none; transition: var(--transition);" />

                                <!-- German Umlaut Toolbar -->
                                <div style="margin-top: 12px; display: flex; align-items: center; gap: 8px; flex-wrap: wrap;">
                                    <span style="font-size: 0.8rem; color: var(--text-muted); font-weight: 600;">German Characters:</span>
                                    <button type="button" class="btn btn-outline umlaut-btn" onclick="insertChar('ä')">ä</button>
                                    <button type="button" class="btn btn-outline umlaut-btn" onclick="insertChar('ö')">ö</button>
                                    <button type="button" class="btn btn-outline umlaut-btn" onclick="insertChar('ü')">ü</button>
                                    <button type="button" class="btn btn-outline umlaut-btn" onclick="insertChar('ß')">ß</button>
                                    <button type="button" class="btn btn-outline umlaut-btn" onclick="insertChar('Ä')">Ä</button>
                                    <button type="button" class="btn btn-outline umlaut-btn" onclick="insertChar('Ö')">Ö</button>
                                    <button type="button" class="btn btn-outline umlaut-btn" onclick="insertChar('Ü')">Ü</button>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                    <!-- Bottom Action Controls -->
                    <div style="display: flex; justify-content: space-between; align-items: center; padding-top: 20px; border-top: 1px solid var(--border-color);">
                        <a href="<c:url value='/quizzes'/>" class="btn btn-outline" style="color: var(--text-muted);" onclick="return confirm('Are you sure you want to exit this quiz? Current progress will not be saved.');">
                            &larr; Exit Quiz
                        </a>

                        <button type="submit" class="btn btn-primary" style="padding: 12px 28px; font-size: 1.05rem; font-weight: 700;">
                            Submit Answer &rarr;
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </div>
</main>

<style>
    .quiz-option-item:hover {
        border-color: var(--accent-blue) !important;
        background-color: #F8FAFC;
    }
    .quiz-option-item input[type="radio"]:checked + span {
        color: var(--accent-blue);
    }
    .umlaut-btn {
        padding: 4px 10px !important;
        font-size: 0.95rem !important;
        font-weight: 700 !important;
        min-width: 32px;
        background: #F1F5F9 !important;
        border-color: #CBD5E1 !important;
        color: var(--primary) !important;
    }
    .umlaut-btn:hover {
        background: #E2E8F0 !important;
        border-color: #94A3B8 !important;
    }
    #textAnswerInput:focus {
        border-color: var(--accent-blue) !important;
        box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
    }
</style>

<script>
    function insertChar(char) {
        const input = document.getElementById('textAnswerInput');
        if (!input) return;
        const start = input.selectionStart || input.value.length;
        const end = input.selectionEnd || input.value.length;
        const text = input.value;
        input.value = text.substring(0, start) + char + text.substring(end);
        input.focus();
        input.setSelectionRange(start + char.length, start + char.length);
    }
</script>

<jsp:include page="../common/footer.jsp" />

