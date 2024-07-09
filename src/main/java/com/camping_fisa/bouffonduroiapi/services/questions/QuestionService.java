package com.camping_fisa.bouffonduroiapi.services.questions;

import com.camping_fisa.bouffonduroiapi.entities.questions.Question;
import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import com.camping_fisa.bouffonduroiapi.exceptions.BusinessException;
import com.camping_fisa.bouffonduroiapi.exceptions.NotFoundException;
import com.camping_fisa.bouffonduroiapi.repositories.questions.QuestionRepository;
import com.camping_fisa.bouffonduroiapi.repositories.questions.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

    private final ThemeRepository themeRepository;

    private final QuestionRepository questionRepository;

    public QuestionService(ThemeRepository themeRepository, QuestionRepository questionRepository) {
        this.themeRepository = themeRepository;
        this.questionRepository = questionRepository;
    }

    public List<Question> getRandomQuestions(long themeId, int nbQuestions) {
        Theme existingTheme = themeRepository.findBythemeIdAndIsMain(themeId, true)
                .orElseThrow(() -> new NotFoundException((int) themeId, Theme.class));
        List<Question> allThemeQuestion = questionRepository.findAllByMainTheme_ThemeId(existingTheme.getThemeId());
        if(nbQuestions > allThemeQuestion.size()) {
            throw new BusinessException(Question.class
                    , "You requested " + nbQuestions + " questions, but only " + allThemeQuestion.size() + " were found");
        } else {
            Collections.shuffle(allThemeQuestion, new Random());
            return allThemeQuestion.subList(0, nbQuestions);
        }
    }
}
