package com.camping_fisa.bouffonduroiapi.controllers.questions.dto;

import com.camping_fisa.bouffonduroiapi.entities.questions.Category;
import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThemeDTO {

    private Integer themeId;

    private String themeName;

    private String themeDescription;

    private Boolean isMain;

    private List<ThemeDTO> themeChildren;

    private List<Category> categories;
}
