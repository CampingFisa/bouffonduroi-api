package com.camping_fisa.bouffonduroiapi.controllers.quiz;

import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.AnswerRequestDto;
import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.AnswerResponseDto;
import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.QuizDto;
import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.QuizRequestDto;
import com.camping_fisa.bouffonduroiapi.services.quiz.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
@Tag(name = "Quiz", description = "Manage solo quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    @Operation(summary = "Generate a quiz")
    public ResponseEntity<QuizDto> generateQuiz(@RequestBody @Valid QuizRequestDto quizRequestDto) {
        QuizDto quiz = quizService.generateQuiz(quizRequestDto);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/answer")
    @Operation(summary = "Check an answer")
    public ResponseEntity<AnswerResponseDto> checkAnswer(@RequestBody @Valid AnswerRequestDto answerRequestDto) {
        AnswerResponseDto response = quizService.checkAnswer(answerRequestDto);
        return ResponseEntity.ok(response);
    }
}

