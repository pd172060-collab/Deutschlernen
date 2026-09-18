package com.example.germanlearning.service.impl;

import com.example.germanlearning.dto.LessonDTO;
import com.example.germanlearning.dto.ModuleDTO;
import com.example.germanlearning.dto.TopicDTO;
import com.example.germanlearning.entity.Lesson;
import com.example.germanlearning.entity.Module;
import com.example.germanlearning.entity.Topic;
import com.example.germanlearning.exception.ResourceNotFoundException;
import com.example.germanlearning.repository.ModuleRepository;
import com.example.germanlearning.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public List<ModuleDTO> getAllModules() {
        return moduleRepository.findAllByOrderByOrderIndexAsc().stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModuleDTO getModuleById(Long id) {
        Module module = moduleRepository.findByIdWithTopics(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with ID: " + id));
        return convertToDetailedDTO(module);
    }

    @Override
    public Module getModuleEntityById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with ID: " + id));
    }

    @Override
    public long countModules() {
        return moduleRepository.count();
    }

    private ModuleDTO convertToSummaryDTO(Module module) {
        ModuleDTO dto = new ModuleDTO(
                module.getId(),
                module.getCode(),
                module.getTitle(),
                module.getGermanTitle(),
                module.getDescription(),
                module.getLevel(),
                module.getOrderIndex()
        );
        dto.setTopicCount(module.getTopics() != null ? module.getTopics().size() : 0);
        int totalLessons = 0;
        if (module.getTopics() != null) {
            for (Topic t : module.getTopics()) {
                if (t.getLessons() != null) {
                    totalLessons += t.getLessons().size();
                }
            }
        }
        dto.setLessonCount(totalLessons);
        dto.setTestCount(module.getTests() != null ? module.getTests().size() : 0);
        if (module.getTopics() != null) {
            List<TopicDTO> topicDTOs = module.getTopics().stream().map(topic -> new TopicDTO(
                    topic.getId(),
                    module.getId(),
                    topic.getTitle(),
                    topic.getGermanTitle(),
                    topic.getDescription(),
                    topic.getOrderIndex()
            )).collect(Collectors.toList());
            dto.setTopics(topicDTOs);
        }
        return dto;
    }

    private ModuleDTO convertToDetailedDTO(Module module) {
        ModuleDTO dto = convertToSummaryDTO(module);
        if (module.getTopics() != null) {
            List<TopicDTO> topicDTOs = module.getTopics().stream().map(topic -> {
                TopicDTO tDto = new TopicDTO(
                        topic.getId(),
                        module.getId(),
                        topic.getTitle(),
                        topic.getGermanTitle(),
                        topic.getDescription(),
                        topic.getOrderIndex()
                );
                tDto.setLessonCount(topic.getLessons() != null ? topic.getLessons().size() : 0);
                tDto.setQuizCount(topic.getQuizzes() != null ? topic.getQuizzes().size() : 0);

                if (topic.getLessons() != null) {
                    List<LessonDTO> lessonDTOs = topic.getLessons().stream().map(lesson ->
                            new LessonDTO(
                                    lesson.getId(),
                                    topic.getId(),
                                    lesson.getTitle(),
                                    lesson.getGermanTitle(),
                                    lesson.getDescription(),
                                    lesson.getOrderIndex(),
                                    lesson.getEstimatedMinutes(),
                                    lesson.getSlides() != null ? lesson.getSlides().size() : 0
                            )
                    ).collect(Collectors.toList());
                    tDto.setLessons(lessonDTOs);
                }
                return tDto;
            }).collect(Collectors.toList());
            dto.setTopics(topicDTOs);
        }
        return dto;
    }
}

