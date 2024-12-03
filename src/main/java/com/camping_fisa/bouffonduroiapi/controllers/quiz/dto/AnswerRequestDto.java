package com.camping_fisa.bouffonduroiapi.controllers.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequestDto {
    private long questionId;
    private String response;
    private String type; // MULTIPLE_CHOICE, OPEN
}