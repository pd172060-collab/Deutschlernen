package com.example.germanlearning.controller;

import com.example.germanlearning.dto.ModuleDTO;
import com.example.germanlearning.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public String listModules(Model model) {
        List<ModuleDTO> modules = moduleService.getAllModules();
        model.addAttribute("modules", modules);
        model.addAttribute("activeNav", "modules");
        return "modules/list";
    }

    @GetMapping("/{id}")
    public String moduleDetails(@PathVariable("id") Long id, Model model) {
        ModuleDTO module = moduleService.getModuleById(id);
        model.addAttribute("module", module);
        model.addAttribute("activeNav", "modules");
        return "modules/details";
    }

    @GetMapping("/{id}/topics")
    public String moduleTopics(@PathVariable("id") Long id, Model model) {
        return moduleDetails(id, model);
    }
}

