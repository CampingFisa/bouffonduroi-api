package com.camping_fisa.bouffonduroiapi.repositories.questions;

import com.camping_fisa.bouffonduroiapi.entities.questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByMainTheme_ThemeId(Long mainThemeId);
}
