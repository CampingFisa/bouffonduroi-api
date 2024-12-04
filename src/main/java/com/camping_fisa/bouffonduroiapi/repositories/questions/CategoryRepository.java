package com.camping_fisa.bouffonduroiapi.repositories.questions;

import com.camping_fisa.bouffonduroiapi.entities.questions.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Integer>  { }
