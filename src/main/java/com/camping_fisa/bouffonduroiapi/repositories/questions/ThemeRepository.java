package com.camping_fisa.bouffonduroiapi.repositories.questions;

import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    // Optional<Theme> findBythemeIdAndIsMain(Long themeId, Boolean isMain);
    @Query("SELECT t FROM Theme t WHERE t.themeId = :themeId AND t.isMain = :isMain")
    Optional<Theme> findBythemeIdAndIsMain(@Param("themeId") Long themeId, @Param("isMain") Boolean isMain);

}
