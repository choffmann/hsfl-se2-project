package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Category
import com.hsfl.springbreak.backend.repository.CategoryRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("http://localhost:3000")
@RestController
class CategoryController(val categoryRepository: CategoryRepository) {

    /**
     * Returns a list of all categories.
     */
    @GetMapping("api/categories")
    fun retrieveDifficulties(): ApiResponse<List<Category>> {
        return ApiResponse(data = categoryRepository.findAll().map { it.toDto() }, success = true)
    }
}