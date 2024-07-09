package com.camping_fisa.bouffonduroiapi.services.questions;

import com.camping_fisa.bouffonduroiapi.controllers.questions.dto.ThemeDTO;
import com.camping_fisa.bouffonduroiapi.entities.questions.Category;
import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import com.camping_fisa.bouffonduroiapi.repositories.questions.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    private void setParentForChildren(Theme newTheme) {
        if(newTheme.getCategories() != null) {
            for (Category cat : newTheme.getCategories()) {
                cat.setTheme(newTheme);
            }
        }
        if (newTheme.getThemeChildren() != null) {
            for (Theme child : newTheme.getThemeChildren()) {
                child.setThemeParent(newTheme);
                setParentForChildren(child);
            }
        }
    }

    public Theme createTheme(ThemeDTO themeDTO) {
        Theme newTheme = fromDto(themeDTO);
        setParentForChildren(newTheme);
        return themeRepository.save(newTheme);
    }

    public static Theme fromDto(ThemeDTO themeDTO) {
        return new Theme(
                null,
                themeDTO.getThemeName(),
                themeDTO.getThemeDescription(),
                themeDTO.getIsMain(),
                null,
                themeDTO.getCategories(),
                themeDTO.getThemeChildren().stream().map(ThemeService::fromDto).toList()
        );
    }

}
