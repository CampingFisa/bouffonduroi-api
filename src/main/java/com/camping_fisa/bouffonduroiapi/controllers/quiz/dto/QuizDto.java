package com.camping_fisa.bouffonduroiapi.controllers.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private String quizId;
    private List<QuestionDto> questions;
}