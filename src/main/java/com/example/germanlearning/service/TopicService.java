package com.example.germanlearning.service;

import java.util.List;

import com.example.germanlearning.dto.TopicDTO;

public interface TopicService {
    List<TopicDTO> getTopicsByModuleId(Long moduleId);
    TopicDTO getTopicById(Long topicId);
    long countTopics();
}

