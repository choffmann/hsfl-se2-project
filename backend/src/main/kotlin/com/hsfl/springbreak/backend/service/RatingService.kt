package com.hsfl.springbreak.backend.service

import com.hsfl.springbreak.backend.entity.RatingEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.repository.RatingRepository
import com.hsfl.springbreak.backend.repository.RecipeRepository
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class RatingService(val ratingRepository: RatingRepository,
                    val recipeRepository: RecipeRepository,
                    val userRepository: UserRepository) {

    /**
     * Saves a new rating to database or updates an existing entry.
     * @param rating The rating from type Rating.SendRating(stars, recipeId, userId) to be saved to database.
     */
    fun setRating(rating: Rating.SendRating): ApiResponse<Double> {
        return if (userRepository.existsById(rating.userId) && recipeRepository.existsById(rating.recipeId)) {
            val recipeProxy = recipeRepository.findById(rating.recipeId).get()
            val userProxy = userRepository.findById(rating.userId).get()
            val ratingProxy = ratingRepository.findRatingEntityByRecipe_IdAndUser_Id(recipeProxy.id!!, userProxy.id!!)

            // Set new Rating if none exists from user else update
            if (ratingProxy == null) {
                ratingRepository.save(RatingEntity.fromDto(rating, recipeProxy, userProxy))
            } else {
                ratingProxy.stars = rating.stars
                ratingRepository.save(ratingProxy)
            }

            ApiResponse(data = calcScore(rating.recipeId), success = true)
        } else {
            ApiResponse(error = "Invalid user recipe combination", success = false)
        }
    }

    private fun calcScore(id: Long): Double {
        val ratings = ratingRepository.findAllByRecipeId(id)
        var sum = 0.00

        for (rating: RatingEntity in ratings) {
            sum += rating.stars
        }
        return sum / ratings.size
    }
}