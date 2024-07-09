package com.camping_fisa.bouffonduroiapi.services.questions;

import com.camping_fisa.bouffonduroiapi.controllers.questions.dto.QuestionDTO;
import com.camping_fisa.bouffonduroiapi.controllers.questions.dto.UpdateCategoryDTO;
import com.camping_fisa.bouffonduroiapi.entities.questions.Category;
import com.camping_fisa.bouffonduroiapi.entities.questions.Question;
import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import com.camping_fisa.bouffonduroiapi.exceptions.BusinessException;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import com.camping_fisa.bouffonduroiapi.repositories.questions.CategoryRepository;
import com.camping_fisa.bouffonduroiapi.repositories.questions.ThemeRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ThemeRepository themeRepository;

    public CategoryService(CategoryRepository categoryRepository, ThemeRepository themeRepository) {
        this.categoryRepository = categoryRepository;
        this.themeRepository = themeRepository;
    }

    public Category updateCategory(long categoryId, UpdateCategoryDTO updateCategoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException((int) categoryId, Category.class));

        Theme mainTheme = themeRepository.findById(existingCategory.getTheme().getThemeId())
                .orElseThrow(() -> new NotFoundException(Math.toIntExact(existingCategory.getTheme().getThemeId())
                        , Theme.class));
        while(!mainTheme.isMain()){
            Long parentThemeId = mainTheme.getThemeParent().getThemeId();
            mainTheme = themeRepository.findById(parentThemeId)
                    .orElseThrow(() -> new NotFoundException(Math.toIntExact(parentThemeId)
                            , Theme.class));
        }

        if(updateCategoryDTO.getCategoryName() == null || updateCategoryDTO.getCategoryName().isEmpty()) {
            throw new BusinessException(Category.class, "Category name cannot be empty");
        }

        existingCategory.setCategoryName(updateCategoryDTO.getCategoryName());
        existingCategory.setCategoryDescription(updateCategoryDTO.getCategoryDescription());

        existingCategory.getQuestions().clear();

        if (updateCategoryDTO.getQuestions() != null) {
            for (QuestionDTO questionDTO : updateCategoryDTO.getQuestions()) {
                if(questionDTO.getQuestion() == null || questionDTO.getQuestion().isEmpty()) {
                    throw new BusinessException(Question.class, "Question cannot be empty");
                }
                if(questionDTO.getAnswerA() == null || questionDTO.getAnswerA().isEmpty()) {
                    throw new BusinessException(Question.class, "Answer A cannot be empty");
                }
                if(questionDTO.getAnswerB() == null || questionDTO.getAnswerB().isEmpty()) {
                    throw new BusinessException(Question.class, "Answer B cannot be empty");
                }
                if(questionDTO.getAnswerC() == null || questionDTO.getAnswerC().isEmpty()) {
                    throw new BusinessException(Question.class, "Answer C cannot be empty");
                }
                if(questionDTO.getAnswerD() == null || questionDTO.getAnswerD().isEmpty()) {
                    throw new BusinessException(Question.class, "Answer D cannot be empty");
                }
                if(questionDTO.getCorrectAnswer() == null || questionDTO.getCorrectAnswer().isEmpty()) {
                    throw new BusinessException(Question.class, "Correct answer cannot be empty");
                }
                Question question = new Question();
                question.setQuestion(questionDTO.getQuestion());
                question.setAnswerA(questionDTO.getAnswerA());
                question.setAnswerB(questionDTO.getAnswerB());
                question.setAnswerC(questionDTO.getAnswerC());
                question.setAnswerD(questionDTO.getAnswerD());
                question.setCorrectAnswer(questionDTO.getCorrectAnswer());
                question.setCategory(existingCategory);
                question.setMainTheme(mainTheme);
                existingCategory.getQuestions().add(question);
            }
        }

        return categoryRepository.save(existingCategory);
    }
}

