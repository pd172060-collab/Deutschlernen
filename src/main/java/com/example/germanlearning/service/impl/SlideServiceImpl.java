package com.example.germanlearning.service.impl;

import com.example.germanlearning.dto.SlideDTO;
import com.example.germanlearning.entity.Lesson;
import com.example.germanlearning.entity.Slide;
import com.example.germanlearning.exception.ResourceNotFoundException;
import com.example.germanlearning.repository.LessonRepository;
import com.example.germanlearning.repository.SlideRepository;
import com.example.germanlearning.service.SlideService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideRepository;
    private final LessonRepository lessonRepository;

    public SlideServiceImpl(SlideRepository slideRepository, LessonRepository lessonRepository) {
        this.slideRepository = slideRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<SlideDTO> getSlidesByLessonId(Long lessonId) {
        int total = (int) slideRepository.countByLessonId(lessonId);
        return slideRepository.findByLessonIdOrderBySlideOrderAsc(lessonId).stream()
                .map(s -> convertToDTO(s, total))
                .collect(Collectors.toList());
    }

    @Override
    public SlideDTO getSlideById(Long slideId) {
        Slide slide = slideRepository.findById(slideId)
                .orElseThrow(() -> new ResourceNotFoundException("Slide not found with ID: " + slideId));
        int total = (int) slideRepository.countByLessonId(slide.getLesson().getId());
        return convertToDTO(slide, total);
    }

    @Override
    public SlideDTO getSlideByLessonAndOrder(Long lessonId, Integer slideOrder) {
        Slide slide = slideRepository.findByLessonIdAndSlideOrder(lessonId, slideOrder)
                .orElseThrow(() -> new ResourceNotFoundException("Slide " + slideOrder + " not found in Lesson " + lessonId));
        int total = (int) slideRepository.countByLessonId(lessonId);
        return convertToDTO(slide, total);
    }

    @Override
    public long countSlidesByLessonId(Long lessonId) {
        return slideRepository.countByLessonId(lessonId);
    }

    @Override
    public SlideDTO getFirstSlideOfLesson(Long lessonId) {
        return getSlideByLessonAndOrder(lessonId, 1);
    }

    private SlideDTO convertToDTO(Slide slide, int totalSlides) {
        SlideDTO dto = new SlideDTO();
        dto.setId(slide.getId());
        dto.setSlideOrder(slide.getSlideOrder());
        dto.setTitle(slide.getTitle());
        dto.setGermanText(slide.getGermanText());
        dto.setEnglishTranslation(slide.getEnglishTranslation());
        dto.setExplanation(slide.getExplanation());
        dto.setContentType(slide.getContentType() != null ? slide.getContentType() : "CONCEPT");
        dto.setVocabulary(slide.getVocabulary());
        dto.setGrammarRule(slide.getGrammarRule());
        dto.setExamples(slide.getExamples());
        dto.setImportantNote(slide.getImportantNote());

        Lesson lesson = slide.getLesson();
        if (lesson != null) {
            dto.setLessonId(lesson.getId());
            dto.setLessonTitle(lesson.getTitle());
            if (lesson.getTopic() != null) {
                dto.setTopicId(lesson.getTopic().getId());
                dto.setTopicTitle(lesson.getTopic().getTitle());
                if (lesson.getTopic().getModule() != null) {
                    dto.setModuleId(lesson.getTopic().getModule().getId());
                    dto.setModuleTitle(lesson.getTopic().getModule().getTitle());
                }
            }
        }

        dto.setTotalSlides(totalSlides);
        boolean isFirst = slide.getSlideOrder() <= 1;
        boolean isLast = slide.getSlideOrder() >= totalSlides;

        dto.setFirstSlide(isFirst);
        dto.setLastSlide(isLast);
        dto.setHasPrevious(!isFirst);
        dto.setHasNext(!isLast);
        dto.setPreviousSlideOrder(isFirst ? null : slide.getSlideOrder() - 1);
        dto.setNextSlideOrder(isLast ? null : slide.getSlideOrder() + 1);

        int percent = totalSlides > 0 ? (int) Math.round(((double) slide.getSlideOrder() / totalSlides) * 100) : 100;
        dto.setProgressPercentage(percent);

        return dto;
    }
}

