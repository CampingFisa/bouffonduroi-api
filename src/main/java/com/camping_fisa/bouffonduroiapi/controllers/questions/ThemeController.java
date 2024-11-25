package com.camping_fisa.bouffonduroiapi.controllers.questions;

import com.camping_fisa.bouffonduroiapi.controllers.questions.dto.ThemeDTO;
import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import com.camping_fisa.bouffonduroiapi.services.questions.ThemeService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;


@RestController
@RequestMapping("/api/themes")
@Tag(name = "Manage themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping
    @Operation(summary = "Retrieves all themes", responses = {
            @ApiResponse(responseCode = "200", description = "Themes found"),
            @ApiResponse(responseCode = "404", description = "Themes not found")
    })
    public List<Theme> getAllThemes(@RequestParam(required = false) Boolean isMain) {
        return themeService.getAllThemes(isMain);
    }

    @GetMapping("/{themeId}")
    @Operation(summary = "Retrieves all themes", responses = {
            @ApiResponse(responseCode = "200", description = "Themes found"),
            @ApiResponse(responseCode = "404", description = "Themes not found")
    })
    public Theme getThemeById(@PathVariable Long themeId) {
        return themeService.getThemeById(themeId);
    }

    @PostMapping
    @Operation(summary = "Create a theme", responses = {
            @ApiResponse(responseCode = "201", description = "Theme created"),
            @ApiResponse(responseCode = "400", description = "Impossible to create a theme with given parameters")
    })
    public Theme createTheme(@RequestBody ThemeDTO theme) {
        return themeService.createTheme(theme);
    }
}
