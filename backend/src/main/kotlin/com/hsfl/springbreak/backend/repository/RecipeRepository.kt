package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.RecipeEntity
import org.springframework.data.repository.CrudRepository

interface RecipeRepository: CrudRepository<RecipeEntity, Long> {
}