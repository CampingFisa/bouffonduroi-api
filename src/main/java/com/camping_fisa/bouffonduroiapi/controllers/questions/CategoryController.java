package com.camping_fisa.bouffonduroiapi.controllers.questions;

import com.camping_fisa.bouffonduroiapi.controllers.questions.dto.UpdateCategoryDTO;
import com.camping_fisa.bouffonduroiapi.entities.questions.Category;
import com.camping_fisa.bouffonduroiapi.services.questions.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Manage categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Update a category", responses = {
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "400", description = "Impossible to create a category with given parameters"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public Category updateCategory(@PathVariable int categoryId, @RequestBody UpdateCategoryDTO updateCategoryDTO) {
        return categoryService.updateCategory(categoryId, updateCategoryDTO);
    }

}
