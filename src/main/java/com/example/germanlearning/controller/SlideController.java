package com.example.germanlearning.controller;

import com.example.germanlearning.dto.SlideDTO;
import com.example.germanlearning.service.SlideService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/slides")
public class SlideController {

    private final SlideService slideService;

    public SlideController(SlideService slideService) {
        this.slideService = slideService;
    }

    @GetMapping("/{id}")
    public String viewSlideById(@PathVariable("id") Long id) {
        SlideDTO slide = slideService.getSlideById(id);
        return "redirect:/lessons/" + slide.getLessonId() + "/slides/" + slide.getSlideOrder();
    }
}

