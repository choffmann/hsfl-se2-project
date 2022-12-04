package com.hsfl.springbreak.backend

import com.hsfl.springbreak.backend.entity.*
import com.hsfl.springbreak.backend.repository.IngredientRepository
import com.hsfl.springbreak.backend.repository.RecipeRepository
import com.hsfl.springbreak.backend.repository.UserRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class EntityConfiguration {

    @Bean
    fun databaseInitializer(recipeRepository: RecipeRepository, userRepository: UserRepository, ingredientRepository: IngredientRepository) =
        ApplicationRunner {
            val user = userRepository.save(UserEntity(
                firstName = "Hektor", lastName = "Panzer", email = "panzer@",
                password = "secret", image = "DickPic.png", favoriteRecipe = listOf()
            ))

            val ingredient = ingredientRepository.save(IngredientEntity(id = 1, name = "Milch", recipes = listOf()))

            recipeRepository.save(RecipeEntity(
                title = "Müsli", shortDescription = "lecker", price = 3.00, duration = 10.00,
                likes = 0, dislikes = 0,
                difficulty = DifficultyEntity(id = null, recipes = listOf(), name = "schwer"),
                category = CategoryEntity(id = null, recipes = listOf(), name = "Frühstück"),
                creator = user,  createTime = LocalDate.now(), image = "Müsli.png", longDescription = "seeehhr lecker", views = 0
                // ingredients = listOf(IngredientRecipeEntity(id = null, recipe = null, ingredient = ingredient, amount = 2, unit = "Liter"))
                /*
                rating = null,
                difficulty = difficulty,
                createTime = LocalDate.now(),
                ingredients = null,
                image = "https://www.einfachbacken.de/sites/einfachbacken.de/files/styles/700_530/public/2019-05/schokomuffins.jpg?h=a1e1a043&itok=by_zr_C7",
                longDescription = "Butter mit Zucker und Vanillezucker verrühren. Eier unterrühren. Zartbitterschokolade grob hacken. Ofen auf 180 Grad (Umluft: 160 Grad) vorheizen. Mehl mit Kakaopulver, Salz und Backpulver vermischen. Mehlmischung mit der Milch zur Butter-Zuckermischung geben und alles gut verrühren. Etwa zwei Drittel der gehackten Schokolade unterheben",
                views = 0
                 */
                ))


        }
}