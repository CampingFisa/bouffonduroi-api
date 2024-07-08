package com.camping_fisa.bouffonduroiapi.controllers.questions.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateCategoryDTO {
    private String categoryName;
    private String categoryDescription;
    private List<QuestionDTO> questions;
}
