package com.camping_fisa.bouffonduroiapi.controllers.questions.dto;

import com.camping_fisa.bouffonduroiapi.entities.questions.Category;
import lombok.Data;

import java.util.List;

@Data
public class ThemeDTO {

    private String themeName;

    private String themeDescription;

    private Boolean isMain;

    private List<ThemeDTO> themeChildren;

    private List<Category> categories;
}
