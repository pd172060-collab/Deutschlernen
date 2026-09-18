package com.example.germanlearning.service.impl;

import com.example.germanlearning.dto.LessonDTO;
import com.example.germanlearning.entity.Lesson;
import com.example.germanlearning.exception.ResourceNotFoundException;
import com.example.germanlearning.repository.LessonRepository;
import com.example.germanlearning.service.LessonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<LessonDTO> getLessonsByTopicId(Long topicId) {
        return lessonRepository.findByTopicIdOrderByOrderIndexAsc(topicId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDTO getLessonById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with ID: " + lessonId));
        return convertToDTO(lesson);
    }

    @Override
    public long countLessons() {
        return lessonRepository.count();
    }



    private LessonDTO convertToDTO(Lesson lesson) {
        LessonDTO dto = new LessonDTO(
                lesson.getId(),
                lesson.getTopic() != null ? lesson.getTopic().getId() : null,
                lesson.getTitle(),
                lesson.getGermanTitle(),
                lesson.getDescription(),
                lesson.getOrderIndex(),
                lesson.getEstimatedMinutes(),
                lesson.getSlides() != null ? lesson.getSlides().size() : 0
        );
        if (lesson.getTopic() != null) {
            dto.setTopicTitle(lesson.getTopic().getTitle());
            if (lesson.getTopic().getModule() != null) {
                dto.setModuleId(lesson.getTopic().getModule().getId());
                dto.setModuleTitle(lesson.getTopic().getModule().getTitle());
            }
        }
        return dto;
    }
}

