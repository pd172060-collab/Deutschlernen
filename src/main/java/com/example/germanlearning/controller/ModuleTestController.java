package com.example.germanlearning.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.germanlearning.dto.ModuleTestAttemptDTO;
import com.example.germanlearning.dto.ModuleTestDTO;
import com.example.germanlearning.dto.ModuleTestQuestionDTO;
import com.example.germanlearning.dto.ModuleTestSessionDTO;
import com.example.germanlearning.dto.ModuleTestSummaryDTO;
import com.example.germanlearning.service.ModuleTestService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tests")
public class ModuleTestController {

    private static final String SESSION_TEST_KEY = "moduleTestSession";
    private static final Long DEFAULT_USER_ID = 1L;

    private final ModuleTestService moduleTestService;

    public ModuleTestController(ModuleTestService moduleTestService) {
        this.moduleTestService = moduleTestService;
    }

    /**
     * Display all Module Tests catalog.
     */
    @GetMapping({"", "/"})
    public String listTests(Model model) {
        List<ModuleTestDTO> tests = moduleTestService.getAllModuleTests(DEFAULT_USER_ID);
        model.addAttribute("tests", tests);
        model.addAttribute("activeNav", "tests");
        return "tests/test-list";
    }

    /**
     * Shortcut from Module Details page to start test.
     */
    @GetMapping("/module/{moduleId}")
    public String startByModule(@PathVariable("moduleId") Long moduleId) {
        ModuleTestDTO testDTO = moduleTestService.getModuleTestByModuleId(moduleId, DEFAULT_USER_ID);
        return "redirect:/tests/" + testDTO.getId() + "/start";
    }

    /**
     * Pre-test instruction & briefing page.
     */
    @GetMapping("/{testId}/start")
    public String testStartBriefing(@PathVariable("testId") Long testId, Model model) {
        ModuleTestDTO test = moduleTestService.getModuleTestById(testId, DEFAULT_USER_ID);
        model.addAttribute("test", test);
        model.addAttribute("activeNav", "tests");
        return "tests/test-start";
    }

    /**
     * Initialize test session in HttpSession and begin the first question.
     */
    @RequestMapping(value = "/{testId}/begin", method = {RequestMethod.GET, RequestMethod.POST})
    public String beginTest(@PathVariable("testId") Long testId, HttpSession session) {
        ModuleTestSessionDTO testSession = moduleTestService.startModuleTest(testId, DEFAULT_USER_ID);
        session.setAttribute(SESSION_TEST_KEY, testSession);
        return "redirect:/tests/" + testId + "/question";
    }

    /**
     * Active question viewer for module test.
     */
    @GetMapping("/{testId}/question")
    public String viewQuestion(@PathVariable("testId") Long testId, HttpSession session, Model model) {
        ModuleTestSessionDTO testSession = (ModuleTestSessionDTO) session.getAttribute(SESSION_TEST_KEY);
        if (testSession == null || !testId.equals(testSession.getTestId())) {
            return "redirect:/tests/" + testId + "/start";
        }
        if (testSession.isCompleted()) {
            return "redirect:/tests/" + testId + "/result";
        }

        ModuleTestQuestionDTO question = moduleTestService.getCurrentQuestion(testSession);
        if (question == null) {
            return "redirect:/tests/" + testId + "/result";
        }

        model.addAttribute("testSession", testSession);
        model.addAttribute("question", question);
        model.addAttribute("activeNav", "tests");
        return "tests/test-question";
    }

    /**
     * Submit an answer for the active test question.
     */
    @PostMapping("/{testId}/answer")
    public String submitAnswer(
            @PathVariable("testId") Long testId,
            @RequestParam("questionId") Long questionId,
            @RequestParam(value = "userAnswer", required = false, defaultValue = "") String userAnswer,
            HttpSession session) {
        ModuleTestSessionDTO testSession = (ModuleTestSessionDTO) session.getAttribute(SESSION_TEST_KEY);
        if (testSession == null || !testId.equals(testSession.getTestId())) {
            return "redirect:/tests";
        }

        moduleTestService.submitAnswer(testSession, questionId, userAnswer);
        return "redirect:/tests/" + testId + "/feedback";
    }

    /**
     * View immediate feedback and explanations.
     */
    @GetMapping("/{testId}/feedback")
    public String viewFeedback(@PathVariable("testId") Long testId, HttpSession session, Model model) {
        ModuleTestSessionDTO testSession = (ModuleTestSessionDTO) session.getAttribute(SESSION_TEST_KEY);
        if (testSession == null || !testId.equals(testSession.getTestId()) || testSession.getLastFeedback() == null) {
            return "redirect:/tests/" + testId + "/question";
        }

        model.addAttribute("testSession", testSession);
        model.addAttribute("feedback", testSession.getLastFeedback());
        model.addAttribute("activeNav", "tests");
        return "tests/test-feedback";
    }

    /**
     * Advance to the next question or final results.
     */
    @GetMapping("/{testId}/next")
    public String nextQuestion(@PathVariable("testId") Long testId, HttpSession session) {
        ModuleTestSessionDTO testSession = (ModuleTestSessionDTO) session.getAttribute(SESSION_TEST_KEY);
        if (testSession == null || !testId.equals(testSession.getTestId())) {
            return "redirect:/tests";
        }

        moduleTestService.advanceToNextQuestion(testSession);

        if (testSession.isCompleted()) {
            return "redirect:/tests/" + testId + "/result";
        }
        return "redirect:/tests/" + testId + "/question";
    }

    /**
     * Final result overview and persistence.
     */
    @GetMapping("/{testId}/result")
    public String viewResult(@PathVariable("testId") Long testId, HttpSession session, Model model) {
        ModuleTestSessionDTO testSession = (ModuleTestSessionDTO) session.getAttribute(SESSION_TEST_KEY);
        if (testSession == null || !testId.equals(testSession.getTestId())) {
            return "redirect:/tests/" + testId + "/start";
        }

        ModuleTestSummaryDTO summary = moduleTestService.completeTest(testSession, DEFAULT_USER_ID);
        // Clear active test session upon completion
        session.removeAttribute(SESSION_TEST_KEY);

        model.addAttribute("summary", summary);
        model.addAttribute("activeNav", "tests");
        return "tests/test-result";
    }

    /**
     * View past module test attempts history.
     */
    @GetMapping("/history")
    public String viewHistory(Model model) {
        List<ModuleTestAttemptDTO> history = moduleTestService.getUserTestHistory(DEFAULT_USER_ID);
        model.addAttribute("history", history);
        model.addAttribute("activeNav", "tests");
        return "tests/test-history";
    }
}

