package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Rating
import com.hsfl.springbreak.backend.service.RatingService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class RatingController(val ratingService: RatingService) {

    @PostMapping("api/rating")
    fun setRating(@RequestBody rating: Rating.SendRating): ApiResponse<Double> = ratingService.setRating(rating)

}
