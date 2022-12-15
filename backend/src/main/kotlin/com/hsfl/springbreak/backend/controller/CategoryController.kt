package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Category
import com.hsfl.springbreak.backend.repository.CategoryRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Call category-related functions from category-repository.
 */
@CrossOrigin("http://localhost:3000")
@RestController
class CategoryController(val categoryRepository: CategoryRepository) {

    /**
     * API-Endpoint for getting a list of all available categories.
     * @return API-Response with a list of all stored categories or an error.
     */
    @GetMapping("api/categories")
    fun getEntries(): ApiResponse<List<Category>> {
        val categoryEntities = categoryRepository.findAll().distinct()
        return if (categoryEntities.isNotEmpty()) {
            val categories = categoryEntities.map { it.toDto() }
            ApiResponse(data = categories, success = true)
        } else {
            ApiResponse(error = "No categories available", success = false)
        }
    }


}