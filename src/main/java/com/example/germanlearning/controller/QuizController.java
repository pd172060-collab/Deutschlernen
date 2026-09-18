package com.example.germanlearning.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.germanlearning.dto.QuizAttemptDTO;
import com.example.germanlearning.dto.QuizDTO;
import com.example.germanlearning.dto.QuizQuestionDTO;
import com.example.germanlearning.dto.QuizSessionDTO;
import com.example.germanlearning.dto.QuizSummaryDTO;
import com.example.germanlearning.service.QuizService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/quizzes")
public class QuizController {

    private static final String SESSION_QUIZ_KEY = "quizSession";
    private static final Long DEFAULT_USER_ID = 1L;

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Display topic quizzes catalog.
     */
    @GetMapping({"", "/"})
    public String listQuizzes(Model model) {
        List<QuizDTO> quizzes = quizService.getAllTopicQuizzes(DEFAULT_USER_ID);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("activeNav", "quizzes");
        return "quizzes/quiz-list";
    }

    /**
     * Start a quiz session for a specific topic.
     */
    @GetMapping("/topic/{topicId}/start")
    public String startTopicQuiz(@PathVariable("topicId") Long topicId, HttpSession session) {
        QuizSessionDTO quizSession = quizService.startTopicQuiz(topicId, DEFAULT_USER_ID);
        session.setAttribute(SESSION_QUIZ_KEY, quizSession);
        return "redirect:/quizzes/question";
    }

    /**
     * Start a quiz session by Quiz ID.
     */
    @GetMapping("/start/{quizId}")
    public String startQuiz(@PathVariable("quizId") Long quizId, HttpSession session) {
        QuizSessionDTO quizSession = quizService.startQuiz(quizId, DEFAULT_USER_ID);
        session.setAttribute(SESSION_QUIZ_KEY, quizSession);
        return "redirect:/quizzes/question";
    }

    /**
     * Interactive question page.
     */
    @GetMapping("/question")
    public String viewQuestion(HttpSession session, Model model) {
        QuizSessionDTO quizSession = (QuizSessionDTO) session.getAttribute(SESSION_QUIZ_KEY);
        if (quizSession == null) {
            return "redirect:/quizzes";
        }
        if (quizSession.isCompleted()) {
            return "redirect:/quizzes/result";
        }

        QuizQuestionDTO question = quizService.getCurrentQuestion(quizSession);
        if (question == null) {
            return "redirect:/quizzes/result";
        }

        model.addAttribute("quizSession", quizSession);
        model.addAttribute("question", question);
        model.addAttribute("activeNav", "quizzes");
        return "quizzes/quiz-question";
    }

    /**
     * Submit an answer for the current question.
     */
    @PostMapping("/answer")
    public String submitAnswer(
            @RequestParam("questionId") Long questionId,
            @RequestParam(value = "userAnswer", required = false, defaultValue = "") String userAnswer,
            HttpSession session) {
        QuizSessionDTO quizSession = (QuizSessionDTO) session.getAttribute(SESSION_QUIZ_KEY);
        if (quizSession == null) {
            return "redirect:/quizzes";
        }

        quizService.submitAnswer(quizSession, questionId, userAnswer);
        return "redirect:/quizzes/feedback";
    }

    /**
     * Display immediate feedback for the last submitted answer.
     */
    @GetMapping("/feedback")
    public String viewFeedback(HttpSession session, Model model) {
        QuizSessionDTO quizSession = (QuizSessionDTO) session.getAttribute(SESSION_QUIZ_KEY);
        if (quizSession == null || quizSession.getLastFeedback() == null) {
            return "redirect:/quizzes/question";
        }

        model.addAttribute("quizSession", quizSession);
        model.addAttribute("feedback", quizSession.getLastFeedback());
        model.addAttribute("activeNav", "quizzes");
        return "quizzes/quiz-feedback";
    }

    /**
     * Advance to the next question or results.
     */
    @GetMapping("/next")
    public String nextQuestion(HttpSession session) {
        QuizSessionDTO quizSession = (QuizSessionDTO) session.getAttribute(SESSION_QUIZ_KEY);
        if (quizSession == null) {
            return "redirect:/quizzes";
        }

        quizService.advanceToNextQuestion(quizSession);

        if (quizSession.isCompleted()) {
            return "redirect:/quizzes/result";
        }
        return "redirect:/quizzes/question";
    }

    /**
     * Display final quiz results and persist score.
     */
    @GetMapping("/result")
    public String viewResult(HttpSession session, Model model) {
        QuizSessionDTO quizSession = (QuizSessionDTO) session.getAttribute(SESSION_QUIZ_KEY);
        if (quizSession == null) {
            return "redirect:/quizzes";
        }

        QuizSummaryDTO summary = quizService.completeQuiz(quizSession, DEFAULT_USER_ID);
        // Clear active session once finished
        session.removeAttribute(SESSION_QUIZ_KEY);

        model.addAttribute("summary", summary);
        model.addAttribute("activeNav", "quizzes");
        return "quizzes/quiz-result";
    }

    /**
     * Display quiz history and past attempts.
     */
    @GetMapping("/history")
    public String viewHistory(Model model) {
        List<QuizAttemptDTO> history = quizService.getUserQuizHistory(DEFAULT_USER_ID);
        model.addAttribute("history", history);
        model.addAttribute("activeNav", "quizzes");
        return "quizzes/quiz-history";
    }
}

