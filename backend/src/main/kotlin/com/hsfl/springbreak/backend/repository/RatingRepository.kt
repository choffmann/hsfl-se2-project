package com.hsfl.springbreak.backend.repository

import com.hsfl.springbreak.backend.entity.RatingEntity
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional


@Transactional(Transactional.TxType.MANDATORY)
interface RatingRepository : CrudRepository<RatingEntity, Long> {
    fun findAllByRecipeId(id: Long): List<RatingEntity>

    fun findRatingEntityByRecipe_IdAndUser_Id(recipeID: Long, userID: Long): RatingEntity?

}

