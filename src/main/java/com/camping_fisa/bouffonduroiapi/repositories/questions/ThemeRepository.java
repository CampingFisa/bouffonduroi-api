package com.camping_fisa.bouffonduroiapi.repositories.questions;

import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    Optional<Theme> findBythemeIdAndIsMain(Long themeId, Boolean isMain);

    List<Theme> findByIsMain(Boolean isMain);
}
