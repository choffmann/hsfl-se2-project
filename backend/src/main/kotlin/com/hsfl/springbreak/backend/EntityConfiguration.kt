package com.hsfl.springbreak.backend

import com.hsfl.springbreak.backend.entity.*
import com.hsfl.springbreak.backend.repository.RecipeRepository
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class EntityConfiguration {

    @Bean
    fun databaseInitializer(recipeRepository: RecipeRepository, userRepository: UserRepository) =
        ApplicationRunner {
            val user = userRepository.save(UserEntity(
                firstName = "Hektor", lastName = "Panzer", email = "panzer@",
                password = "secret", image = "DickPic.png", favoriteRecipe = listOf()
            ))

            val category = CategoryEntity(name = "Backen")
            val difficulty = DifficultyEntity(name = "Leicht")
            recipeRepository.save(RecipeEntity(
                title = "Müsli", shortDescription = "lecker", price = 3.00, duration = 10.00,
                rating = null,
                difficulty = difficulty,
                category = category,
                creator = user,
                createTime = LocalDate.now(),
                ingredients = null,
                image = "https://www.einfachbacken.de/sites/einfachbacken.de/files/styles/700_530/public/2019-05/schokomuffins.jpg?h=a1e1a043&itok=by_zr_C7",
                longDescription = "Butter mit Zucker und Vanillezucker verrühren. Eier unterrühren. Zartbitterschokolade grob hacken. Ofen auf 180 Grad (Umluft: 160 Grad) vorheizen. Mehl mit Kakaopulver, Salz und Backpulver vermischen. Mehlmischung mit der Milch zur Butter-Zuckermischung geben und alles gut verrühren. Etwa zwei Drittel der gehackten Schokolade unterheben",
                views = 0
                ))


        }
}