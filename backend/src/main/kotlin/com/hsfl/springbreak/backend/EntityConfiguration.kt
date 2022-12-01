package com.hsfl.springbreak.backend

import com.hsfl.springbreak.backend.entity.RatingEntity
import com.hsfl.springbreak.backend.entity.RecipeEntity
import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.repository.RecipeRepository
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EntityConfiguration {

    @Bean
    fun databaseInitializer(recipeRepository: RecipeRepository, userRepository: UserRepository) =
        ApplicationRunner {
            val user = userRepository.save(UserEntity(
                firstName = "Hektor", lastName = "Panzer", email = "panzer@",
                password = "secret", image = "DickPic.png", favoriteRecipe = listOf()
            ))

            /*
            recipeRepository.save(RecipeEntity(
                title = "Müsli", shortDescription = "lecker", price = 3.00, duration = 10.00,


                ))

             */
        }
}