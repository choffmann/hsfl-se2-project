package com.hsfl.springbreak.backend.controller

import com.hsfl.springbreak.backend.entity.FavoriteEntity
import com.hsfl.springbreak.backend.entity.IngredientRecipeEntity
import com.hsfl.springbreak.backend.model.ApiResponse
import com.hsfl.springbreak.backend.model.Favorite
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.repository.FavoriteRepository
import com.hsfl.springbreak.backend.repository.IngredientRecipeRepository
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


        @CrossOrigin("http://localhost:3000")
        @RestController
       class FavoriteController(val  favoriteRepository: FavoriteRepository) {

            @PostMapping("api/Favorite")
            fun createFavorite(@RequestBody newFavorite: Favorite): ApiResponse<Favorite> {
                val savedFavorite = favoriteRepository.save(FavoriteEntity.fromDto(newFavorite)).toDto()
                print("-------- " + savedFavorite + "----- \n")
                return if (savedFavorite != null) {
                    ApiResponse(data = savedFavorite, success = true)
                } else {
                    ApiResponse(error = "Error by saving", success = false)
                }
            }
        }
