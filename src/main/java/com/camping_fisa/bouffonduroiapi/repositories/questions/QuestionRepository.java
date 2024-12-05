package com.camping_fisa.bouffonduroiapi.repositories.questions;

import com.camping_fisa.bouffonduroiapi.entities.questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    //List<Question> findAllByMainTheme_ThemeId(Long mainThemeId);

    @Query("SELECT q FROM Question q WHERE q.mainTheme.themeId = :mainThemeId")
    List<Question> findAllByMainTheme_ThemeId(@Param("mainThemeId") Long mainThemeId);

}
