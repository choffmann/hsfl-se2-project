package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.entity.RecipeEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Component
@Transactional
interface IngredientRecipeRepository: CrudRepository<IngredientRecipeEntity, Long> {
    //fun deleteAllByRecipeId(recipeId: Long): Unit
    @Modifying
    @Query("DELETE from IngredientRecipeEntity ir where ir.id.recipeId = :recipeID")
    fun deleteByRecipeId(@Param("recipeID") recipeID: Long): Unit
}