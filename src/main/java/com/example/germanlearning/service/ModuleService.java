package com.example.germanlearning.service;

import java.util.List;

import com.example.germanlearning.dto.ModuleDTO;
import com.example.germanlearning.entity.Module;

public interface ModuleService {
    List<ModuleDTO> getAllModules();
    ModuleDTO getModuleById(Long id);
    Module getModuleEntityById(Long id);
    long countModules();
}

