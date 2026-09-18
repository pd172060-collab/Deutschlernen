package com.example.germanlearning;

import com.example.germanlearning.dto.SlideDTO;
import com.example.germanlearning.entity.UserProgress;
import com.example.germanlearning.service.SlideService;
import com.example.germanlearning.service.UserProgressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SlideServiceTest {

    @Autowired
    private SlideService slideService;

    @Autowired
    private UserProgressService userProgressService;

    @Test
    @DisplayName("Verify slide retrieval and navigation calculations for Lesson 1")
    void testSlideNavigationCalculations() {
        List<SlideDTO> slides = slideService.getSlidesByLessonId(1L);
        assertNotNull(slides);
        assertEquals(2, slides.size());

        // Test First Slide
        SlideDTO firstSlide = slideService.getSlideByLessonAndOrder(1L, 1);
        assertNotNull(firstSlide);
        assertTrue(firstSlide.isFirstSlide());
        assertFalse(firstSlide.isLastSlide());
        assertFalse(firstSlide.isHasPrevious());
        assertTrue(firstSlide.isHasNext());
        assertEquals(2, firstSlide.getNextSlideOrder());
        assertNull(firstSlide.getPreviousSlideOrder());
        assertEquals(50, firstSlide.getProgressPercentage()); // 1/2 = 50%

        // Test Last Slide
        SlideDTO lastSlide = slideService.getSlideByLessonAndOrder(1L, 2);
        assertNotNull(lastSlide);
        assertFalse(lastSlide.isFirstSlide());
        assertTrue(lastSlide.isLastSlide());
        assertTrue(lastSlide.isHasPrevious());
        assertFalse(lastSlide.isHasNext());
        assertEquals(1, lastSlide.getPreviousSlideOrder());
        assertNull(lastSlide.getNextSlideOrder());
        assertEquals(100, lastSlide.getProgressPercentage()); // 2/2 = 100%
    }

    @Test
    @DisplayName("Verify slide content fields (German text, translation, grammar, vocabulary, examples)")
    void testSlideEducationalFields() {
        SlideDTO slide = slideService.getSlideByLessonAndOrder(1L, 1);
        assertNotNull(slide.getTitle());
        assertNotNull(slide.getGermanText());
        assertNotNull(slide.getEnglishTranslation());
        assertNotNull(slide.getExplanation());
        assertNotNull(slide.getVocabulary());
        assertNotNull(slide.getGrammarRule());
        assertNotNull(slide.getExamples());
        assertNotNull(slide.getImportantNote());
    }

    @Test
    @DisplayName("Verify lesson progress recording and completion")
    void testLessonCompletion() {
        // Initial state
        assertFalse(userProgressService.isLessonCompleted(1L, 1L));

        // Record progress
        UserProgress inProgress = userProgressService.recordLessonProgress(1L, 1L, 1, false);
        assertEquals("IN_PROGRESS", inProgress.getStatus());

        // Mark completed
        UserProgress completed = userProgressService.markLessonCompleted(1L, 1L);
        assertEquals("COMPLETED", completed.getStatus());
        assertTrue(userProgressService.isLessonCompleted(1L, 1L));
    }

    @Autowired
    private com.example.germanlearning.repository.ModuleRepository moduleRepository;

    @Test
    @DisplayName("Verify Module III structure: 7 topics, lessons, and slides populated from mod3.pdf")
    void testModule3Structure() {
        var mod3Opt = moduleRepository.findByCode("MODULE_3");
        assertTrue(mod3Opt.isPresent());
        var mod3 = mod3Opt.get();
        assertEquals(7, mod3.getTopics().size());
        assertEquals("Module III: Time, Prepositions, Modal Verbs, Professions, Health & Perfect Tense", mod3.getTitle());

        // Verify Topic 1: Uhrzeit & Termine
        var topic1 = mod3.getTopics().get(0);
        assertTrue(topic1.getTitle().contains("Uhrzeit & Termine"));
        assertFalse(topic1.getLessons().isEmpty());

        // Verify Topic 2: Präpositionen
        var topic2 = mod3.getTopics().get(1);
        assertTrue(topic2.getTitle().contains("Präpositionen"));

        // Verify Topic 3: Modalverben
        var topic3 = mod3.getTopics().get(2);
        assertTrue(topic3.getTitle().contains("Modalverben"));

        // Verify Topic 4: Berufe & Arbeitsplatz
        var topic4 = mod3.getTopics().get(3);
        assertTrue(topic4.getTitle().contains("Berufe & Arbeitsplatz"));

        // Verify Topic 5: Gesundheit, Körperteile & Gefühle
        var topic5 = mod3.getTopics().get(4);
        assertTrue(topic5.getTitle().contains("Gesundheit"));

        // Verify Topic 6: Das Perfekt
        var topic6 = mod3.getTopics().get(5);
        assertTrue(topic6.getTitle().contains("Perfekt"));

        // Verify Topic 7: Aktivitäten
        var topic7 = mod3.getTopics().get(6);
        assertTrue(topic7.getTitle().contains("Aktivitäten"));

        // Check slide content on a Module 3 lesson
        var firstLesson = topic1.getLessons().get(0);
        assertNotNull(firstLesson.getId());
        List<SlideDTO> slides = slideService.getSlidesByLessonId(firstLesson.getId());
        assertFalse(slides.isEmpty());
        SlideDTO firstSlide = slides.get(0);
        assertNotNull(firstSlide.getGermanText());
        assertNotNull(firstSlide.getEnglishTranslation());
        assertNotNull(firstSlide.getGrammarRule());
    }

    @Test
    @DisplayName("Verify Module IV structure: 6 topics, lessons, and slides populated from mod4.pdf")
    void testModule4Structure() {
        var mod4Opt = moduleRepository.findByCode("MODULE_4");
        assertTrue(mod4Opt.isPresent());
        var mod4 = mod4Opt.get();
        assertEquals(6, mod4.getTopics().size());
        assertEquals("Module IV: Free Time, Holidays & Essential Grammar", mod4.getTitle());

        // Verify Topic 1: Freizeit & Hobbys
        var topic1 = mod4.getTopics().get(0);
        assertTrue(topic1.getTitle().contains("Freizeit & Hobbys"));
        assertFalse(topic1.getLessons().isEmpty());

        // Verify Topic 2: Trennbare Verben
        var topic2 = mod4.getTopics().get(1);
        assertTrue(topic2.getTitle().contains("Trennbare Verben"));

        // Verify Topic 3: Der Imperativ
        var topic3 = mod4.getTopics().get(2);
        assertTrue(topic3.getTitle().contains("Imperativ"));

        // Verify Topic 4: Modalverben
        var topic4 = mod4.getTopics().get(3);
        assertTrue(topic4.getTitle().contains("Modalverben"));

        // Verify Topic 5: Reisen, Wetter, Feste & Feiertage
        var topic5 = mod4.getTopics().get(4);
        assertTrue(topic5.getTitle().contains("Reisen"));

        // Verify Topic 6: Wiederholung & Grammatik-Übersicht
        var topic6 = mod4.getTopics().get(5);
        assertTrue(topic6.getTitle().contains("Wiederholung"));

        // Check slide content on a Module 4 lesson (e.g. Topic 1 Lesson 1)
        var firstLesson = topic1.getLessons().get(0);
        assertNotNull(firstLesson.getId());
        List<SlideDTO> slides = slideService.getSlidesByLessonId(firstLesson.getId());
        assertFalse(slides.isEmpty());
        SlideDTO firstSlide = slides.get(0);
        assertNotNull(firstSlide.getGermanText());
        assertNotNull(firstSlide.getEnglishTranslation());
        assertNotNull(firstSlide.getVocabulary());
        assertNotNull(firstSlide.getGrammarRule());
    }
}

