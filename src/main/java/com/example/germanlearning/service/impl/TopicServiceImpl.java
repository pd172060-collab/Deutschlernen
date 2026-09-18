package com.example.germanlearning.service.impl;

import com.example.germanlearning.dto.LessonDTO;
import com.example.germanlearning.dto.TopicDTO;
import com.example.germanlearning.entity.Topic;
import com.example.germanlearning.exception.ResourceNotFoundException;
import com.example.germanlearning.repository.TopicRepository;
import com.example.germanlearning.service.TopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public List<TopicDTO> getTopicsByModuleId(Long moduleId) {
        return topicRepository.findByModuleIdOrderByOrderIndexAsc(moduleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TopicDTO getTopicById(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with ID: " + topicId));
        return convertToDTO(topic);
    }

    @Override
    public long countTopics() {
        return topicRepository.count();
    }

    private TopicDTO convertToDTO(Topic topic) {
        TopicDTO dto = new TopicDTO(
                topic.getId(),
                topic.getModule() != null ? topic.getModule().getId() : null,
                topic.getTitle(),
                topic.getGermanTitle(),
                topic.getDescription(),
                topic.getOrderIndex()
        );
        dto.setLessonCount(topic.getLessons() != null ? topic.getLessons().size() : 0);
        dto.setQuizCount(topic.getQuizzes() != null ? topic.getQuizzes().size() : 0);
        if (topic.getModule() != null) {
            dto.setModuleTitle(topic.getModule().getTitle());
        }
        if (topic.getLessons() != null) {
            List<LessonDTO> lessonDTOs = topic.getLessons().stream()
                    .map(l -> {
                        LessonDTO lDto = new LessonDTO(
                                l.getId(),
                                topic.getId(),
                                l.getTitle(),
                                l.getGermanTitle(),
                                l.getDescription(),
                                l.getOrderIndex(),
                                l.getEstimatedMinutes(),
                                l.getSlides() != null ? l.getSlides().size() : 0
                        );
                        lDto.setTopicTitle(topic.getTitle());
                        if (topic.getModule() != null) {
                            lDto.setModuleId(topic.getModule().getId());
                            lDto.setModuleTitle(topic.getModule().getTitle());
                        }
                        return lDto;
                    })
                    .collect(Collectors.toList());
            dto.setLessons(lessonDTOs);
        }
        return dto;
    }
}

