package com.camping_fisa.bouffonduroiapi.controllers.questions.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCategoryDTO {
    private String categoryName;
    private String categoryDescription;
    private List<QuestionDTO> questions;
}
