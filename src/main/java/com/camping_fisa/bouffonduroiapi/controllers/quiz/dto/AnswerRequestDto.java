package com.camping_fisa.bouffonduroiapi.controllers.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequestDto {
    private long questionId;
    @NotBlank(message = "Response cannot be blank")
    private String response;
    @NotBlank(message = "Type cannot be blank")
    @Pattern(regexp = "OPEN|CLOSED", message = "Type must be OPEN or CLOSED")
    private String type; // MULTIPLE_CHOICE, OPEN
}