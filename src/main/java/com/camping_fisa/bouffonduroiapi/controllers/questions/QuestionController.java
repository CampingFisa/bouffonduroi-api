package com.camping_fisa.bouffonduroiapi.controllers.questions;

import com.camping_fisa.bouffonduroiapi.entities.questions.Question;
import com.camping_fisa.bouffonduroiapi.services.questions.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
@Tag(name = "Manage questions")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping
    @Operation(summary = "Retrieves random questions from main theme", responses = {
            @ApiResponse(responseCode = "201", description = "Questions found"),
            @ApiResponse(responseCode = "400", description = "Impossible to retrieve questions with given parameters"),
            @ApiResponse(responseCode = "404", description = "Questions not found")
    })
    public List<Question> getRandomQuestions(@RequestParam long themeId, @RequestParam int nbQuestions){
        return questionService.getRandomQuestions(themeId, nbQuestions);
    }
}
