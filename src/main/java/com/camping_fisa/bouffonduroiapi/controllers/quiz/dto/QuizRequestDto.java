package com.camping_fisa.bouffonduroiapi.controllers.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequestDto {
    private long themeId;
    private int numberOfQuestions;
}
