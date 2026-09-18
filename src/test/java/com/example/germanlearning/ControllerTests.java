package com.example.germanlearning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / redirects to /dashboard")
    void testRootRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    @DisplayName("GET /dashboard returns dashboard view with summary")
    void testDashboard() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard/index"))
                .andExpect(model().attributeExists("summary"));
    }

    @Test
    @DisplayName("GET /modules returns modules list view")
    void testModulesList() throws Exception {
        mockMvc.perform(get("/modules"))
                .andExpect(status().isOk())
                .andExpect(view().name("modules/list"))
                .andExpect(model().attributeExists("modules"));
    }

    @Test
    @DisplayName("GET /modules/1 returns module details view")
    void testModuleDetails() throws Exception {
        mockMvc.perform(get("/modules/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("modules/details"))
                .andExpect(model().attributeExists("module"));
    }

    @Test
    @DisplayName("GET /modules/1/topics returns module details view")
    void testModuleTopics() throws Exception {
        mockMvc.perform(get("/modules/1/topics"))
                .andExpect(status().isOk())
                .andExpect(view().name("modules/details"))
                .andExpect(model().attributeExists("module"));
    }

    @Test
    @DisplayName("GET /topics/1 returns topic details view")
    void testTopicDetails() throws Exception {
        mockMvc.perform(get("/topics/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("topics/details"))
                .andExpect(model().attributeExists("topic"));
    }

    @Test
    @DisplayName("GET /topics/1/lessons returns topic details view")
    void testTopicLessons() throws Exception {
        mockMvc.perform(get("/topics/1/lessons"))
                .andExpect(status().isOk())
                .andExpect(view().name("topics/details"))
                .andExpect(model().attributeExists("topic"));
    }

    @Test
    @DisplayName("GET /lessons/1 returns lesson details view")
    void testLessonDetails() throws Exception {
        mockMvc.perform(get("/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/details"))
                .andExpect(model().attributeExists("lesson"));
    }

    @Test
    @DisplayName("GET /lessons/1/slides redirects to /lessons/1/slides/1")
    void testLessonSlidesRedirect() throws Exception {
        mockMvc.perform(get("/lessons/1/slides"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lessons/1/slides/1"));
    }

    @Test
    @DisplayName("GET /lessons/1/slides/1 returns slide viewer with first slide state")
    void testSlideViewerFirstSlide() throws Exception {
        mockMvc.perform(get("/lessons/1/slides/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/slide-viewer"))
                .andExpect(model().attributeExists("slide"));
    }

    @Test
    @DisplayName("GET /lessons/1/slides/2 returns slide viewer with last slide state")
    void testSlideViewerLastSlide() throws Exception {
        mockMvc.perform(get("/lessons/1/slides/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("lessons/slide-viewer"))
                .andExpect(model().attributeExists("slide"));
    }

    @Test
    @DisplayName("POST /lessons/1/complete records completion and redirects")
    void testCompleteLessonPost() throws Exception {
        mockMvc.perform(post("/lessons/1/complete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/lessons/1?completed=true"));
    }

    @Test
    @DisplayName("GET /slides/1 redirects to /lessons/{lessonId}/slides/{slideOrder}")
    void testSlideByIdRedirect() throws Exception {
        mockMvc.perform(get("/slides/1"))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @DisplayName("GET /quizzes returns quizzes placeholder view")
    void testQuizzes() throws Exception {
        mockMvc.perform(get("/quizzes"))
                .andExpect(status().isOk())
                .andExpect(view().name("quizzes/index"));
    }

    @Test
    @DisplayName("GET /tests returns tests placeholder view")
    void testTests() throws Exception {
        mockMvc.perform(get("/tests"))
                .andExpect(status().isOk())
                .andExpect(view().name("tests/index"));
    }

    @Test
    @DisplayName("GET /progress returns progress placeholder view")
    void testProgress() throws Exception {
        mockMvc.perform(get("/progress"))
                .andExpect(status().isOk())
                .andExpect(view().name("progress/index"))
                .andExpect(model().attributeExists("summary"));
    }
}
