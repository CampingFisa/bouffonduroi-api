package com.camping_fisa.bouffonduroiapi.controllers.quiz;

import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.AnswerRequestDto;
import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.AnswerResponseDto;
import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.QuizDto;
import com.camping_fisa.bouffonduroiapi.controllers.quiz.dto.QuizRequestDto;
import com.camping_fisa.bouffonduroiapi.services.quiz.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
@Tag(name = "Quiz", description = "Manage solo quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    @Operation(
            summary = "Generate a quiz",
            description = "Generates a new solo quiz based on the provided request.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quiz generated", content = @Content(schema = @Schema(implementation = QuizDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request")
            }
    )
    public ResponseEntity<QuizDto> generateQuiz(@RequestBody @Valid QuizRequestDto quizRequestDto) {
        QuizDto quiz = quizService.generateQuiz(quizRequestDto);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/answer")
    @Operation(
            summary = "Check an answer",
            description = "Checks the validity of an answer for a given quiz question.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Answer checked", content = @Content(schema = @Schema(implementation = AnswerResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "Quiz or question not found")
            }
    )
    public ResponseEntity<AnswerResponseDto> checkAnswer(@RequestBody @Valid AnswerRequestDto answerRequestDto) {
        AnswerResponseDto response = quizService.checkAnswer(answerRequestDto);
        return ResponseEntity.ok(response);
    }
}
