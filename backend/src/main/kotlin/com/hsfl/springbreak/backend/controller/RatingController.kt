package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.service.RatingService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Call rating-related functions from rating-service.
 */
@CrossOrigin("http://localhost:3000")
@RestController
class RatingController(val ratingService: RatingService) {

    /**
     * API endpoint for setting a recipe rating.
     * @param rating The Rating-DTO from the request body.
     * @return API-Response with the current recipe's score or an error.
     */
    @PostMapping("api/rating")
    fun setRating(@RequestBody rating: Rating.SendRating): ApiResponse<Double> = ratingService.setRating(rating)

}
