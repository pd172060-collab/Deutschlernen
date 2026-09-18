package com.example.germanlearning.controller;

import com.example.germanlearning.dto.TopicDTO;
import com.example.germanlearning.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/{id}")
    public String topicDetails(@PathVariable("id") Long id, Model model) {
        TopicDTO topic = topicService.getTopicById(id);
        model.addAttribute("topic", topic);
        model.addAttribute("activeNav", "modules");
        return "topics/details";
    }

    @GetMapping("/{id}/lessons")
    public String topicLessons(@PathVariable("id") Long id, Model model) {
        return topicDetails(id, model);
    }
}

