package com.camping_fisa.bouffonduroiapi.controllers.questions.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private int questionId;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;
    private Integer categoryId;
}

