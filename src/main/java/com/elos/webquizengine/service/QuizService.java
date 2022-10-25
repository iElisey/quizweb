package com.elos.webquizengine.service;

import com.elos.webquizengine.dto.AnswerDto;
import com.elos.webquizengine.model.Complete;
import com.elos.webquizengine.model.Quiz;
import com.elos.webquizengine.model.User;
import com.elos.webquizengine.repo.CompleteRepository;
import com.elos.webquizengine.repo.QuizRepository;
import com.elos.webquizengine.response.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final CompleteRepository completeRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, CompleteRepository completeRepository) {
        this.quizRepository = quizRepository;
        this.completeRepository = completeRepository;
    }

    public Quiz add(Quiz quiz) {
        if (quiz.getAnswer() == null) quiz.setAnswer(new ArrayList<>());
        quiz.setDate(LocalDateTime.now());
        return quizRepository.save(quiz);
    }

    public Quiz getById(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void remove(Long id, User user) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        System.out.println(quiz.getAuthor());
        System.out.println(user);
        if (!quiz.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.delete(quiz);
    }

    public Page<Quiz> getAll(PageRequest pageRequest) {
        return quizRepository.findAll(pageRequest);
    }

    public QuizResponse solveQuiz(Long id, AnswerDto answerDto, User user) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (answerDto.getAnswer().size() != quiz.getAnswer().size()) {
            return new QuizResponse(false, "Wrong answer! Please, try again.");
        } else {
            for (int i = 0; i < answerDto.getAnswer().size(); i++) {
                if (!answerDto.getAnswer().get(i).equals(quiz.getAnswer().get(i))) {
                    return new QuizResponse(false, "Wrong answer! Please, try again.");
                }
            }
        }
        Complete save = completeRepository.save(new Complete(quiz.getId(), user, LocalDateTime.now()));
        return new QuizResponse(true, "Congratulations, you're right!");

    }

    public Page<Complete> getAllByUser(User user, PageRequest pageRequest) {
        return completeRepository.findAllByUserOrderByIdDesc(user, pageRequest);
    }

    public List<Quiz> getQuizzesIn30Days() {
        return quizRepository.findQuizzesByDateBetweenOrderByDateDesc(LocalDateTime.now().minusDays(30), LocalDateTime.now());

    }
}
