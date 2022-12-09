package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.entity.RatingEntity
import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.repository.IngredientRecipeRepository
import com.hsfl.springbreak.backend.repository.RatingRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
    @RestController
    class RatingController (val ratingRepository: RatingRepository) {

        @PostMapping("api/rating")
        fun createNewRating(@RequestBody newRating: Rating): ApiResponse<Rating> {
            val savedRating = ratingRepository.save(RatingEntity.fromDto(newRating)).toDto()
            print("-------- "+savedRating +"----- \n")
            return if (savedRating  != null) {
                ApiResponse(data = savedRating, success = true)
            } else {
                ApiResponse(error = "Error by saving", success = false)
            }
        }

        @PutMapping("api/ratingLike/{id}")
        fun addLike(@PathVariable("id") ratingId: Long): ApiResponse<Rating> {
            return if (ratingRepository.existsById(ratingId)) {
                val result =  ratingRepository.findById(ratingId).get()
                result.likes = result.likes+1
                return ApiResponse(data = ratingRepository.save(result).toDto(), success = true)

            } else return ApiResponse(error = "No Recipe with the ID: ${ratingId} ", success = false)
        }

       @PutMapping("api/ratingdisLike/{id}")
       fun addDisLike(@PathVariable("id") ratingId: Long): ApiResponse<Rating> {
        return if (ratingRepository.existsById(ratingId)) {
            val result =  ratingRepository.findById(ratingId).get()
            result.dislike = result.dislike+1
            return ApiResponse(data = ratingRepository.save(result).toDto(), success = true)

        } else return ApiResponse(error = "No Recipe with the ID: ${ratingId} ", success = false)
     }

    }
