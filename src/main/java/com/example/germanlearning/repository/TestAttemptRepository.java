package com.example.germanlearning.repository;

import com.example.germanlearning.entity.TestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {
    List<TestAttempt> findByUserIdOrderByCompletedAtDesc(Long userId);
    List<TestAttempt> findByUserIdAndTestId(Long userId, Long testId);
    long countByUserIdAndPassedTrue(Long userId);
}

