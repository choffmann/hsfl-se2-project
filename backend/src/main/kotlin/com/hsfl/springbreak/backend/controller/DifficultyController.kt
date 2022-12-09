package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Difficulty
import com.hsfl.springbreak.backend.repository.DifficultyRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class DifficultyController(val difficultyRepository: DifficultyRepository) {

    @GetMapping("difficulties")
    fun retrieveDifficulties(): ApiResponse<List<Difficulty>> {
        val difficulties = difficultyRepository.findAll().map { it.toDto() }
        return ApiResponse(data = difficulties, success = true)
    }
}