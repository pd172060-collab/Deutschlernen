package com.example.germanlearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.germanlearning.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuizId(Long quizId);
    List<Question> findByTestId(Long testId);
    List<Question> findByModuleId(Long moduleId);
    List<Question> findByTopicId(Long topicId);
    List<Question> findByModuleIdAndTopicId(Long moduleId, Long topicId);
    List<Question> findByQuestionType(String questionType);
    boolean existsByTopicIdAndQuestionText(Long topicId, String questionText);
}
