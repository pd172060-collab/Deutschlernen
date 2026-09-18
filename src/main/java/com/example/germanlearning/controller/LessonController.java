package com.example.germanlearning.controller;

import com.example.germanlearning.dto.LessonDTO;
import com.example.germanlearning.dto.SlideDTO;
import com.example.germanlearning.service.LessonService;
import com.example.germanlearning.service.SlideService;
import com.example.germanlearning.service.UserProgressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final SlideService slideService;
    private final UserProgressService userProgressService;

    public LessonController(
            LessonService lessonService,
            SlideService slideService,
            UserProgressService userProgressService) {
        this.lessonService = lessonService;
        this.slideService = slideService;
        this.userProgressService = userProgressService;
    }

    @GetMapping("/{id}")
    public String lessonDetails(
            @PathVariable("id") Long id,
            @RequestParam(value = "completed", required = false) Boolean completed,
            Model model) {
        LessonDTO lesson = lessonService.getLessonById(id);
        boolean isCompleted = userProgressService.isLessonCompleted(1L, id);
        lesson.setCompleted(isCompleted);

        model.addAttribute("lesson", lesson);
        model.addAttribute("justCompleted", Boolean.TRUE.equals(completed));
        model.addAttribute("activeNav", "modules");
        return "lessons/details";
    }

    @GetMapping("/{id}/slides")
    public String startLessonSlides(@PathVariable("id") Long id) {
        return "redirect:/lessons/" + id + "/slides/1";
    }

    @GetMapping("/{id}/slides/{slideOrder}")
    public String viewSlide(
            @PathVariable("id") Long id,
            @PathVariable("slideOrder") Integer slideOrder,
            Model model) {
        SlideDTO slide = slideService.getSlideByLessonAndOrder(id, slideOrder);
        // Track slide progress
        userProgressService.recordLessonProgress(1L, id, slideOrder, false);

        model.addAttribute("slide", slide);
        model.addAttribute("activeNav", "modules");
        return "lessons/slide-viewer";
    }

    @PostMapping("/{id}/complete")
    public String completeLessonPost(@PathVariable("id") Long id) {
        userProgressService.markLessonCompleted(1L, id);
        return "redirect:/lessons/" + id + "?completed=true";
    }

    @GetMapping("/{id}/complete")
    public String completeLessonGet(@PathVariable("id") Long id) {
        userProgressService.markLessonCompleted(1L, id);
        return "redirect:/lessons/" + id + "?completed=true";
    }
}

