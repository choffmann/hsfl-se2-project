package com.hsfl.springbreak.backend

import com.hsfl.springbreak.backend.entity.*
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.IngredientRecipeId
import com.hsfl.springbreak.backend.repository.*
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class EntityConfiguration {

    @Bean
    fun databaseInitializer(favoriteRepository: FavoriteRepository,
                            ingredientRepository: IngredientRepository,
                            ratingRepository: RatingRepository,
                            recipeRepository: RecipeRepository, userRepository: UserRepository,
                            categoryRepository: CategoryRepository,
                            difficultyRepository: DifficultyRepository,
                            ingredientRecipeRepository: IngredientRecipeRepository) =
            ApplicationRunner {
                val user = userRepository.save(UserEntity(
                        firstName = "Hektor", lastName = "Panzer", email = "panzer@",
                        password = "secret", image = "DickPic.png"
                ))
                 print("user:" + user)

                val diff = difficultyRepository.save(DifficultyEntity(
                    name = "Easy"
                ))
                print("difficulty:" + diff)

                val cat = categoryRepository.save(CategoryEntity(
                        name = "Food"
                ))
                print("category:" + cat)


                val ingredient1 = ingredientRepository.save(IngredientEntity(
                        name = "Milch"
                ))
                print("first ingredient:" + ingredient1)

                val ingredient2 = ingredientRepository.save(IngredientEntity(
                    name = "Müsli"
                ))
                print("second ingredient:" + ingredient2)


                val recipe = recipeRepository.save(RecipeEntity(
                        title = "Hektor", shortDescription = "Panzer", price = 25.9,
                        duration = 10.8, difficulty=diff,category= cat, creator= user ,
                        createTime = LocalDate.now(), image ="halloImage" , longDescription = "kkskskwkwkwk", views = 100,
                        ingredients = listOf()
                ))
                print("recipe:" + recipe)

                val ingredientRecipeDto = IngredientRecipe(
                    id = IngredientRecipeId( recipeId = recipe.id!!, ingredientId = ingredient1.id!!),
                    recipe = recipe.toDto(),
                    ingredient = ingredient1.toDto(),
                    unit = "Liter",
                    amount = 1
                )
                val ingredientRecipe = ingredientRecipeRepository.save(IngredientRecipeEntity.fromDto(ingredientRecipeDto))
                print("Ingredient-Recipe:" + ingredientRecipe)

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
