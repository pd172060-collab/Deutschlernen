package com.example.germanlearning;

import com.example.germanlearning.entity.Module;
import com.example.germanlearning.entity.Topic;
import com.example.germanlearning.repository.ModuleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ModuleRepositoryTest {

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    @DisplayName("Should find all seeded modules in order")
    void testFindAllModules() {
        List<Module> modules = moduleRepository.findAllByOrderByOrderIndexAsc();
        assertNotNull(modules);
        assertFalse(modules.isEmpty());
        assertEquals("MODULE_1", modules.get(0).getCode());
    }

    @Test
    @DisplayName("Should find module with topics by ID")
    void testFindByIdWithTopics() {
        List<Module> modules = moduleRepository.findAllByOrderByOrderIndexAsc();
        Long firstModuleId = modules.get(0).getId();

        Optional<Module> found = moduleRepository.findByIdWithTopics(firstModuleId);
        assertTrue(found.isPresent());
        assertNotNull(found.get().getTopics());
        assertFalse(found.get().getTopics().isEmpty());
    }
}

