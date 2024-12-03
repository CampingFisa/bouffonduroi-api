package com.camping_fisa.bouffonduroiapi.services.quiz;

import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.*;
import com.camping_fisa.bouffonduroiapi.entities.questions.Question;
import com.camping_fisa.bouffonduroiapi.exceptions.BusinessException;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import com.camping_fisa.bouffonduroiapi.repositories.questions.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuestionRepository questionRepository;

    public QuizDto generateQuiz(QuizRequestDto quizRequestDto) {
        // Récupérer les questions du thème
        List<Question> questions = questionRepository.findAllByMainTheme_ThemeId(quizRequestDto.getThemeId());

        if (questions.size() < quizRequestDto.getNumberOfQuestions()) {
            throw new BusinessException(Question.class, "not enough questions for the requested theme.");
        }

        // Mélanger et sélectionner les questions
        Collections.shuffle(questions);
        List<Question> selectedQuestions = questions.subList(0, quizRequestDto.getNumberOfQuestions());

        // Construire l'objet QuizDto sans réponses
        return new QuizDto(UUID.randomUUID().toString(), selectedQuestions.stream().map(this::toQuestionDto).toList());
    }

    public AnswerResponseDto checkAnswer(AnswerRequestDto answerRequestDto) {
        Question question = questionRepository.findById(answerRequestDto.getQuestionId())
                .orElseThrow(() -> new NotFoundException("Question not found"));

        boolean correct = question.getCorrectAnswer().equalsIgnoreCase(answerRequestDto.getResponse());

        return new AnswerResponseDto(correct);
    }

    private QuestionDto toQuestionDto(Question question) {
        return new QuestionDto(
                question.getQuestionId(),
                question.getQuestion(),
                List.of(question.getAnswerA(), question.getAnswerB(), question.getAnswerC(), question.getAnswerD())
        );
    }
}
