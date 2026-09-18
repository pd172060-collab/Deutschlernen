package com.example.germanlearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.germanlearning.dto.DashboardSummaryDTO;
import com.example.germanlearning.service.DashboardService;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        DashboardSummaryDTO summary = dashboardService.getDashboardSummary();
        model.addAttribute("summary", summary);
        model.addAttribute("activeNav", "dashboard");
        return "dashboard/index";
    }



    @GetMapping("/progress")
    public String progress(Model model) {
        DashboardSummaryDTO summary = dashboardService.getDashboardSummary();
        model.addAttribute("summary", summary);
        model.addAttribute("activeNav", "progress");
        return "progress/index";
    }
}
