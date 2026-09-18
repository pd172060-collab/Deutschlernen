package com.example.germanlearning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.germanlearning.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByModuleId(Long moduleId);
}

