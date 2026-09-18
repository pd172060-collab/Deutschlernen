package com.example.germanlearning.repository;

import com.example.germanlearning.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findAllByOrderByOrderIndexAsc();
    Optional<Module> findByCode(String code);

    @Query("SELECT m FROM Module m LEFT JOIN FETCH m.topics WHERE m.id = :id")
    Optional<Module> findByIdWithTopics(Long id);
}

