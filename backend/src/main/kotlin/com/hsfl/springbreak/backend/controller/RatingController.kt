package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.RatingEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.repository.RatingRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
class RatingController(val ratingRepository: RatingRepository) {

    @PostMapping("rating")
    fun createNewRating(@RequestBody rating: Rating): ApiResponse<Rating> {
        val savedRating = ratingRepository.save(RatingEntity.fromDto(rating)).toDto()
        print("-------- " + savedRating + "----- \n")
        return if (savedRating != null) {
            ApiResponse(data = savedRating, success = true)
        } else {
            ApiResponse(error = "Error by saving", success = false)
        }
    }

    @PutMapping("rating/like/{id}")
    fun addLike(@PathVariable("id") id: Long): ApiResponse<Rating> {
        if (ratingRepository.existsById(id)) {
            val result = ratingRepository.findById(id).get()
            result.likes = result.likes + 1
            return ApiResponse(data = ratingRepository.save(result).toDto(), success = true)

        } else
            return ApiResponse(error = "No Recipe with the ID: ${id} ", success = false)
    }

    @PutMapping("rating/dislike/{id}")
    fun addDisLike(@PathVariable("id") id: Long): ApiResponse<Rating> {
        return if (ratingRepository.existsById(id)) {
            val result = ratingRepository.findById(id).get()
            result.dislike = result.dislike + 1
            return ApiResponse(data = ratingRepository.save(result).toDto(), success = true)

        } else return ApiResponse(error = "No Recipe with the ID: ${id} ", success = false)
    }

}
