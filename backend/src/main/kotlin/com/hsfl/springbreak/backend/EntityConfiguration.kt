package com.hsfl.springbreak.backend

import com.hsfl.springbreak.backend.entity.*
import com.hsfl.springbreak.backend.repository.*
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EntityConfiguration {

    @Bean
    fun databaseInitializer(favoriteRepository: FavoriteRepository,
                            ingredientRepository: IngredientRepository,
                            ratingRepository: RatingRepository,
                            recipeRepository: RecipeRepository, userRepository: UserRepository,
                            categoryRepository: CategoryRepository,
                            difficultyRepository: DifficultyRepository) =
            ApplicationRunner {
                val user = userRepository.save(UserEntity(
                        firstName = "Hektor", lastName = "Panzer", email = "panzer@",
                        password = "secret", image = "DickPic.png", favoriteRecipe = listOf()
                ))
                 print("user:" + user)

                val cat = categoryRepository.save(CategoryEntity(
                        name = "Food"
                ))
                print("cat:" + cat)


                val ingredient = ingredientRepository.save(IngredientEntity(
                        name = "ttttttttt"
                ))
                print("ingredient:" + ingredient)


                val diff = difficultyRepository.save(DifficultyEntity(
                        name = "Easy"
                ))

                val recipe = recipeRepository.save(RecipeEntity(
                        title = "Hektor", shortDescription = "Panzer", price = 25.9,
                        duration = 10.8, category= cat, creator= user ,difficulty=diff
                ))
                print("recipe:" + recipe)


                val rating = ratingRepository.save(RatingEntity(
                        likes= 6, dislike= 7,  recipe= recipe
                ))
                print("rating:" + rating)

            }

}


/*
            recipeRepository.save(RecipeEntity(
                id = null,
                title = "Müsli", shortDescription = "lecker", price = 3.00, duration = 10.00,
               category = cat,
               creator = user))
        }
}

                rating = null,
                difficulty = difficulty,
                createTime = LocalDate.now(),
                ingredients = null,
                image = "https://www.einfachbacken.de/sites/einfachbacken.de/files/styles/700_530/public/2019-05/schokomuffins.jpg?h=a1e1a043&itok=by_zr_C7",
                longDescription = "Butter mit Zucker und Vanillezucker verrühren. Eier unterrühren. Zartbitterschokolade grob hacken. Ofen auf 180 Grad (Umluft: 160 Grad) vorheizen. Mehl mit Kakaopulver, Salz und Backpulver vermischen. Mehlmischung mit der Milch zur Butter-Zuckermischung geben und alles gut verrühren. Etwa zwei Drittel der gehackten Schokolade unterheben",
                views = 0

                ))


        }

                 */
