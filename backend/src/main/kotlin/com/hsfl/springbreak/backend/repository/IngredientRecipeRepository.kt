package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
@Transactional
interface IngredientRecipeRepository : CrudRepository<IngredientRecipeEntity, Long> {

    /**
     * Custom query to delete all IngredientRecipeEntities that belong to a given recipeId.
     * @param recipeID The id of the recipe which ingredients shall be deleted.
     */
    @Modifying
    @Query("DELETE from IngredientRecipeEntity ir where ir.id.recipeId = :recipeID")
    fun deleteByRecipeId(@Param("recipeID") recipeID: Long)
}