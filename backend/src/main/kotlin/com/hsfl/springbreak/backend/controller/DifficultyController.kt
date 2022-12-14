package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Difficulty
import com.hsfl.springbreak.backend.repository.DifficultyRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Call difficulty-related functions from difficulty-repository.
 */
@CrossOrigin("http://localhost:3000")
@RestController
class DifficultyController(val difficultyRepository: DifficultyRepository) {

    /**
     * API-Endpoint for getting a list of all available difficulties.
     * @return API-Response with a list of all stored difficulties or an error
     */
    @GetMapping("api/difficulties")
    fun retrieveDifficulties(): ApiResponse<List<Difficulty>> {
        val difficultyEntities = difficultyRepository.findAll().distinct()
        return if (difficultyEntities.isNotEmpty()) {
            val ingredients = difficultyEntities.map { it.toDto() }
            ApiResponse(data = ingredients, success = true)
        } else {
            ApiResponse(error = "No ingredients available", success = false)
        }
    }
}