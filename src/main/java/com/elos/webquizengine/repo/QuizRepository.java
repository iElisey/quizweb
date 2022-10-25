package com.elos.webquizengine.repo;

import com.elos.webquizengine.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface QuizRepository extends CrudRepository<Quiz, Long> {
    Page<Quiz> findAll(Pageable pageable);

    List<Quiz> findQuizzesByDateBetweenOrderByDateDesc(LocalDateTime first, LocalDateTime second);
}